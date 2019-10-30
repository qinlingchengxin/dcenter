package net.ys.mapper;

import net.ys.bean.PlatEntFilter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlatEntFilterMapper implements RowMapper<PlatEntFilter> {
    @Override
    public PlatEntFilter mapRow(ResultSet resultSet, int i) throws SQLException {
        PlatEntFilter platEntFilter = new PlatEntFilter();
        platEntFilter.setId(resultSet.getString("ID"));
        platEntFilter.setEpId(resultSet.getString("EP_ID"));
        platEntFilter.setTableName(resultSet.getString("TABLE_NAME"));
        platEntFilter.setField(resultSet.getString("FIELD"));
        platEntFilter.setCondition(resultSet.getString("CONDITION"));
        platEntFilter.setAn(resultSet.getString("AN"));
        platEntFilter.setConValue(resultSet.getString("CON_VALUE"));
        return platEntFilter;
    }
}