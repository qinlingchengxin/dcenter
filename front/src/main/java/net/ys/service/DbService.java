package net.ys.service;

import net.ys.dao.DbDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User: LiWenC
 * Date: 18-4-24
 */

@Service
public class DbService {

    @Resource
    private DbDao dbDao;
}