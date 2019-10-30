package net.ys.service;

import net.ys.dao.SystemDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemService {

    @Resource
    private SystemDao systemDao;
}
