package net.ys.mapper;

import net.ys.bean.EntityPrivilege;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityPrivilegeMapper implements RowMapper<EntityPrivilege> {
    @Override
    public EntityPrivilege mapRow(ResultSet resultSet, int i) throws SQLException {
        EntityPrivilege entityPrivilege = new EntityPrivilege();
        entityPrivilege.setId(resultSet.getString("ID"));
        entityPrivilege.setPlatformCode(resultSet.getString("PLATFORM_CODE"));
        entityPrivilege.setPlatformName(resultSet.getString("PLAT_NAME"));
        entityPrivilege.setTableName(resultSet.getString("TABLE_NAME"));
        entityPrivilege.setPriType(resultSet.getInt("PRI_TYPE"));
        entityPrivilege.setAssignerAid(resultSet.getString("ASSIGNER_AID"));
        entityPrivilege.setAssignerTime(resultSet.getLong("ASSIGNER_TIME"));
        return entityPrivilege;
    }
}