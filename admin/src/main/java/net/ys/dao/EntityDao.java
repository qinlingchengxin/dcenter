package net.ys.dao;

import net.ys.bean.Admin;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusCustomField;
import net.ys.bean.PlatEntFilter;
import net.ys.mapper.BusCustomEntityMapper;
import net.ys.mapper.BusCustomFieldMapper;
import net.ys.mapper.PlatEntFilterMapper;
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

    public List<Map<String, Object>> queryEntityList() {
        String sql = "SELECT TABLE_NAME, IN_CHINESE FROM BUS_CUSTOM_ENTITY";
        return jdbcTemplate.queryForList(sql);
    }

    public boolean updateEntity(BusCustomEntity busCustomEntity, boolean chgUpt) {
        String sql = "UPDATE BUS_CUSTOM_ENTITY SET IN_CHINESE = ?, CALLBACK_URL = ?, DESCRIPTION = ?, UPDATE_AID = ? WHERE ID = ?";
        if (chgUpt) {
            sql = "UPDATE BUS_CUSTOM_ENTITY SET IN_CHINESE = ?, CALLBACK_URL = ?, DESCRIPTION = ?, UPDATE_AID = ?, UPDATE_TIME = " + System.currentTimeMillis() + " WHERE ID = ?";
        }
        return jdbcTemplate.update(sql, busCustomEntity.getInChinese(), busCustomEntity.getCallbackUrl(), busCustomEntity.getDescription(), busCustomEntity.getUpdateAid(), busCustomEntity.getId()) >= 0;
    }

    public boolean addEntity(BusCustomEntity busCustomEntity, long time) {
        String sql = "INSERT INTO BUS_CUSTOM_ENTITY ( ID, TABLE_NAME, REAL_TAB_NAME, IN_CHINESE, CALLBACK_URL, DESCRIPTION, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME ) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, busCustomEntity.getId(), busCustomEntity.getTableName(), busCustomEntity.getRealTabName(), busCustomEntity.getInChinese(), busCustomEntity.getCallbackUrl(), busCustomEntity.getDescription(), busCustomEntity.getCreateAid(), time, busCustomEntity.getCreateAid(), time) > 0;
    }

    public List<BusCustomField> queryFields(String tableName) {
        String sql = "SELECT bcf.ID, bcf.TABLE_NAME, bcf.REAL_FIELD_NAME, bcf.FIELD_NAME, bcf.IN_CHINESE, bcf.FIELD_TYPE, bcf.FIELD_LENGTH, bcf.PRECISIONS, bcf.REQUIRED, bcf.VALUE_FIELD, bcf.UNIQUE_KEY, bcf.EXPOSED, bcf.REMARKS, a.USERNAME AS CREATE_AID, bcf.CREATE_TIME, b.USERNAME AS UPDATE_AID, bcf.UPDATE_TIME FROM BUS_CUSTOM_FIELD bcf LEFT JOIN ADMIN a ON a.ID = bcf.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bcf.UPDATE_AID WHERE bcf.TABLE_NAME = ?";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName);
    }

    public boolean updateEntityUpdateTime(String tableName) {
        String sql = "UPDATE BUS_CUSTOM_ENTITY SET UPDATE_TIME = ? WHERE TABLE_NAME = ?";
        return jdbcTemplate.update(sql, System.currentTimeMillis(), tableName) > 0;
    }

    public List<BusCustomField> queryUpdateFields(String tableName) {
        String sql = "SELECT bcf.ID, bcf.TABLE_NAME, bcf.FIELD_NAME, bcf.REAL_FIELD_NAME, bcf.IN_CHINESE, bcf.FIELD_TYPE, bcf.FIELD_LENGTH, bcf.PRECISIONS, bcf.REQUIRED, bcf.VALUE_FIELD, bcf.UNIQUE_KEY, bcf.EXPOSED, bcf.REMARKS, a.USERNAME AS CREATE_AID, bcf.CREATE_TIME, b.USERNAME AS UPDATE_AID, bcf.UPDATE_TIME FROM BUS_CUSTOM_FIELD bcf LEFT JOIN ADMIN a ON a.ID = bcf.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bcf.UPDATE_AID WHERE bcf.TABLE_NAME = ? AND bcf.UPDATE_TIME > bcf.RELEASE_TIME";
        return jdbcTemplate.query(sql, new BusCustomFieldMapper(), tableName);
    }

    public boolean updateReleaseStatus(Admin admin, String tableName, long time) {
        String[] sql = new String[3];
        sql[0] = "UPDATE BUS_CUSTOM_ENTITY SET RELEASE_AID = '" + admin.getId() + "', RELEASE_TIME = " + time + " WHERE TABLE_NAME = '" + tableName + "'";
        sql[1] = "UPDATE BUS_CUSTOM_FIELD SET RELEASE_TIME = " + time + " WHERE UPDATE_TIME > RELEASE_TIME AND TABLE_NAME = '" + tableName + "'";
        sql[2] = "UPDATE BUS_SYNC_TABLE SET RELEASE_TIME = " + time + " WHERE TABLE_NAME = '" + tableName + "'";
        int[] result = jdbcTemplate.batchUpdate(sql);
        if (result[0] + result[1] < 1) {
            throw new RuntimeException();
        }
        return true;
    }

    public void addTablePack(String tableName) {
        String sql = "INSERT INTO BUS_TABLE_PACK (ID, TABLE_NAME, LAST_PACK_TIME) VALUES (?,?,?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), tableName, 0);
    }

    public boolean removeEntity(String tableName) {
        String[] sql = new String[2];
        sql[0] = "DELETE FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME = '" + tableName + "'";
        sql[1] = "DELETE FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = '" + tableName + "'";
        int[] result = jdbcTemplate.batchUpdate(sql);

        if (result[0] + result[1] < 2) {
            throw new RuntimeException();
        }
        return true;
    }

    public boolean queryEntityRelease(String tableName) {
        String sql = "SELECT RELEASE_TIME FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, tableName);
        if (list.size() > 0) {
            return !"0".equals(String.valueOf(list.get(0).get("RELEASE_TIME")));
        }
        return false;
    }

    public List<Map<String, Object>> queryEntityByPlatCode(String platformCode) {
        String sql = "SELECT TABLE_NAME, IN_CHINESE FROM BUS_CUSTOM_ENTITY bce WHERE TABLE_NAME IN ( SELECT DISTINCT TABLE_NAME FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? )";
        return jdbcTemplate.queryForList(sql, platformCode);
    }

    public long queryEntityCount(String platformCode) {
        String sql = "SELECT COUNT(DISTINCT TABLE_NAME) FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, platformCode);
    }

    public long queryEntityCountByTabName(String tableName) {
        String sql = "SELECT COUNT(*) FROM BUS_CUSTOM_ENTITY";
        if (!"".equals(tableName)) {
            sql += " WHERE TABLE_NAME LIKE '%" + tableName + "%'";
        }
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<BusCustomEntity> queryEntities(String platformCode, int page, int pageSize) {
        String sql = "SELECT bce.ID, bce.TABLE_NAME, bce.REAL_TAB_NAME, bce.IN_CHINESE, bce.CALLBACK_URL, bce.DESCRIPTION, a.USERNAME AS CREATE_AID, bce.CREATE_TIME, b.USERNAME AS UPDATE_AID, bce.UPDATE_TIME, c.USERNAME AS RELEASE_AID, bce.RELEASE_TIME FROM BUS_CUSTOM_ENTITY bce LEFT JOIN ADMIN a ON a.ID = bce.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bce.UPDATE_AID LEFT JOIN ADMIN c ON c.ID = bce.RELEASE_AID WHERE TABLE_NAME IN ( SELECT DISTINCT TABLE_NAME FROM ENTITY_PRIVILEGE WHERE PLATFORM_CODE = ? ) LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusCustomEntityMapper(), platformCode, (page - 1) * pageSize, pageSize);
    }

    public List<BusCustomEntity> queryEntitiesByTab(String tableName, int page, int pageSize) {
        String sql = "SELECT bce.ID, bce.TABLE_NAME, bce.REAL_TAB_NAME, bce.IN_CHINESE, bce.CALLBACK_URL, bce.DESCRIPTION, a.USERNAME AS CREATE_AID, bce.CREATE_TIME, b.USERNAME AS UPDATE_AID, bce.UPDATE_TIME, c.USERNAME AS RELEASE_AID, bce.RELEASE_TIME FROM BUS_CUSTOM_ENTITY bce LEFT JOIN ADMIN a ON a.ID = bce.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bce.UPDATE_AID LEFT JOIN ADMIN c ON c.ID = bce.RELEASE_AID";
        if (!"".equals(tableName)) {
            sql += " WHERE bce.TABLE_NAME LIKE '%" + tableName + "%'";
        }
        sql += " LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusCustomEntityMapper(), (page - 1) * pageSize, pageSize);
    }

    public BusCustomEntity queryEntity(String tableName) {
        String sql = "SELECT bce.ID, bce.TABLE_NAME, bce.REAL_TAB_NAME, bce.IN_CHINESE, bce.CALLBACK_URL, bce.DESCRIPTION, a.USERNAME AS CREATE_AID, bce.CREATE_TIME, b.USERNAME AS UPDATE_AID, bce.UPDATE_TIME, c.USERNAME AS RELEASE_AID, bce.RELEASE_TIME FROM BUS_CUSTOM_ENTITY bce LEFT JOIN ADMIN a ON a.ID = bce.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bce.UPDATE_AID LEFT JOIN ADMIN c ON c.ID = bce.RELEASE_AID WHERE bce.TABLE_NAME = ?";
        List<BusCustomEntity> list = jdbcTemplate.query(sql, new BusCustomEntityMapper(), tableName);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void executeSql(String sql) {
        jdbcTemplate.execute(sql);
    }

    public long queryDataFilterCount(String epId) {
        String sql = "SELECT COUNT(*) FROM PLAT_ENT_FILTER WHERE EP_ID = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, epId);
    }

    public List<PlatEntFilter> queryPlatEntFilters(String epId, int page, int pageSize) {
        String sql = "SELECT pef.ID, pef.EP_ID, ep.TABLE_NAME, pef.FIELD, pef.`CONDITION`, pef.AN, pef.CON_VALUE FROM PLAT_ENT_FILTER pef, ENTITY_PRIVILEGE ep WHERE ep.ID = pef.EP_ID AND pef.EP_ID = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new PlatEntFilterMapper(), epId, (page - 1) * pageSize, pageSize);
    }

    public PlatEntFilter queryPlatEntFilter(String pnId) {
        String sql = "SELECT pef.ID, pef.EP_ID, ep.TABLE_NAME, pef.FIELD, pef.`CONDITION`, pef.AN, pef.CON_VALUE FROM PLAT_ENT_FILTER pef, ENTITY_PRIVILEGE ep WHERE ep.ID = pef.EP_ID AND pef.ID = ?";
        List<PlatEntFilter> list = jdbcTemplate.query(sql, new PlatEntFilterMapper(), pnId);
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    public String queryTableNameByEp(String epId) {
        String sql = "SELECT TABLE_NAME FROM ENTITY_PRIVILEGE WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, String.class, epId);
    }

    public boolean updatePlatEntFilter(PlatEntFilter platEntFilter) {
        String sql = "UPDATE PLAT_ENT_FILTER SET FIELD = ?, `CONDITION` = ?, AN = ?, CON_VALUE = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, platEntFilter.getField(), platEntFilter.getCondition(), platEntFilter.getAn(), platEntFilter.getConValue(), platEntFilter.getId()) >= 0;
    }

    public PlatEntFilter addPlatEntFilter(PlatEntFilter platEntFilter) {
        platEntFilter.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO PLAT_ENT_FILTER (ID, EP_ID, FIELD, `CONDITION`, AN, CON_VALUE) VALUES (?,?,?,?,?,?)";
        boolean flag = jdbcTemplate.update(sql, platEntFilter.getId(), platEntFilter.getEpId(), platEntFilter.getField(), platEntFilter.getCondition(), platEntFilter.getAn(), platEntFilter.getConValue()) > 0;
        if (flag) {
            return platEntFilter;
        }

        return null;
    }

    public boolean dataFilterRemove(String id) {
        String sql = "DELETE FROM PLAT_ENT_FILTER WHERE ID = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
