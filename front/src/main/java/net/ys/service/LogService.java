package net.ys.service;

import net.ys.dao.LogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogService {

    @Resource
    private LogDao logDao;

}
