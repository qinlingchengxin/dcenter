package net.ys.mapper;

import net.ys.bean.BusCustomEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusCustomEntityMapper implements RowMapper<BusCustomEntity> {
    @Override
    public BusCustomEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        BusCustomEntity busCustomEntity = new BusCustomEntity();
        busCustomEntity.setId(resultSet.getString("ID"));
        busCustomEntity.setRealTabName(resultSet.getString("REAL_TAB_NAME"));
        busCustomEntity.setTableName(resultSet.getString("TABLE_NAME"));
        busCustomEntity.setInChinese(resultSet.getString("IN_CHINESE"));
        busCustomEntity.setCallbackUrl(resultSet.getString("CALLBACK_URL"));
        busCustomEntity.setDescription(resultSet.getString("DESCRIPTION"));
        busCustomEntity.setCreateAid(resultSet.getString("CREATE_AID"));
        busCustomEntity.setCreateTime(resultSet.getLong("CREATE_TIME"));
        busCustomEntity.setUpdateAid(resultSet.getString("UPDATE_AID"));
        busCustomEntity.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
        busCustomEntity.setReleaseAid(resultSet.getString("RELEASE_AID"));
        busCustomEntity.setReleaseTime(resultSet.getLong("RELEASE_TIME"));
        return busCustomEntity;
    }
}