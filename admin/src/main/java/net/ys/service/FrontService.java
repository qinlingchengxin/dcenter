package net.ys.service;

import net.ys.bean.BusUser;
import net.ys.dao.FrontDao;
import net.ys.utils.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FrontService {

    @Resource
    private FrontDao frontDao;

    public BusUser queryBusUser(String username, String password) {
        password = Tools.genMD5(password);
        return frontDao.queryBusUser(username, password);
    }
}
