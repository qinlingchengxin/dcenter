package net.ys.dao;

import net.sf.json.JSONNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CommonDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

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

    public List<Map<String, Object>> queryDataList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }
}
