package net.ys.mapper;

import net.ys.bean.BusDataTransLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusDataTransLogMapper implements RowMapper<BusDataTransLog> {
    @Override
    public BusDataTransLog mapRow(ResultSet resultSet, int i) throws SQLException {
        BusDataTransLog busDataTransLog = new BusDataTransLog();
        busDataTransLog.setId(resultSet.getString("ID"));
        busDataTransLog.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        busDataTransLog.setTableName(resultSet.getString("TABLE_NAME"));
        busDataTransLog.setTransType(resultSet.getInt("TRANS_TYPE"));
        busDataTransLog.setContent(resultSet.getString("CONTENT"));
        busDataTransLog.setFailedContent(resultSet.getString("FAILED_CONTENT"));
        busDataTransLog.setStatus(resultSet.getInt("STATUS"));
        busDataTransLog.setCreateTime(resultSet.getLong("CREATE_TIME"));
        busDataTransLog.setRemarks(resultSet.getString("REMARKS"));
        return busDataTransLog;
    }
}