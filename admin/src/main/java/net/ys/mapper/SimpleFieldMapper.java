package net.ys.mapper;

import net.ys.bean.SimpleField;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleFieldMapper implements RowMapper<SimpleField> {
    @Override
    public SimpleField mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SimpleField(resultSet.getString("FIELD_NAME"), resultSet.getString("REAL_FIELD_NAME"), resultSet.getString("IN_CHINESE"));
    }
}