package net.ys.service;

import net.ys.bean.BusPlatform;
import net.ys.dao.PlatDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PlatService {

    @Resource
    private PlatDao platDao;

    public BusPlatform queryPlat(String code) {
        return platDao.queryPlat(code);
    }
}
