package net.ys.mapper;

import net.ys.bean.DownStock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DownStockMapper implements RowMapper<DownStock> {
    @Override
    public DownStock mapRow(ResultSet resultSet, int i) throws SQLException {
        DownStock downStock = new DownStock();
        downStock.setId(resultSet.getString("ID"));
        downStock.setTableName(resultSet.getString("TABLE_NAME"));
        downStock.setRealTabName(resultSet.getString("REAL_TAB_NAME"));
        downStock.setStartTime(resultSet.getLong("START_TIME"));
        downStock.setEndTime(resultSet.getLong("END_TIME"));
        downStock.setPage(resultSet.getInt("PAGE"));
        downStock.setPageSize(resultSet.getInt("PAGE_SIZE"));
        downStock.setStatus(resultSet.getInt("STATUS"));
        downStock.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return downStock;
    }
}