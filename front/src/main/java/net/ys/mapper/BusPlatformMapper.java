package net.ys.mapper;

import net.ys.bean.BusPlatform;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BusPlatformMapper implements RowMapper<BusPlatform> {
    @Override
    public BusPlatform mapRow(ResultSet resultSet, int i) throws SQLException {
        BusPlatform busPlatform = new BusPlatform();
        busPlatform.setId(resultSet.getString("ID"));
        busPlatform.setCode(resultSet.getString("CODE"));
        busPlatform.setPlatName(resultSet.getString("PLAT_NAME"));
        busPlatform.setArea(resultSet.getString("AREA"));
        busPlatform.setAreaId(resultSet.getString("AREA_ID"));
        busPlatform.setContactUsername(resultSet.getString("CONTACT_USERNAME"));
        busPlatform.setContactPhone(resultSet.getString("CONTACT_PHONE"));
        busPlatform.setTransType(resultSet.getInt("TRANS_TYPE"));
        busPlatform.setSharePath(resultSet.getString("SHARE_PATH"));
        busPlatform.setPublicKey(resultSet.getString("PUBLIC_KEY"));
        busPlatform.setPrivateKey(resultSet.getString("PRIVATE_KEY"));
        busPlatform.setCreateAid(resultSet.getString("CREATE_AID"));
        busPlatform.setCreateTime(resultSet.getLong("CREATE_TIME"));
        busPlatform.setUpdateAid(resultSet.getString("UPDATE_AID"));
        busPlatform.setUpdateTime(resultSet.getLong("UPDATE_TIME"));
        return busPlatform;
    }
}