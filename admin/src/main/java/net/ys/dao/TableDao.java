package net.ys.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class TableDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> syncTableStructure(String platformCode, long syncTime) {
        String sql = "SELECT ID, TABLE_NAME, REAL_TAB_NAME, IN_CHINESE, CALLBACK_URL, DESCRIPTION, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME, RELEASE_AID, RELEASE_TIME FROM BUS_CUSTOM_ENTITY WHERE TABLE_NAME IN ( SELECT TABLE_NAME FROM BUS_SYNC_TABLE WHERE PLATFORM_CODE = ? AND RELEASE_TIME > ? ) ORDER BY TABLE_NAME";
        return jdbcTemplate.queryForList(sql, platformCode, syncTime);
    }

    public List<Map<String, Object>> syncFields(String condition) {
        String sql = "SELECT ID, TABLE_NAME, FIELD_NAME, REAL_FIELD_NAME, IN_CHINESE, FIELD_TYPE, FIELD_LENGTH, PRECISIONS, REQUIRED, VALUE_FIELD, UNIQUE_KEY, EXPOSED, REMARKS, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME FROM BUS_CUSTOM_FIELD WHERE EXPOSED = 1 AND RELEASE_TIME > UPDATE_TIME AND TABLE_NAME IN " + condition + " ORDER BY TABLE_NAME";
        return jdbcTemplate.queryForList(sql);
    }
}
