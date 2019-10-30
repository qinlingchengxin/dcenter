package net.ys.dao;

import net.ys.bean.BusDataTransLog;
import net.ys.bean.BusTfChangeLog;
import net.ys.bean.EpChangeLog;
import net.ys.mapper.BusDataTransLogMapper;
import net.ys.mapper.BusTfChangeLogMapper;
import net.ys.mapper.EpChangeLogMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Repository
public class LogDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public long queryDataTransLogCount(String tableName, String platformCode, int type, int status, String content, long start, long end) {
        String sql = "SELECT COUNT(*) FROM BUS_DATA_TRANS_LOG WHERE TRANS_TYPE = " + type + " AND CREATE_TIME BETWEEN " + start + " AND " + end;

        if (status > -1) {
            sql = sql + " AND STATUS = " + status;
        }

        if (StringUtils.isNotBlank(tableName)) {
            sql += " AND TABLE_NAME = '" + tableName + "'";
        }

        if (StringUtils.isNotBlank(platformCode)) {
            sql += " AND PLATFORM_CODE = '" + platformCode + "'";
        }

        if (StringUtils.isNotBlank(content)) {
            sql += " AND CONTENT LIKE '%" + content + "%'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<BusDataTransLog> queryDataTransLogs(String content, String tableName, String platformCode, int type, int status, int page, int pageSize, long start, long end) {
        String sql = "SELECT ID, PLATFORM_CODE, TABLE_NAME, TRANS_TYPE, CONTENT, FAILED_CONTENT, STATUS, CREATE_TIME, REMARKS FROM BUS_DATA_TRANS_LOG WHERE TRANS_TYPE = " + type + " AND CREATE_TIME BETWEEN " + start + " AND " + end;

        if (status > -1) {
            sql = sql + " AND STATUS = " + status;
        }

        if (StringUtils.isNotBlank(tableName)) {
            sql += " AND TABLE_NAME = '" + tableName + "'";
        }

        if (StringUtils.isNotBlank(platformCode)) {
            sql += " AND PLATFORM_CODE = '" + platformCode + "'";
        }

        if (StringUtils.isNotBlank(content)) {
            sql += " AND CONTENT LIKE '%" + content + "%'";
        }

        sql += " ORDER BY CREATE_TIME DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusDataTransLogMapper(), (page - 1) * pageSize, pageSize);
    }

    public long queryTfChangeLogCount() {
        String sql = "SELECT COUNT(*) FROM BUS_TF_CHANGE_LOG";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<BusTfChangeLog> queryTfChangeLogLogs(int page, int pageSize) {
        String sql = "SELECT ID, CHG_TYPE, OBJ_ID, UPDATE_AID, UPDATE_TIME, CONTENT FROM BUS_TF_CHANGE_LOG ORDER BY UPDATE_TIME DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusTfChangeLogMapper(), (page - 1) * pageSize, pageSize);
    }

    public long queryEpChangeLogCount() {
        String sql = "SELECT COUNT(*) FROM EP_CHANGE_LOG";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<EpChangeLog> queryEpChangeLogs(int page, int pageSize) {
        String sql = "SELECT ecl.ID, ecl.PLATFORM_CODE, bp.PLAT_NAME AS PLATFORM_NAME, ecl.TABLE_NAME, ecl.CHG_TYPE, ecl.UPDATE_TIME, ad.USERNAME AS UPDATE_AID, ecl.UPDATE_TIME FROM EP_CHANGE_LOG ecl LEFT JOIN ADMIN ad ON ad.ID = ecl.UPDATE_AID LEFT JOIN BUS_PLATFORM bp ON bp.`CODE` = ecl.PLATFORM_CODE ORDER BY ecl.UPDATE_TIME DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new EpChangeLogMapper(), (page - 1) * pageSize, pageSize);
    }

    public void addTransLog(String platformCode, String tableName, int result, int transType, String content, String failedContent, String remarks, int amount) {
        String sql = "INSERT INTO BUS_DATA_TRANS_LOG ( ID, PLATFORM_CODE, TABLE_NAME, TRANS_TYPE, CONTENT, FAILED_CONTENT, STATUS, CREATE_TIME, REMARKS, AMOUNT ) VALUES (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), platformCode, tableName, transType, content, failedContent, result, System.currentTimeMillis(), remarks, amount);
    }

    public BusDataTransLog queryDataTransLog(String id) {
        String sql = "SELECT ID, PLATFORM_CODE, TABLE_NAME, TRANS_TYPE, CONTENT, FAILED_CONTENT, STATUS, CREATE_TIME, REMARKS FROM BUS_DATA_TRANS_LOG WHERE ID = ?";
        List<BusDataTransLog> list = jdbcTemplate.query(sql, new BusDataTransLogMapper(), id);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
