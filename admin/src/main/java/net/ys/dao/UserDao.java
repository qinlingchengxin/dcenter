package net.ys.dao;

import net.ys.bean.Admin;
import net.ys.bean.BusUser;
import net.ys.mapper.BusUserMapper;
import net.ys.utils.Tools;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public boolean addUser(BusUser busUser) {
        String sql = "INSERT INTO BUS_USER ( ID, USERNAME, PASSWORD, PLATFORM_CODE, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME ) VALUES (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, busUser.getId(), busUser.getUsername(), busUser.getPassword(), busUser.getPlatformCode(), busUser.getCreateAid(), busUser.getCreateTime(), busUser.getCreateAid(), busUser.getCreateTime()) > 0;
    }

    public boolean resetPassword(Admin admin, String id) {
        String sql = "UPDATE BUS_USER SET PASSWORD = ?, UPDATE_AID = ?, UPDATE_TIME = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, Tools.genMD5("123456"), admin.getId(), System.currentTimeMillis(), id) >= 0;
    }

    public long queryUserCount(String platformCode) {
        String sql = "SELECT COUNT(*) FROM BUS_USER WHERE PLATFORM_CODE = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, platformCode);
    }

    public List<BusUser> queryUsersByCode(String platformCode, int page, int pageSize) {
        String sql = "SELECT bu.ID, bu.USERNAME, bu.PLATFORM_CODE, bp.PLAT_NAME AS PLATFORM_NAME, a.USERNAME AS CREATE_AID, bu.CREATE_TIME, b.USERNAME AS UPDATE_AID, bu.UPDATE_TIME FROM BUS_USER bu LEFT JOIN ADMIN a ON a.ID = bu.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bu.CREATE_AID LEFT JOIN BUS_PLATFORM bp ON bp.CODE = bu.PLATFORM_CODE WHERE bu.PLATFORM_CODE = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusUserMapper(), platformCode, (page - 1) * pageSize, pageSize);
    }

    public BusUser queryUser(String id) {
        String sql = "SELECT bu.ID, bu.USERNAME, bu.PLATFORM_CODE, bp.PLAT_NAME AS PLATFORM_NAME, a.USERNAME AS CREATE_AID, bu.CREATE_TIME, b.USERNAME AS UPDATE_AID, bu.UPDATE_TIME FROM BUS_USER bu LEFT JOIN ADMIN a ON a.ID = bu.CREATE_AID LEFT JOIN ADMIN b ON b.ID = bu.CREATE_AID LEFT JOIN BUS_PLATFORM bp ON bp.CODE = bu.PLATFORM_CODE WHERE bu.ID = ?";
        List<BusUser> list = jdbcTemplate.query(sql, new BusUserMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }
}
