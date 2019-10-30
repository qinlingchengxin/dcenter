package net.ys.dao;

import net.ys.bean.*;
import net.ys.mapper.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class EtlDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public long queryEtlEntityCount(String prjId) {
        String sql = "SELECT COUNT(*) FROM ETL_ENTITY WHERE PRJ_ID = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, prjId);
    }

    public List<EtlEntity> queryEtlEntities(String prjId, int page, int pageSize) {
        String sql = "SELECT ID, PRJ_ID, SRC_TAB_NAME, DES_TAB_NAME, SRC_PRIMARY_KEY, DES_PRIMARY_KEY, DESCRIPTION, ETL_ID, CREATE_TIME, `REPEAT`, SCHEDULE_TYPE, INTERVAL_SECOND, INTERVAL_MINUTE, FIXED_HOUR, FIXED_MINUTE, FIXED_WEEKDAY, FIXED_DAY, IS_EXEC, `CONDITION` FROM ETL_ENTITY WHERE PRJ_ID = ? ORDER BY SRC_TAB_NAME LIMIT ?,?";
        return jdbcTemplate.query(sql, new EtlEntityMapper(), prjId, (page - 1) * pageSize, pageSize);
    }

    public EtlEntity queryEtlEntity(String id) {
        String sql = "SELECT ID, PRJ_ID, SRC_TAB_NAME, DES_TAB_NAME, SRC_PRIMARY_KEY, DES_PRIMARY_KEY, DESCRIPTION, ETL_ID, CREATE_TIME, `REPEAT`, SCHEDULE_TYPE, INTERVAL_SECOND, INTERVAL_MINUTE, FIXED_HOUR, FIXED_MINUTE, FIXED_WEEKDAY, FIXED_DAY, IS_EXEC, `CONDITION` FROM ETL_ENTITY WHERE ID = ?";
        List<EtlEntity> list = jdbcTemplate.query(sql, new EtlEntityMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean updateEtlEntity(EtlEntity etlEntity) {
        String sql = "UPDATE ETL_ENTITY SET SRC_TAB_NAME = ?, DES_TAB_NAME = ?, SRC_PRIMARY_KEY = ?, DES_PRIMARY_KEY = ?, DESCRIPTION = ?, `REPEAT` = ?, SCHEDULE_TYPE = ?, INTERVAL_SECOND = ?, INTERVAL_MINUTE = ?, FIXED_HOUR = ?, FIXED_MINUTE = ?, FIXED_WEEKDAY = ?, FIXED_DAY = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, etlEntity.getSrcTabName(), etlEntity.getDesTabName(), etlEntity.getSrcPrimaryKey(), etlEntity.getDesPrimaryKey(), etlEntity.getDescription(), etlEntity.getRepeat(), etlEntity.getScheduleType(), etlEntity.getIntervalSecond(), etlEntity.getIntervalMinute(), etlEntity.getFixedHour(), etlEntity.getFixedMinute(), etlEntity.getFixedWeekday(), etlEntity.getFixedDay(), etlEntity.getId()) >= 0;
    }

    public EtlEntity addEtlEntity(EtlEntity etlEntity) {
        etlEntity.setId(UUID.randomUUID().toString());
        etlEntity.setEtlId(String.valueOf(System.currentTimeMillis()));
        String sql = "INSERT INTO ETL_ENTITY ( ID, PRJ_ID, SRC_TAB_NAME, DES_TAB_NAME, SRC_PRIMARY_KEY, DES_PRIMARY_KEY, DESCRIPTION, ETL_ID, CREATE_TIME, `REPEAT`, SCHEDULE_TYPE, INTERVAL_SECOND, INTERVAL_MINUTE, FIXED_HOUR, FIXED_MINUTE, FIXED_WEEKDAY, FIXED_DAY  ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean flag = jdbcTemplate.update(sql, etlEntity.getId(), etlEntity.getPrjId(), etlEntity.getSrcTabName(), etlEntity.getDesTabName(), etlEntity.getSrcPrimaryKey(), etlEntity.getDesPrimaryKey(), etlEntity.getDescription(), etlEntity.getEtlId(), System.currentTimeMillis(), etlEntity.getRepeat(), etlEntity.getScheduleType(), etlEntity.getIntervalSecond(), etlEntity.getIntervalMinute(), etlEntity.getFixedHour(), etlEntity.getFixedMinute(), etlEntity.getFixedWeekday(), etlEntity.getFixedDay()) > 0;
        if (flag) {
            return etlEntity;
        }
        return null;
    }

    public long queryEtlFieldCount(String entityId) {
        String sql = "SELECT COUNT(*) FROM ETL_FIELD WHERE ENTITY_ID = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, entityId);
    }

    public List<EtlField> queryEtlFields(String entityId, int page, int pageSize) {
        String sql = "SELECT ID, ENTITY_ID, SRC_FIELD_NAME, DES_FIELD_NAME, CREATE_TIME FROM ETL_FIELD WHERE ENTITY_ID = ? ORDER BY SRC_FIELD_NAME LIMIT ?,?";
        return jdbcTemplate.query(sql, new EtlFieldMapper(), entityId, (page - 1) * pageSize, pageSize);
    }

    public List<EtlField> queryEtlKtrFields(String entityId) {
        String sql = "SELECT ID, ENTITY_ID, SRC_FIELD_NAME, DES_FIELD_NAME, CREATE_TIME FROM ETL_FIELD ef WHERE ef.ENTITY_ID = ? ORDER BY CREATE_TIME";
        return jdbcTemplate.query(sql, new EtlFieldMapper(), entityId);
    }

    public EtlField addEtlField(EtlField etlField) {
        etlField.setId(UUID.randomUUID().toString());
        String sql = "INSERT IGNORE INTO ETL_FIELD ( ID, ENTITY_ID, SRC_FIELD_NAME, DES_FIELD_NAME, CREATE_TIME ) VALUES (?,?,?,?,?)";
        boolean flag = jdbcTemplate.update(sql, etlField.getId(), etlField.getEntityId(), etlField.getSrcFieldName(), etlField.getDesFieldName(), System.currentTimeMillis()) >= 0;

        if (flag) {
            return etlField;
        }

        return null;
    }

    public long queryEtlProjectCount() {
        String sql = "SELECT COUNT(*) FROM ETL_PROJECT WHERE STATUS = 0";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<EtlProject> queryEtlProjects(int page, int pageSize) {
        String sql = "SELECT ep.ID, ep.PRJ_NAME, src_db.ID AS SRC_DB_ID, src_db.SOURCE_NAME AS SRC_DB_NAME, des_db.ID AS DES_DB_ID, des_db.SOURCE_NAME AS DES_DB_NAME, src_db.DB_TYPE AS CENTER_DB_TYPE, src_db.DB_IP AS CENTER_DB_IP, src_db.DB_NAME AS CENTER_DB_NAME, src_db.DB_PORT AS CENTER_DB_PORT, src_db.DB_USER_NAME AS CENTER_DB_USER_NAME, src_db.DB_PWD AS CENTER_DB_PWD, des_db.DB_TYPE AS BUS_DB_TYPE, des_db.DB_IP AS BUS_DB_IP, des_db.DB_NAME AS BUS_DB_NAME, des_db.DB_PORT AS BUS_DB_PORT, des_db.DB_USER_NAME AS BUS_DB_USER_NAME, des_db.DB_PWD AS BUS_DB_PWD, ep.CREATE_TIME FROM ETL_PROJECT ep, ETL_DATA_SOURCE src_db, ETL_DATA_SOURCE des_db WHERE ep.STATUS = 0 AND src_db.ID = ep.SRC_DB_ID AND des_db.ID = ep.DES_DB_ID LIMIT ?,?";
        return jdbcTemplate.query(sql, new EtlProjectMapper(), (page - 1) * pageSize, pageSize);
    }

    public EtlProject queryEtlProject(String id) {
        String sql = "SELECT ep.ID, ep.PRJ_NAME, src_db.ID AS SRC_DB_ID, src_db.SOURCE_NAME AS SRC_DB_NAME, des_db.ID AS DES_DB_ID, des_db.SOURCE_NAME AS DES_DB_NAME, src_db.DB_TYPE AS CENTER_DB_TYPE, src_db.DB_IP AS CENTER_DB_IP, src_db.DB_NAME AS CENTER_DB_NAME, src_db.DB_PORT AS CENTER_DB_PORT, src_db.DB_USER_NAME AS CENTER_DB_USER_NAME, src_db.DB_PWD AS CENTER_DB_PWD, des_db.DB_TYPE AS BUS_DB_TYPE, des_db.DB_IP AS BUS_DB_IP, des_db.DB_NAME AS BUS_DB_NAME, des_db.DB_PORT AS BUS_DB_PORT, des_db.DB_USER_NAME AS BUS_DB_USER_NAME, des_db.DB_PWD AS BUS_DB_PWD, ep.CREATE_TIME FROM ETL_PROJECT ep, ETL_DATA_SOURCE src_db, ETL_DATA_SOURCE des_db WHERE src_db.ID = ep.SRC_DB_ID AND des_db.ID = ep.DES_DB_ID AND ep.ID = ?";
        List<EtlProject> list = jdbcTemplate.query(sql, new EtlProjectMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean updateEtlProject(EtlProject etlProject) {
        String sql = "UPDATE ETL_PROJECT SET PRJ_NAME = ?, SRC_DB_ID = ?, DES_DB_ID = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, etlProject.getPrjName(), etlProject.getSrcDbId(), etlProject.getDesDbId(), etlProject.getId()) >= 0;
    }

    public EtlProject addEtlProject(EtlProject etlProject) {
        etlProject.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO ETL_PROJECT ( ID, PRJ_NAME, SRC_DB_ID, DES_DB_ID, CREATE_TIME ) VALUES ( ?,?,?,?,? )";
        boolean flag = jdbcTemplate.update(sql, etlProject.getId(), etlProject.getPrjName(), etlProject.getSrcDbId(), etlProject.getDesDbId(), System.currentTimeMillis()) > 0;
        if (flag) {
            return etlProject;
        }
        return null;
    }

    public EtlProject queryEtlKtrProject(String entityId) {
        String sql = "SELECT ep.ID, ep.PRJ_NAME, src_db.ID AS SRC_DB_ID, src_db.SOURCE_NAME AS SRC_DB_NAME, des_db.ID AS DES_DB_ID, des_db.SOURCE_NAME AS DES_DB_NAME, src_db.DB_TYPE AS CENTER_DB_TYPE, src_db.DB_IP AS CENTER_DB_IP, src_db.DB_NAME AS CENTER_DB_NAME, src_db.DB_PORT AS CENTER_DB_PORT, src_db.DB_USER_NAME AS CENTER_DB_USER_NAME, src_db.DB_PWD AS CENTER_DB_PWD, des_db.DB_TYPE AS BUS_DB_TYPE, des_db.DB_IP AS BUS_DB_IP, des_db.DB_NAME AS BUS_DB_NAME, des_db.DB_PORT AS BUS_DB_PORT, des_db.DB_USER_NAME AS BUS_DB_USER_NAME, des_db.DB_PWD AS BUS_DB_PWD, ep.CREATE_TIME FROM ETL_PROJECT ep, ETL_DATA_SOURCE src_db, ETL_DATA_SOURCE des_db, ETL_ENTITY ee WHERE src_db.ID = ep.SRC_DB_ID AND des_db.ID = ep.DES_DB_ID AND ep.ID = ee.PRJ_ID AND ee.ID = ?";
        List<EtlProject> list = jdbcTemplate.query(sql, new EtlProjectMapper(), entityId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public long queryEtlDataSourceCount() {
        String sql = "SELECT COUNT(*) FROM ETL_DATA_SOURCE";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<EtlDataSource> queryEtlDataSources(int page, int pageSize) {
        String sql = "SELECT ID, SOURCE_NAME, DB_TYPE, DB_IP, DB_PORT, DB_NAME, DB_USER_NAME, DB_PWD, CREATE_TIME FROM ETL_DATA_SOURCE LIMIT ?,?";
        return jdbcTemplate.query(sql, new EtlDataSourceMapper(), (page - 1) * pageSize, pageSize);
    }

    public EtlDataSource queryEtlDataSource(String id) {
        String sql = "SELECT ID, SOURCE_NAME, DB_TYPE, DB_IP, DB_PORT, DB_NAME, DB_USER_NAME, DB_PWD, CREATE_TIME FROM ETL_DATA_SOURCE WHERE ID = ?";
        List<EtlDataSource> list = jdbcTemplate.query(sql, new EtlDataSourceMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean updateEtlDataSource(EtlDataSource etlDataSource) {
        String sql = "UPDATE ETL_DATA_SOURCE SET SOURCE_NAME = ?, DB_TYPE = ?, DB_IP = ?, DB_PORT = ?, DB_NAME = ?, DB_USER_NAME = ?, DB_PWD = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, etlDataSource.getSourceName(), etlDataSource.getDbType(), etlDataSource.getDbIp(), etlDataSource.getDbPort(), etlDataSource.getDbName(), etlDataSource.getDbUsername(), etlDataSource.getDbPwd(), etlDataSource.getId()) >= 0;
    }

    public EtlDataSource addEtlDataSource(EtlDataSource etlDataSource) {
        etlDataSource.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO ETL_DATA_SOURCE ( ID, SOURCE_NAME, DB_TYPE, DB_IP, DB_PORT, DB_NAME, DB_USER_NAME, DB_PWD, CREATE_TIME ) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean flag = jdbcTemplate.update(sql, etlDataSource.getId(), etlDataSource.getSourceName(), etlDataSource.getDbType(), etlDataSource.getDbIp(), etlDataSource.getDbPort(), etlDataSource.getDbName(), etlDataSource.getDbUsername(), etlDataSource.getDbPwd(), System.currentTimeMillis()) > 0;
        if (flag) {
            return etlDataSource;
        }
        return null;
    }

    public boolean entityDel(String id) {
        String sql = "DELETE FROM ETL_ENTITY WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public boolean projectDel(String id) {
        String sql = "UPDATE ETL_PROJECT SET STATUS = 1 WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public boolean fieldDel(String id) {
        String sql = "DELETE FROM ETL_FIELD WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public boolean chgJobStatus(String entityId, int status) {
        String sql = "UPDATE ETL_ENTITY SET IS_EXEC = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, status, entityId) >= 0;
    }

    public List<EtlAllTable> queryEtlAllTables(String dbId) {
        String sql = "SELECT ID, DS_ID, `NAME`, `COMMENT`, CREATE_TIME FROM ETL_ALL_TABLE WHERE DS_ID =?";
        return jdbcTemplate.query(sql, new EtlAllTableMapper(), dbId);
    }

    public EtlAllTable querySrcEtlAllTable(String prjId, String tabName) {
        String sql = "SELECT eat.ID, eat.DS_ID, eat.`NAME`, eat.`COMMENT`, eat.CREATE_TIME FROM ETL_ALL_TABLE eat, ETL_PROJECT ep WHERE eat.DS_ID = ep.SRC_DB_ID AND eat.`NAME` = ? AND ep.ID = ?";
        List<EtlAllTable> list = jdbcTemplate.query(sql, new EtlAllTableMapper(), tabName, prjId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public EtlAllTable queryDesEtlAllTable(String prjId, String tabName) {
        String sql = "SELECT eat.ID, eat.DS_ID, eat.`NAME`, eat.`COMMENT`, eat.CREATE_TIME FROM ETL_ALL_TABLE eat, ETL_PROJECT ep WHERE eat.DS_ID = ep.DES_DB_ID AND eat.`NAME` = ? AND ep.ID = ?";
        List<EtlAllTable> list = jdbcTemplate.query(sql, new EtlAllTableMapper(), tabName, prjId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<EtlAllField> queryPrimaryKey(String tableId) {
        String sql = "SELECT ID, TABLE_ID, `NAME`, PRI_KEY, `COMMENT`, CREATE_TIME FROM ETL_ALL_FIELD WHERE TABLE_ID = ? AND PRI_KEY = 1";
        return jdbcTemplate.query(sql, new EtlAllFieldMapper(), tableId);
    }

    public List<EtlAllField> queryFields(EtlAllTable table) {
        String sql = "SELECT ID, TABLE_ID, `NAME`, PRI_KEY, `COMMENT`, CREATE_TIME FROM ETL_ALL_FIELD WHERE TABLE_ID = ?";
        return jdbcTemplate.query(sql, new EtlAllFieldMapper(), table.getId());
    }

    public List<Map<String, Object>> queryEtlStartedEntities() {
        String sql = "SELECT ID, ETL_ID FROM ETL_ENTITY WHERE IS_EXEC = 1";
        return jdbcTemplate.queryForList(sql);
    }
}
