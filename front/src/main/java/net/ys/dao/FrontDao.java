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

}
