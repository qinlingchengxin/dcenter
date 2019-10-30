package net.ys.dao;

import net.ys.bean.DownStock;
import net.ys.bean.Stock;
import net.ys.bean.SyncTable;
import net.ys.mapper.DownStockMapper;
import net.ys.mapper.StockMapper;
import net.ys.mapper.SyncTableMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BuDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public boolean addStock(String platformCode, String tableName, String content) {
        String sql = "INSERT INTO STOCK (ID, PLATFORM_CODE, TABLE_NAME, CONTENT, CREATE_TIME) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql, UUID.randomUUID().toString(), platformCode, tableName, content, System.currentTimeMillis()) > 0;
    }

    public boolean chgStock(String id, int status) {
        String sql = "UPDATE STOCK SET STATUS = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, status, id) >= 0;
    }

    public List<SyncTable> queryAllTables() {
        String sql = "SELECT bce.TABLE_NAME, bce.REAL_TAB_NAME, btp.LAST_PACK_TIME FROM BUS_CUSTOM_ENTITY bce, BUS_TABLE_PACK btp WHERE bce.TABLE_NAME = btp.TABLE_NAME";
        return jdbcTemplate.query(sql, new SyncTableMapper());
    }

    public List<String> queryFields(String tableName) {
        String sql = "SELECT FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, String.class, tableName);
    }

    public long queryDataPackCount(String platformCode, String tableName, long lastPackTime, long time) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE SYS__PLATFORM_CODE = ? AND PACK_STATUS = 0 AND SYS__CREATE_TIME BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, Long.class, platformCode, lastPackTime, time);
    }

    public List<Map<String, Object>> queryPackDataByPage(String platformCode, SyncTable table, long time, String fieldStr, int page, int pageSize) {
        String sql = "SELECT " + fieldStr + " FROM " + table.getRealTabName() + " WHERE SYS__PLATFORM_CODE = ? AND PACK_STATUS = 0 AND SYS__CREATE_TIME BETWEEN ? AND ? ORDER BY SYS__CREATE_TIME LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, platformCode, table.getLastPackTime(), time, (page - 1) * pageSize, pageSize);
    }

    public void chgStockPackStatus(SyncTable table, long time) {
        String[] sql = new String[2];
        sql[0] = "UPDATE " + table.getRealTabName() + " SET PACK_STATUS = 1 WHERE SYS__CREATE_TIME BETWEEN " + table.getLastPackTime() + " AND " + time;
        sql[1] = "UPDATE BUS_TABLE_PACK SET LAST_PACK_TIME = " + time + " WHERE TABLE_NAME = '" + table.getTableName() + "'";
        jdbcTemplate.batchUpdate(sql);
    }

    public List<Stock> queryStocks(int status, long time) {
        String sql = "SELECT ID,PLATFORM_CODE,TABLE_NAME,CONTENT,STATUS,CREATE_TIME FROM STOCK WHERE STATUS = ? AND CREATE_TIME <= ?";
        return jdbcTemplate.query(sql, new StockMapper(), status, time);
    }

    public void addSliceSyncData(List<String> sql) {
        String[] strings = new String[sql.size()];
        sql.toArray(strings);

        int[] result = jdbcTemplate.batchUpdate(strings);
        int sum = 0;
        for (int i : result) {
            sum += i;
        }
        if (sum != sql.size()) {
            throw new RuntimeException("db error!");
        }
    }

    public List<DownStock> queryDownStocks(int status, long now) {
        String sql = "SELECT DS.ID, BCE.TABLE_NAME, BCE.REAL_TAB_NAME, DS.START_TIME, DS.END_TIME, DS.PAGE, DS.PAGE_SIZE, DS.STATUS, DS.CREATE_TIME FROM DOWN_STOCK DS LEFT JOIN BUS_CUSTOM_ENTITY BCE ON BCE.REAL_TAB_NAME = DS.TABLE_NAME WHERE DS.STATUS = ? AND DS.CREATE_TIME <= ? ORDER BY DS.PAGE";
        return jdbcTemplate.query(sql, new DownStockMapper(), status, now);
    }

    public void updateDownStockStatus(DownStock downStock, int status) {
        String sql = "UPDATE DOWN_STOCK SET STATUS = ? WHERE ID = ?";
        jdbcTemplate.update(sql, status, downStock.getId());
    }
}
