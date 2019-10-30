package net.ys.mapper;

import net.ys.bean.SyncTable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncTableMapper implements RowMapper<SyncTable> {
    @Override
    public SyncTable mapRow(ResultSet resultSet, int i) throws SQLException {
        SyncTable syncTable = new SyncTable();
        syncTable.setTableName(resultSet.getString("TABLE_NAME"));
        syncTable.setRealTabName(resultSet.getString("REAL_TAB_NAME"));
        syncTable.setLastPackTime(resultSet.getLong("LAST_PACK_TIME"));
        return syncTable;
    }
}