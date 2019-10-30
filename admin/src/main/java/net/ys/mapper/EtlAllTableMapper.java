package net.ys.mapper;

import net.ys.bean.EtlAllTable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlAllTableMapper implements RowMapper<EtlAllTable> {
    @Override
    public EtlAllTable mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlAllTable table = new EtlAllTable();
        table.setId(resultSet.getString("ID"));
        table.setDsId(resultSet.getString("DS_ID"));
        table.setName(resultSet.getString("NAME"));
        table.setComment(resultSet.getString("COMMENT"));
        table.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return table;
    }
}