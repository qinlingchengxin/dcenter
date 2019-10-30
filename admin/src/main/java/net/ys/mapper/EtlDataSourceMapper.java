package net.ys.mapper;

import net.ys.bean.EtlDataSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlDataSourceMapper implements RowMapper<EtlDataSource> {
    @Override
    public EtlDataSource mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlDataSource dataSource = new EtlDataSource();
        dataSource.setId(resultSet.getString("ID"));
        dataSource.setSourceName(resultSet.getString("SOURCE_NAME"));
        dataSource.setDbType(resultSet.getString("DB_TYPE"));
        dataSource.setDbIp(resultSet.getString("DB_IP"));
        dataSource.setDbPort(resultSet.getInt("DB_PORT"));
        dataSource.setDbName(resultSet.getString("DB_NAME"));
        dataSource.setDbUsername(resultSet.getString("DB_USER_NAME"));
        dataSource.setDbPwd(resultSet.getString("DB_PWD"));
        dataSource.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return dataSource;
    }
}