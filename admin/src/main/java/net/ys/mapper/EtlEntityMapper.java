package net.ys.mapper;

import net.ys.bean.EtlEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EtlEntityMapper implements RowMapper<EtlEntity> {
    @Override
    public EtlEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        EtlEntity etlEntity = new EtlEntity();
        etlEntity.setId(resultSet.getString("ID"));
        etlEntity.setPrjId(resultSet.getString("PRJ_ID"));
        etlEntity.setSrcTabName(resultSet.getString("SRC_TAB_NAME"));
        etlEntity.setDesTabName(resultSet.getString("DES_TAB_NAME"));
        etlEntity.setSrcPrimaryKey(resultSet.getString("SRC_PRIMARY_KEY"));
        etlEntity.setDesPrimaryKey(resultSet.getString("DES_PRIMARY_KEY"));
        etlEntity.setDescription(resultSet.getString("DESCRIPTION"));
        etlEntity.setEtlId(resultSet.getString("ETL_ID"));
        etlEntity.setCreateTime(resultSet.getLong("CREATE_TIME"));
        etlEntity.setRepeat(resultSet.getInt("REPEAT"));
        etlEntity.setScheduleType(resultSet.getInt("SCHEDULE_TYPE"));
        etlEntity.setIntervalSecond(resultSet.getInt("INTERVAL_SECOND"));
        etlEntity.setIntervalMinute(resultSet.getInt("INTERVAL_MINUTE"));
        etlEntity.setFixedHour(resultSet.getInt("FIXED_HOUR"));
        etlEntity.setFixedMinute(resultSet.getInt("FIXED_MINUTE"));
        etlEntity.setFixedWeekday(resultSet.getInt("FIXED_WEEKDAY"));
        etlEntity.setFixedDay(resultSet.getInt("FIXED_DAY"));
        etlEntity.setExec(resultSet.getInt("IS_EXEC"));
        etlEntity.setCondition(resultSet.getString("CONDITION"));
        return etlEntity;
    }
}