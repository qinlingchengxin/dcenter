package net.ys.mapper;

import net.ys.bean.BusTfChangeLog;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusTfChangeLogMapper implements RowMapper<BusTfChangeLog> {
	@Override
	public BusTfChangeLog mapRow(ResultSet resultSet, int i) throws SQLException {
		BusTfChangeLog busTfChangeLog = new BusTfChangeLog();
		busTfChangeLog.setId(resultSet.getString("ID"));
		busTfChangeLog.setChgType(resultSet.getInt("CHG_TYPE"));
		busTfChangeLog.setObjId(resultSet.getString("OBJ_ID"));
		busTfChangeLog.setUpdateAid(resultSet.getString("UPDATE_AID"));
		busTfChangeLog.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
		busTfChangeLog.setContent(resultSet.getString("CONTENT"));
		return busTfChangeLog;
	}
}