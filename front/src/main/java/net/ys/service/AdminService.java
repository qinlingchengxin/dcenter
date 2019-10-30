package net.ys.service;

import net.ys.dao.AdminDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;

}
