package net.ys.dao;

import net.ys.bean.BusUser;
import net.ys.mapper.BusUserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class FrontDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public BusUser queryBusUser(String username, String password) {
        String sql = "SELECT ID, USERNAME, PASSWORD, PLATFORM_CODE, '' AS PLATFORM_NAME, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME FROM BUS_USER WHERE USERNAME = ? AND PASSWORD = ?";
        List<BusUser> list = jdbcTemplate.query(sql, new BusUserMapper(), username, password);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
