package net.ys.mapper;

import net.ys.bean.EtlAllField;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlAllFieldMapper implements RowMapper<EtlAllField> {
    @Override
    public EtlAllField mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlAllField field = new EtlAllField();
        field.setId(resultSet.getString("ID"));
        field.setTableId(resultSet.getString("TABLE_ID"));
        field.setName(resultSet.getString("NAME"));
        field.setPriKey(resultSet.getInt("PRI_KEY"));
        field.setComment(resultSet.getString("COMMENT"));
        field.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return field;
    }
}