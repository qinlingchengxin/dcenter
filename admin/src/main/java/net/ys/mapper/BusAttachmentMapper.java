package net.ys.mapper;

import net.ys.bean.BusAttachment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusAttachmentMapper implements RowMapper<BusAttachment> {
    @Override
    public BusAttachment mapRow(ResultSet resultSet, int i) throws SQLException {
        BusAttachment busAttachment = new BusAttachment();
        busAttachment.setId(resultSet.getString("ID"));
        busAttachment.setPath(resultSet.getString("PATH"));
        busAttachment.setAttName(resultSet.getString("ATT_NAME"));
        busAttachment.setSrcName(resultSet.getString("SRC_NAME"));
        busAttachment.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        busAttachment.setTableName(resultSet.getString("TABLE_NAME"));
        busAttachment.setFieldName(resultSet.getString("FIELD_NAME"));
        busAttachment.setDataPriField(resultSet.getString("DATA_PRI_FIELD"));
        busAttachment.setDataId(resultSet.getString("DATA_ID"));
        busAttachment.setUpdateStatus(resultSet.getInt("UPDATE_STATUS"));
        busAttachment.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return busAttachment;
    }
}