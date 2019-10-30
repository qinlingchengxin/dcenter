package net.ys.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class BuDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<String> queryFields(String tableName) {
        String sql = "SELECT FIELD_NAME FROM BUS_CUSTOM_FIELD WHERE TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, String.class, tableName);
    }
}
