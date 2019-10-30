package net.ys.service;

import net.ys.bean.Admin;
import net.ys.bean.BusPlatform;
import net.ys.dao.PlatDao;
import net.ys.utils.RSAUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PlatService {

    @Resource
    private PlatDao platDao;

    public long queryPlatCount() {
        return platDao.queryPlatCount();
    }

    public List<BusPlatform> queryPlats(int page, int pageSize) {
        return platDao.queryPlats(page, pageSize);
    }

    public BusPlatform queryPlat(String code) {
        return platDao.queryPlat(code);
    }

    public BusPlatform updatePlat(Admin admin, BusPlatform busPlatform) {
        busPlatform.setUpdateAid(admin.getId());
        busPlatform.setUpdateTime(System.currentTimeMillis());
        boolean flag = platDao.updatePlat(busPlatform);
        if (flag) {
            busPlatform.setUpdateAid(admin.getUsername());
            return busPlatform;
        }
        return null;
    }

    public BusPlatform addPlat(Admin admin, BusPlatform busPlatform) throws Exception {

        busPlatform.setId(UUID.randomUUID().toString());
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);//平台编码
        busPlatform.setCode(code);
        busPlatform.setCreateAid(admin.getId());
        busPlatform.setCreateTime(System.currentTimeMillis());

        String publicKey = RSAUtil.getPublicKey();
        String privateKey = RSAUtil.getPrivateKey();
        busPlatform.setPublicKey(publicKey);
        busPlatform.setPrivateKey(privateKey);
        boolean flag = platDao.addPlat(busPlatform);
        if (flag) {
            busPlatform.setCreateAid(admin.getUsername());
            return busPlatform;
        }
        return null;
    }

    public List<Map<String, Object>> queryPlatformList() {
        return platDao.queryPlatList();
    }
}
