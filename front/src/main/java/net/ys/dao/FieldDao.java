package net.ys.dao;

import net.ys.bean.BusCustomField;
import net.ys.mapper.BusCustomFieldMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class FieldDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<BusCustomField> queryFields(String tableName, String fieldName, int page, int pageSize) {
        String sql = "SELECT bcf.ID, bcf.TABLE_NAME, bcf.REAL_FIELD_NAME, bcf.FIELD_NAME, bcf.IN_CHINESE, bcf.FIELD_TYPE, bcf.FIELD_LENGTH, bcf.PRECISIONS, bcf.REQUIRED, bcf.VALUE_FIELD, bcf.UNIQUE_KEY, bcf.EXPOSED, bcf.REMARKS, a.USERNAME AS CREATE_AID, bcf.CREATE_TIME, b.USERNAME AS UPDATE_AID, bcf.UPDATE_TIME FROM BUS_CUSTOM_FIELD bcf LEFT JOIN ADMIN a ON a.ID = bcf.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bcf.UPDATE_AID WHERE bcf.TABLE_NAME = ?";
        if (!"".equals(fieldName)) {
            sql += " AND bcf.FIELD_NAME LIKE '%" + fieldName + "%'";
        }
        sql += " LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName, (page - 1) * pageSize, pageSize);
    }

    public List<BusCustomField> queryFieldsFront(String tableName, int page, int pageSize) {
        String sql = "SELECT bcf.ID, bcf.TABLE_NAME, bcf.REAL_FIELD_NAME, bcf.FIELD_NAME, bcf.IN_CHINESE, bcf.FIELD_TYPE, bcf.FIELD_LENGTH, bcf.PRECISIONS, bcf.REQUIRED, bcf.VALUE_FIELD, bcf.UNIQUE_KEY, bcf.EXPOSED, bcf.REMARKS, '' AS CREATE_AID, bcf.CREATE_TIME, '' AS UPDATE_AID, bcf.UPDATE_TIME FROM BUS_CUSTOM_FIELD bcf WHERE bcf.TABLE_NAME = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName, (page - 1) * pageSize, pageSize);
    }

    public List<BusCustomField> queryFieldFront(String tableName) {
        String sql = "SELECT ID, TABLE_NAME, REAL_FIELD_NAME, FIELD_NAME, IN_CHINESE, FIELD_TYPE, FIELD_LENGTH, PRECISIONS, REQUIRED, VALUE_FIELD, UNIQUE_KEY, EXPOSED, REMARKS, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName);
    }

    public List<Map<String, Object>> queryFieldsFront(String tableName) {
        String sql = "SELECT REAL_FIELD_NAME, FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, tableName);
    }

    public long queryFieldCount(String tableName, String fieldName) {
        String sql = "SELECT COUNT(*) FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        if (!"".equals(fieldName)) {
            sql += " AND FIELD_NAME LIKE '%" + fieldName + "%'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class, tableName);
    }

}
