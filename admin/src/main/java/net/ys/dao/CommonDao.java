package net.ys.dao;

import net.sf.json.JSONNull;
import net.ys.bean.Admin;
import net.ys.bean.BusAttachment;
import net.ys.bean.EntityPrivilege;
import net.ys.bean.PlatEntFilter;
import net.ys.mapper.BusAttachmentMapper;
import net.ys.mapper.EntityPrivilegeMapper;
import net.ys.mapper.PlatEntFilterMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class CommonDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public long queryPriCount(String platformCode, int type) {
        String sql = "SELECT COUNT(*) FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? AND PRI_TYPE = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, platformCode, type);
    }

    public long queryPriCount(String platformCode, String tableName) {
        String sql = "SELECT COUNT(*) FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? AND TABLE_NAME = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, platformCode, tableName);
    }

    public List<EntityPrivilege> queryPris(String platformCode, int type, int page, int pageSize) {
        String sql = "SELECT ep.ID, ep.PLATFORM_CODE, bp.PLAT_NAME, ep.TABLE_NAME, ep.PRI_TYPE, a.USERNAME AS ASSIGNER_AID, ep.ASSIGNER_TIME FROM ENTITY_PRIVILEGE ep LEFT JOIN BUS_PLATFORM bp ON bp.CODE = ep.PLATFORM_CODE LEFT JOIN ADMIN a ON a.ID = ep.ASSIGNER_AID WHERE ep.PLATFORM_CODE = ? AND PRI_TYPE = ? ORDER BY ep.PLATFORM_CODE, ep.TABLE_NAME LIMIT ?,?";
        return jdbcTemplate.query(sql, new EntityPrivilegeMapper(), platformCode, type, (page - 1) * pageSize, pageSize);
    }

    public List<Map<String, Object>> queryTableList(String platformCode, int priType) {
        String sql = "SELECT TABLE_NAME, IN_CHINESE FROM BUS_CUSTOM_ENTITY WHERE RELEASE_TIME >= UPDATE_TIME AND TABLE_NAME NOT IN ( SELECT TABLE_NAME FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? AND PRI_TYPE = ? )";
        return jdbcTemplate.queryForList(sql, platformCode, priType);
    }

    public boolean queryPriExist(EntityPrivilege entityPrivilege) {
        String sql = "SELECT COUNT(*) FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? AND TABLE_NAME = ? AND PRI_TYPE = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, entityPrivilege.getPlatformCode(), entityPrivilege.getTableName(), entityPrivilege.getPriType()) > 0;
    }

    public boolean addPri(Admin admin, EntityPrivilege entityPrivilege) {
        String sql = "INSERT INTO ENTITY_PRIVILEGE ( ID, PLATFORM_CODE, TABLE_NAME, PRI_TYPE, ASSIGNER_AID, ASSIGNER_TIME ) VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, UUID.randomUUID().toString(), entityPrivilege.getPlatformCode(), entityPrivilege.getTableName(), entityPrivilege.getPriType(), admin.getId(), System.currentTimeMillis()) > 0;
    }

    public boolean removePri(String id) {
        String sql = "DELETE FROM ENTITY_PRIVILEGE WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public void addPriChgLog(Admin admin, EntityPrivilege entityPrivilege, int type) {
        String sql = "INSERT INTO EP_CHANGE_LOG ( ID, PLATFORM_CODE, TABLE_NAME, CHG_TYPE, UPDATE_AID, UPDATE_TIME ) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), entityPrivilege.getPlatformCode(), entityPrivilege.getTableName(), type, admin.getId(), System.currentTimeMillis());
    }

    public EntityPrivilege queryPri(String id) {
        String sql = "SELECT ID, PLATFORM_CODE, '' AS PLAT_NAME, TABLE_NAME, PRI_TYPE, '' AS ASSIGNER_AID, ASSIGNER_TIME FROM ENTITY_PRIVILEGE WHERE ID = ?";
        List<EntityPrivilege> list = jdbcTemplate.query(sql, new EntityPrivilegeMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long queryEntityDataCount(String tableName, String platformCode) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        if (!"".equals(platformCode)) {
            sql = sql + " WHERE SYS__PLATFORM_CODE = '" + platformCode + "'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<Map<String, Object>> queryEntityDataList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> queryEntityData(String sql) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long queryAttCount(String platformCode, String tableName, String fieldName) {
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM BUS_ATTACHMENT WHERE 1=1");

        if (!"".equals(platformCode)) {
            sql.append(" AND PLATFORM_CODE = '").append(platformCode).append("'");
        }

        if (!"".equals(tableName)) {
            sql.append(" AND TABLE_NAME = '").append(tableName).append("'");
        }

        if (!"".equals(fieldName)) {
            sql.append(" AND FIELD_NAME = '").append(fieldName).append("'");
        }

        return jdbcTemplate.queryForObject(sql.toString(), Long.class);
    }

    public List<BusAttachment> queryAttachments(String platformCode, String tableName, String fieldName, int page, int pageSize) {
        StringBuffer sql = new StringBuffer("SELECT ID, PATH, ATT_NAME, SRC_NAME, PLATFORM_CODE, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, UPDATE_STATUS, CREATE_TIME FROM BUS_ATTACHMENT WHERE 1=1");

        if (!"".equals(platformCode)) {
            sql.append(" AND PLATFORM_CODE = '").append(platformCode).append("'");
        }

        if (!"".equals(tableName)) {
            sql.append(" AND TABLE_NAME = '").append(tableName).append("'");
        }

        if (!"".equals(fieldName)) {
            sql.append(" AND FIELD_NAME = '").append(fieldName).append("'");
        }

        sql.append(" ORDER BY CREATE_TIME DESC LIMIT ").append((page - 1) * pageSize).append(",").append(pageSize);
        return jdbcTemplate.query(sql.toString(), new BusAttachmentMapper());
    }

    public List<BusAttachment> queryAttachments() {
        String sql = "SELECT ID, PATH, ATT_NAME, SRC_NAME, PLATFORM_CODE, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, UPDATE_STATUS, CREATE_TIME FROM BUS_ATTACHMENT WHERE UPDATE_STATUS = 0 LIMIT 100";
        return jdbcTemplate.query(sql, new BusAttachmentMapper());
    }

    public void addSyncTable(EntityPrivilege entityPrivilege) {
        String sql = "INSERT INTO BUS_SYNC_TABLE ( ID, PLATFORM_CODE, TABLE_NAME, RELEASE_TIME ) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), entityPrivilege.getPlatformCode(), entityPrivilege.getTableName(), System.currentTimeMillis());
    }

    public boolean querySyncTable(EntityPrivilege entityPrivilege) {
        String sql = "SELECT COUNT(*) FROM BUS_SYNC_TABLE WHERE PLATFORM_CODE = ? AND TABLE_NAME = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, entityPrivilege.getPlatformCode(), entityPrivilege.getTableName()) > 0;
    }

    public void removeSyncTable(EntityPrivilege entityPrivilege) {
        String sql = "DELETE FROM BUS_SYNC_TABLE WHERE PLATFORM_CODE = ? AND TABLE_NAME = ?";
        jdbcTemplate.update(sql, entityPrivilege.getPlatformCode(), entityPrivilege.getTableName());
    }

    public boolean addAttachment(BusAttachment attachment) {
        String sql = "INSERT INTO BUS_ATTACHMENT ( ID, PATH, ATT_NAME, SRC_NAME, PLATFORM_CODE, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, CREATE_TIME ) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, attachment.getId(), attachment.getPath(), attachment.getAttName(), attachment.getSrcName(), attachment.getPlatformCode(), attachment.getTableName(), attachment.getFieldName(), attachment.getDataPriField(), attachment.getDataId(), attachment.getCreateTime()) > 0;
    }

    public boolean batAddData(String sql, final List<Map<String, Object>> dataList) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> data = dataList.get(i);
                int index = 1;
                Object o;
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    o = entry.getValue();
                    if (o instanceof JSONNull) {
                        ps.setObject(index, null);
                    } else {
                        ps.setObject(index, o);
                    }
                    index++;
                }
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });
        return true;
    }

    public List<Map<String, Object>> sliceSyncData(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    public String queryTablePrivilege(String tableName, String platformCode, int priType) {
        String sql = "SELECT bce.REAL_TAB_NAME FROM ENTITY_PRIVILEGE ep, BUS_CUSTOM_ENTITY bce WHERE ep.TABLE_NAME = bce.TABLE_NAME AND ep.PLATFORM_CODE = ? AND ep.PRI_TYPE = ? AND bce.TABLE_NAME = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, platformCode, priType, tableName);
        if (list.size() > 0) {
            return String.valueOf(list.get(0).get("REAL_TAB_NAME"));
        }
        return "";
    }

    public List<Map<String, Object>> queryDataList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    public List<PlatEntFilter> queryFilters(String platformCode) {
        String sql = "SELECT pef.ID, pef.EP_ID, ep.TABLE_NAME, pef.FIELD, pef.`CONDITION`, pef.AN, pef.CON_VALUE FROM PLAT_ENT_FILTER pef, ENTITY_PRIVILEGE ep WHERE ep.ID = pef.EP_ID AND ep.PLATFORM_CODE = ?";
        return jdbcTemplate.query(sql, new PlatEntFilterMapper(), platformCode);
    }

    public List<PlatEntFilter> queryFilters(String platformCode, String tableName) {
        String sql = "SELECT pef.ID, pef.EP_ID, ep.TABLE_NAME, pef.FIELD, pef.`CONDITION`, pef.AN, pef.CON_VALUE FROM PLAT_ENT_FILTER pef, ENTITY_PRIVILEGE ep WHERE ep.ID = pef.EP_ID AND ep.PLATFORM_CODE = ? AND ep.TABLE_NAME = ?";
        return jdbcTemplate.query(sql, new PlatEntFilterMapper(), platformCode, tableName);
    }

    public void updateData(String[] sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    public List<Map<String, Object>> queryRealFields(BusAttachment busAttachment) {
        String sql = "SELECT REAL_FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ? AND FIELD_NAME = ? UNION ALL SELECT REAL_FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ? AND FIELD_NAME = ?";
        return jdbcTemplate.queryForList(sql, busAttachment.getTableName(), busAttachment.getDataPriField(), busAttachment.getTableName(), busAttachment.getFieldName());
    }

    public BusAttachment queryAttachment(String id) {
        String sql = "SELECT ID, PATH, ATT_NAME, SRC_NAME, PLATFORM_CODE, TABLE_NAME, FIELD_NAME, DATA_PRI_FIELD, DATA_ID, UPDATE_STATUS, CREATE_TIME FROM BUS_ATTACHMENT WHERE ID = ?";
        List<BusAttachment> list = jdbcTemplate.query(sql, new BusAttachmentMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
