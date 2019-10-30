package net.ys.mapper;

import net.ys.bean.BusAtt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusAttMapper implements RowMapper<BusAtt> {
    @Override
    public BusAtt mapRow(ResultSet resultSet, int i) throws SQLException {
        BusAtt busAtt = new BusAtt();
        busAtt.setId(resultSet.getLong("ID"));
        busAtt.setFileName(resultSet.getString("FILE_NAME"));
        busAtt.setAttName(resultSet.getString("ATT_NAME"));
        busAtt.setTableName(resultSet.getString("TABLE_NAME"));
        busAtt.setFieldName(resultSet.getString("FIELD_NAME"));
        busAtt.setDataPriField(resultSet.getString("DATA_PRI_FIELD"));
        busAtt.setDataId(resultSet.getString("DATA_ID"));
        busAtt.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        busAtt.setPath(resultSet.getString("FILE_PATH"));
        busAtt.setUpStatus(resultSet.getInt("UP_STATUS"));
        return busAtt;
    }
}