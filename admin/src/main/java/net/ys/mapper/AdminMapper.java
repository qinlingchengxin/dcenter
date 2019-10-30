package net.ys.mapper;

import net.ys.bean.Admin;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin> {
	@Override
	public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
		Admin admin = new Admin();
		admin.setId(resultSet.getString("ID"));
		admin.setMagType(resultSet.getInt("MAG_TYPE"));
		admin.setUsername(resultSet.getString("USERNAME"));
		admin.setPassword(resultSet.getString("PASSWORD"));
		return admin;
	}
}