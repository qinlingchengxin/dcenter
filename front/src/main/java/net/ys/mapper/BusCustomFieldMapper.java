package net.ys.mapper;

import net.ys.bean.BusCustomField;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusCustomFieldMapper implements RowMapper<BusCustomField> {
    @Override
    public BusCustomField mapRow(ResultSet resultSet, int i) throws SQLException {
        BusCustomField busCustomField = new BusCustomField();
        busCustomField.setId(resultSet.getString("ID"));
        busCustomField.setTableName(resultSet.getString("TABLE_NAME"));
        busCustomField.setFieldName(resultSet.getString("FIELD_NAME"));
        busCustomField.setRealFieldName(resultSet.getString("REAL_FIELD_NAME"));
        busCustomField.setInChinese(resultSet.getString("IN_CHINESE"));
        busCustomField.setFieldType(resultSet.getInt("FIELD_TYPE"));
        busCustomField.setFieldLength(resultSet.getInt("FIELD_LENGTH"));
        busCustomField.setPrecisions(resultSet.getInt("PRECISIONS"));
        busCustomField.setRequired(resultSet.getInt("REQUIRED"));
        busCustomField.setValueField(resultSet.getString("VALUE_FIELD"));
        busCustomField.setUniqueKey(resultSet.getInt("UNIQUE_KEY"));
        busCustomField.setExposed(resultSet.getInt("EXPOSED"));
        busCustomField.setRemarks(resultSet.getString("REMARKS"));
        busCustomField.setCreateAid(resultSet.getString("CREATE_AID"));
        busCustomField.setCreateTime(resultSet.getLong("CREATE_TIME"));
        busCustomField.setUpdateAid(resultSet.getString("UPDATE_AID"));
        busCustomField.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
        return busCustomField;
    }
}