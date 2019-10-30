package net.ys.mapper;

import net.ys.bean.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockMapper implements RowMapper<Stock> {
    @Override
    public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
        Stock stock = new Stock();
        stock.setId(resultSet.getString("ID"));
        stock.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        stock.setTableName(resultSet.getString("TABLE_NAME"));
        stock.setContent(resultSet.getString("CONTENT"));
        stock.setStatus(resultSet.getInt("STATUS"));
        stock.setCreateTime(resultSet.getLong("CREATE_TIME"));
        return stock;
    }
}