package net.ys.mapper;

import net.ys.bean.EtlProject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlProjectMapper implements RowMapper<EtlProject> {
    @Override
    public EtlProject mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlProject etlProject = new EtlProject();
        etlProject.setId(resultSet.getString("ID"));
        etlProject.setPrjName(resultSet.getString("PRJ_NAME"));

        etlProject.setSrcDbId(resultSet.getString("SRC_DB_ID"));
        etlProject.setSrcDbName(resultSet.getString("SRC_DB_NAME"));
        etlProject.setDesDbId(resultSet.getString("DES_DB_ID"));
        etlProject.setDesDbName(resultSet.getString("DES_DB_NAME"));

        etlProject.setCenterDbType(resultSet.getString("CENTER_DB_TYPE"));
        etlProject.setCenterDbIp(resultSet.getString("CENTER_DB_IP"));
        etlProject.setCenterDbPort(resultSet.getString("CENTER_DB_PORT"));
        etlProject.setCenterDbName(resultSet.getString("CENTER_DB_NAME"));
        etlProject.setCenterDbUsername(resultSet.getString("CENTER_DB_USER_NAME"));
        etlProject.setCenterDbPwd(resultSet.getString("CENTER_DB_PWD"));

        etlProject.setBusDbType(resultSet.getString("BUS_DB_TYPE"));
        etlProject.setBusDbIp(resultSet.getString("BUS_DB_IP"));
        etlProject.setBusDbPort(resultSet.getString("BUS_DB_PORT"));
        etlProject.setBusDbName(resultSet.getString("BUS_DB_NAME"));
        etlProject.setBusDbUsername(resultSet.getString("BUS_DB_USER_NAME"));
        etlProject.setBusDbPwd(resultSet.getString("BUS_DB_PWD"));

        etlProject.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return etlProject;
    }
}