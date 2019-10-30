package net.ys.dao;

import net.ys.bean.Admin;
import net.ys.bean.BusCustomField;
import net.ys.bean.SimpleField;
import net.ys.mapper.BusCustomFieldMapper;
import net.ys.mapper.SimpleFieldMapper;
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

    public int updateField(Admin admin, BusCustomField busCustomField) {
        String sql = "UPDATE BUS_CUSTOM_FIELD SET IN_CHINESE = ?, FIELD_TYPE = ?, FIELD_LENGTH = ?, PRECISIONS = ?, REQUIRED=?, VALUE_FIELD = ?,UNIQUE_KEY = ?, EXPOSED = ?, REMARKS = ?, UPDATE_AID = ?, UPDATE_TIME = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, busCustomField.getInChinese(), busCustomField.getFieldType(), busCustomField.getFieldLength(), busCustomField.getPrecisions(), busCustomField.getRequired(), busCustomField.getValueField(), busCustomField.getUniqueKey(), busCustomField.getExposed(), busCustomField.getRemarks(), admin.getId(), System.currentTimeMillis(), busCustomField.getId());
    }

    public boolean addField(BusCustomField busCustomField, long time) {
        String sql = "INSERT INTO BUS_CUSTOM_FIELD ( ID, TABLE_NAME, FIELD_NAME, REAL_FIELD_NAME, IN_CHINESE, FIELD_TYPE, FIELD_LENGTH, PRECISIONS, REQUIRED, VALUE_FIELD, UNIQUE_KEY, EXPOSED, REMARKS, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
        return jdbcTemplate.update(sql, busCustomField.getId(), busCustomField.getTableName(), busCustomField.getFieldName(), busCustomField.getRealFieldName(), busCustomField.getInChinese(), busCustomField.getFieldType(), busCustomField.getFieldLength(), busCustomField.getPrecisions(), busCustomField.getRequired(), busCustomField.getValueField(), busCustomField.getUniqueKey(), busCustomField.getExposed(), busCustomField.getRemarks(), busCustomField.getCreateAid(), time, busCustomField.getCreateAid(), time) > 0;
    }

    public boolean removeField(String tableName, String fieldName) {
        String sql = "DELETE FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = '" + tableName + "' AND FIELD_NAME = '" + fieldName + "'";
        return jdbcTemplate.update(sql) > 0;
    }

    public List<BusCustomField> queryFieldFront(String tableName) {
        String sql = "SELECT ID, TABLE_NAME, REAL_FIELD_NAME, FIELD_NAME, IN_CHINESE, FIELD_TYPE, FIELD_LENGTH, PRECISIONS, REQUIRED, VALUE_FIELD, UNIQUE_KEY, EXPOSED, REMARKS, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName);
    }

    public List<Map<String, Object>> queryFieldsFront(String tableName) {
        String sql = "SELECT REAL_FIELD_NAME, FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, tableName);
    }

    public List<Map<String, Object>> queryFieldsFilter(String tableName) {
        String sql = "SELECT REAL_FIELD_NAME, FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ? AND FIELD_NAME !='SYS__ID' AND FIELD_NAME !='SYS__CREATE_TIME' AND FIELD_NAME != 'SYS__PLATFORM_CODE' AND FIELD_NAME != 'SYS__DATETIME_STAMP'";
        return jdbcTemplate.queryForList(sql, tableName);
    }

    public long queryFieldCount(String tableName, String fieldName) {
        String sql = "SELECT COUNT(*) FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        if (!"".equals(fieldName)) {
            sql += " AND FIELD_NAME LIKE '%" + fieldName + "%'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class, tableName);
    }

    public BusCustomField queryField(String fieldName, String tableName) {
        String sql = "SELECT bcf.ID, bcf.TABLE_NAME, bcf.REAL_FIELD_NAME, bcf.FIELD_NAME, bcf.IN_CHINESE, bcf.FIELD_TYPE, bcf.FIELD_LENGTH, bcf.PRECISIONS, bcf.REQUIRED, bcf.VALUE_FIELD, bcf.UNIQUE_KEY, bcf.EXPOSED, bcf.REMARKS, a.USERNAME AS CREATE_AID, bcf.CREATE_TIME, b.USERNAME AS UPDATE_AID, bcf.UPDATE_TIME FROM BUS_CUSTOM_FIELD bcf LEFT JOIN ADMIN a ON a.ID = bcf.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bcf.UPDATE_AID WHERE bcf.TABLE_NAME = ? AND bcf.FIELD_NAME = ?";
        List<BusCustomField> list = jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName, fieldName);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<SimpleField> querySimpleFields(String tableName, int fieldNumLimit) {
        String sql = "SELECT REAL_FIELD_NAME, FIELD_NAME, IN_CHINESE FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ? AND FIELD_NAME != ? AND FIELD_NAME != ? AND FIELD_NAME != ? AND FIELD_NAME != ? LIMIT ?";
        return jdbcTemplate.query(sql, new SimpleFieldMapper(), tableName, "SYS__PLATFORM_CODE", "SYS__CREATE_TIME", "SYS__DATETIME_STAMP", "SYS__ID", fieldNumLimit);
    }
}
