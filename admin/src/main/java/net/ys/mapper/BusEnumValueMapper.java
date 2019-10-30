package net.ys.mapper;

import net.ys.bean.BusEnumValue;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusEnumValueMapper implements RowMapper<BusEnumValue> {
	@Override
	public BusEnumValue mapRow(ResultSet resultSet, int i) throws SQLException {
		BusEnumValue busEnumValue = new BusEnumValue();
		busEnumValue.setId(resultSet.getString("ID"));
		busEnumValue.setEnumCode(resultSet.getInt("ENUM_CODE"));
		busEnumValue.setVi(resultSet.getInt("VI"));
		busEnumValue.setVs(resultSet.getString("VS"));
		return busEnumValue;
	}
}