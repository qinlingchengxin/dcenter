package net.ys.dao;

import net.ys.bean.Admin;
import net.ys.mapper.AdminMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AdminDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Admin queryAdmin(String username, String pass) {
        String sql = "SELECT ID, MAG_TYPE, USERNAME, PASSWORD FROM ADMIN WHERE USERNAME = ? AND PASSWORD =?";
        List<Admin> list = jdbcTemplate.query(sql, new AdminMapper(), username, pass);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
