package net.ys.mapper;

import net.ys.bean.BusMonitor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusMonitorMapper implements RowMapper<BusMonitor> {
    @Override
    public BusMonitor mapRow(ResultSet resultSet, int i) throws SQLException {
        BusMonitor monitor = new BusMonitor();
        monitor.setId(resultSet.getString("ID"));
        monitor.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        monitor.setPlatName(resultSet.getString("PLAT_NAME"));
        monitor.setType(resultSet.getInt("TYPE"));
        monitor.setIp(resultSet.getString("IP"));
        monitor.setPort(resultSet.getInt("PORT"));
        monitor.setIntervalDay(resultSet.getInt("INTERVAL_DAY"));
        monitor.setIntervalHour(resultSet.getInt("INTERVAL_HOUR"));
        monitor.setIntervalMinute(resultSet.getInt("INTERVAL_MINUTE"));
        monitor.setNotice(resultSet.getInt("NOTICE"));
        monitor.setStatus(resultSet.getInt("STATUS"));
        monitor.setUpAmount(resultSet.getInt("UP_AMOUNT"));
        monitor.setDownAmount(resultSet.getInt("DOWN_AMOUNT"));
        monitor.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return monitor;
    }
}