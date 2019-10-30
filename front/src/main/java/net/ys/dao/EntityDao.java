package net.ys.dao;

import net.ys.bean.BusCustomEntity;
import net.ys.mapper.BusCustomEntityMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class EntityDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addTablePack(String tableName) {
        String sql = "INSERT INTO BUS_TABLE_PACK (ID, TABLE_NAME, LAST_PACK_TIME) VALUES (?,?,?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), tableName, 0);
    }

    public BusCustomEntity queryEntityFront(String tableName) {
        String sql = "SELECT ID, TABLE_NAME, REAL_TAB_NAME, IN_CHINESE, CALLBACK_URL, DESCRIPTION, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME, RELEASE_AID, RELEASE_TIME FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME = ? LIMIT 1";
        List<BusCustomEntity> list = jdbcTemplate.query(sql, new BusCustomEntityMapper(), tableName);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean queryEntityRelease(String tableName) {
        String sql = "SELECT RELEASE_TIME FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, tableName);
        if (list.size() > 0) {
            return !"0".equals(String.valueOf(list.get(0).get("RELEASE_TIME")));
        }
        return false;
    }

    public long queryEntityCountByTabName(String tableName) {
        String sql = "SELECT COUNT(*) FROM BUS_CUSTOM_ENTITY";
        if (!"".equals(tableName)) {
            sql += " WHERE TABLE_NAME LIKE '%" + tableName + "%'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<BusCustomEntity> queryEntitiesFront(int page, int pageSize) {
        String sql = "SELECT bce.ID, bce.TABLE_NAME, bce.REAL_TAB_NAME, bce.IN_CHINESE, bce.CALLBACK_URL, bce.DESCRIPTION, '' AS CREATE_AID, bce.CREATE_TIME, '' AS UPDATE_AID, bce.UPDATE_TIME, '' AS RELEASE_AID, bce.RELEASE_TIME FROM BUS_CUSTOM_ENTITY bce LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusCustomEntityMapper(), (page - 1) * pageSize, pageSize);
    }

    public void executeSql(String sql) {
        jdbcTemplate.execute(sql);
    }

    public String queryEntityRealName(String tableName) {
        String sql = "SELECT REAL_TAB_NAME FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, tableName);
        if (list.size() > 0) {
            return String.valueOf(list.get(0).get("REAL_TAB_NAME"));
        }
        return null;
    }
}
