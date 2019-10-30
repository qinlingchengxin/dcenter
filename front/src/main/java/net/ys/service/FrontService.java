package net.ys.service;

import net.ys.dao.FrontDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FrontService {

    @Resource
    private FrontDao frontDao;
}
