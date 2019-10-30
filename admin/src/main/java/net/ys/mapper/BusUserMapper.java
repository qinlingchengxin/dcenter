package net.ys.mapper;

import net.ys.bean.BusUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusUserMapper implements RowMapper<BusUser> {
    @Override
    public BusUser mapRow(ResultSet resultSet, int i) throws SQLException {
        BusUser busUser = new BusUser();
        busUser.setId(resultSet.getString("ID"));
        busUser.setUsername(resultSet.getString("USERNAME"));
        busUser.setPlatformName(resultSet.getString("PLATFORM_NAME"));
        busUser.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        busUser.setCreateAid(resultSet.getString("CREATE_AID"));
        busUser.setCreateTime(resultSet.getLong("CREATE_TIME"));
        busUser.setUpdateAid(resultSet.getString("UPDATE_AID"));
        busUser.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
        return busUser;
    }
}