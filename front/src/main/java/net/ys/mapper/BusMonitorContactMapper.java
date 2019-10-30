package net.ys.mapper;

import net.ys.bean.BusMonitorContact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusMonitorContactMapper implements RowMapper<BusMonitorContact> {
    @Override
    public BusMonitorContact mapRow(ResultSet resultSet, int i) throws SQLException {
        BusMonitorContact contact = new BusMonitorContact();
        contact.setId(resultSet.getString("ID"));
        contact.setMonitorId(resultSet.getString("M_ID"));
        contact.setName(resultSet.getString("NAME"));
        contact.setTelNo(resultSet.getString("TEL_NO"));
        contact.setEmail(resultSet.getString("EMAIL"));
        contact.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return contact;
    }
}