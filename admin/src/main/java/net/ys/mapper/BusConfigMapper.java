package net.ys.mapper;

import net.ys.bean.BusConfig;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusConfigMapper implements RowMapper<BusConfig> {
	@Override
	public BusConfig mapRow(ResultSet resultSet, int i) throws SQLException {
		BusConfig busConfig = new BusConfig();
		busConfig.setId(resultSet.getString("ID"));
		busConfig.setCfgName(resultSet.getString("CFG_NAME"));
		busConfig.setCode(resultSet.getInt("CODE"));
		busConfig.setVi(resultSet.getInt("VI"));
		busConfig.setVs(resultSet.getString("VS"));
		return busConfig;
	}
}