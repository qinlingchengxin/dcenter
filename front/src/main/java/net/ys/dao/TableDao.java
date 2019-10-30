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

    public long queryTableSyncTime() {
        String sql = "SELECT MAX(RELEASE_TIME) FROM BUS_CUSTOM_ENTITY";
        List<Long> list = jdbcTemplate.queryForList(sql, Long.class);
        Long amount = list.get(0);
        if (amount == null) {
            return 0;
        }
        return amount;
    }

    public List<String> queryTabFieldIds() {
        String sql = "SELECT ID FROM BUS_CUSTOM_ENTITY UNION ALL SELECT ID FROM BUS_CUSTOM_FIELD";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public String queryDataSyncTime() {
        String sql = "SELECT VI FROM BUS_CONFIG WHERE CODE = 1000";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<Map<String, Object>> queryTableNames() {
        String sql = "SELECT TABLE_NAME, REAL_TAB_NAME FROM BUS_CUSTOM_ENTITY";
        return jdbcTemplate.queryForList(sql);
    }

    public void batExecuteSql(List<String> sqlList) {
        String[] sql = new String[sqlList.size()];
        sqlList.toArray(sql);
        jdbcTemplate.batchUpdate(sql);
    }
}
