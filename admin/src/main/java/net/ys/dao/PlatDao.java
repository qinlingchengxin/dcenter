package net.ys.dao;

import net.ys.bean.BusPlatform;
import net.ys.mapper.BusPlatformMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class PlatDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<BusPlatform> queryPlats(int page, int pageSize) {
        String sql = "SELECT bp.ID, bp.CODE, bp.PLAT_NAME, bev.VS AS AREA, bev.ID AS AREA_ID, bp.CONTACT_USERNAME, bp.CONTACT_PHONE, bp.TRANS_TYPE, bp.SHARE_PATH, bp.PUBLIC_KEY, bp.PRIVATE_KEY, a.USERNAME AS CREATE_AID, bp.CREATE_TIME, b.USERNAME AS UPDATE_AID, bp.UPDATE_TIME FROM BUS_PLATFORM bp LEFT JOIN ADMIN a ON a.ID = bp.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bp.UPDATE_AID LEFT JOIN BUS_ENUM_VALUE bev ON bev.ENUM_CODE = 1000 AND bev.ID = bp.AREA_CODE LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusPlatformMapper(), (page - 1) * pageSize, pageSize);
    }

    public boolean updatePlat(BusPlatform busPlatform) {
        String sql = "UPDATE BUS_PLATFORM SET PLAT_NAME = ?, AREA_CODE = ?, CONTACT_USERNAME = ?, CONTACT_PHONE = ?, TRANS_TYPE = ?, SHARE_PATH = ?, UPDATE_AID = ?, UPDATE_TIME =? WHERE ID = ?";
        return jdbcTemplate.update(sql, busPlatform.getPlatName(), busPlatform.getAreaId(), busPlatform.getContactUsername(), busPlatform.getContactPhone(), busPlatform.getTransType(), busPlatform.getSharePath(), busPlatform.getUpdateAid(), busPlatform.getUpdateTime(), busPlatform.getId()) >= 0;
    }

    public boolean addPlat(BusPlatform busPlatform) {
        String sql = "INSERT INTO BUS_PLATFORM ( ID, CODE, PLAT_NAME, AREA_CODE, CONTACT_USERNAME, CONTACT_PHONE, TRANS_TYPE, SHARE_PATH, PUBLIC_KEY, PRIVATE_KEY, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, busPlatform.getId(), busPlatform.getCode(), busPlatform.getPlatName(), busPlatform.getAreaId(), busPlatform.getContactUsername(), busPlatform.getContactPhone(), busPlatform.getTransType(), busPlatform.getSharePath(), busPlatform.getPublicKey(), busPlatform.getPrivateKey(), busPlatform.getCreateAid(), busPlatform.getCreateTime(), busPlatform.getCreateAid(), busPlatform.getCreateTime()) > 0;
    }

    public List<Map<String, Object>> queryPlatList() {
        String sql = "SELECT CODE, PLAT_NAME FROM BUS_PLATFORM";
        return jdbcTemplate.queryForList(sql);
    }

    public long queryPlatCount() {
        String sql = "SELECT COUNT(*) FROM BUS_PLATFORM";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public BusPlatform queryPlat(String code) {
        String sql = "SELECT bp.ID, bp.CODE, bp.PLAT_NAME, bev.VS AS AREA, bev.ID AS AREA_ID, bp.CONTACT_USERNAME, bp.CONTACT_PHONE, bp.TRANS_TYPE, bp.SHARE_PATH, bp.PUBLIC_KEY, bp.PRIVATE_KEY, a.USERNAME AS CREATE_AID, bp.CREATE_TIME, b.USERNAME AS UPDATE_AID, bp.UPDATE_TIME FROM BUS_PLATFORM bp LEFT JOIN ADMIN a ON a.ID = bp.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bp.UPDATE_AID LEFT JOIN BUS_ENUM_VALUE bev ON bev.ENUM_CODE = 1000 AND bev.ID = bp.AREA_CODE WHERE bp.CODE = ?";
        List<BusPlatform> list = jdbcTemplate.query(sql, new BusPlatformMapper(), code);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
