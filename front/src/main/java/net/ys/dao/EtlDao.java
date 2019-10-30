package net.ys.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class EtlDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
}
