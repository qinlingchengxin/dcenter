package net.ys.dao;

import net.ys.bean.BusPlatform;
import net.ys.mapper.BusPlatformMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PlatDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public BusPlatform queryPlat(String code) {
        String sql = "SELECT bp.ID, bp.CODE, bp.PLAT_NAME, bev.VS AS AREA, bev.ID AS AREA_ID, bp.CONTACT_USERNAME, bp.CONTACT_PHONE, bp.TRANS_TYPE, bp.SHARE_PATH, bp.PUBLIC_KEY, bp.PRIVATE_KEY, a.USERNAME AS CREATE_AID, bp.CREATE_TIME, b.USERNAME AS UPDATE_AID, bp.UPDATE_TIME FROM BUS_PLATFORM bp LEFT JOIN ADMIN a ON a.ID = bp.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bp.UPDATE_AID LEFT JOIN BUS_ENUM_VALUE bev ON bev.ENUM_CODE = 1000 AND bev.ID = bp.AREA_CODE WHERE bp.CODE = ?";
        List<BusPlatform> list = jdbcTemplate.query(sql, new BusPlatformMapper(), code);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
