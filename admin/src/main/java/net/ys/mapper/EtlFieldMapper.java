package net.ys.mapper;

import net.ys.bean.EtlField;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlFieldMapper implements RowMapper<EtlField> {
    @Override
    public EtlField mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlField etlField = new EtlField();
        etlField.setId(resultSet.getString("ID"));
        etlField.setEntityId(resultSet.getString("ENTITY_ID"));
        etlField.setSrcFieldName(resultSet.getString("SRC_FIELD_NAME"));
        etlField.setDesFieldName(resultSet.getString("DES_FIELD_NAME"));
        etlField.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return etlField;
    }
}