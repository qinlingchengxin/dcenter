package net.ys.service;

import net.ys.bean.Admin;
import net.ys.bean.BusUser;
import net.ys.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public long queryUserCount(String platformCode) {
        return userDao.queryUserCount(platformCode);
    }

    public List<BusUser> queryUsers(String platformCode, int page, int pageSize) {
        return userDao.queryUsersByCode(platformCode, page, pageSize);
    }

    public BusUser queryUser(String id) {
        return userDao.queryUser(id);
    }

    public boolean addUser(BusUser busUser) {
        return userDao.addUser(busUser);
    }

    public boolean resetPassword(Admin admin, String id) {
        return userDao.resetPassword(admin, id);
    }
}
