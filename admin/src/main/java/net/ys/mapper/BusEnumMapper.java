package net.ys.mapper;

import net.ys.bean.BusEnum;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusEnumMapper implements RowMapper<BusEnum> {
	@Override
	public BusEnum mapRow(ResultSet resultSet, int i) throws SQLException {
		BusEnum busEnum = new BusEnum();
		busEnum.setId(resultSet.getString("ID"));
		busEnum.setCode(resultSet.getInt("CODE"));
		busEnum.setEnumName(resultSet.getString("ENUM_NAME"));
		return busEnum;
	}
}