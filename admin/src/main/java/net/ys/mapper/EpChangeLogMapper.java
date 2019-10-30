package net.ys.mapper;

import net.ys.bean.EpChangeLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EpChangeLogMapper implements RowMapper<EpChangeLog> {
    @Override
    public EpChangeLog mapRow(ResultSet resultSet, int i) throws SQLException {
        EpChangeLog epChangeLog = new EpChangeLog();
        epChangeLog.setId(resultSet.getString("ID"));
        epChangeLog.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        epChangeLog.setPlatformName(resultSet.getString("PLATFORM_NAME"));
        epChangeLog.setTableName(resultSet.getString("TABLE_NAME"));
        epChangeLog.setChgType(resultSet.getInt("CHG_TYPE"));
        epChangeLog.setUpdateAid(resultSet.getString("UPDATE_AID"));
        epChangeLog.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
        return epChangeLog;
    }
}