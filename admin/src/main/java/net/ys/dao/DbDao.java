package net.ys.dao;

import net.ys.bean.DbField;
import net.ys.bean.DbTable;
import net.ys.bean.EtlAllTable;
import net.ys.bean.EtlDataSource;
import net.ys.mapper.EtlAllTableMapper;
import net.ys.mapper.EtlDataSourceMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DbDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<EtlDataSource> queryDataSources() {
        String sql = "SELECT `ID`, `SOURCE_NAME`, `DB_TYPE`, `DB_IP`, `DB_PORT`, `DB_NAME`, `DB_USER_NAME`, `DB_PWD`, `CREATE_TIME` FROM ETL_DATA_SOURCE";
        return jdbcTemplate.query(sql, new EtlDataSourceMapper());
    }

    public void addTables(final String dsId, final List<DbTable> tables) {
        String sql = "INSERT IGNORE INTO ETL_ALL_TABLE ( ID, DS_ID, `NAME`, COMMENT, CREATE_TIME ) VALUES (?,?,?,?,?)";
        final long now = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, dsId);
                ps.setString(3, tables.get(i).getTableName());
                ps.setString(4, tables.get(i).getComment());
                ps.setLong(5, now);
            }

            @Override
            public int getBatchSize() {
                return tables.size();
            }
        });
    }

    public List<EtlAllTable> queryEtlAllTables(EtlDataSource dataSource) {
        String sql = "SELECT `ID`, `DS_ID`, `NAME`, `COMMENT`, `CREATE_TIME` FROM ETL_ALL_TABLE WHERE `DS_ID` = ?";
        return jdbcTemplate.query(sql, new EtlAllTableMapper(), dataSource.getId());
    }

    public void addFields(final List<DbField> fields) {
        final long now = System.currentTimeMillis();
        String sql = "INSERT IGNORE INTO ETL_ALL_FIELD ( `ID`, `TABLE_ID`, `NAME`, `COMMENT`, `PRI_KEY`, `CREATE_TIME`) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DbField field = fields.get(i);
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, field.getTableId());
                ps.setString(3, field.getFieldName());
                ps.setString(4, field.getComment());
                ps.setInt(5, field.getPriKey());
                ps.setLong(6, now);
            }

            @Override
            public int getBatchSize() {
                return fields.size();
            }
        });
    }
}
