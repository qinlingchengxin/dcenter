package net.ys.service;

import net.ys.bean.BusConfig;
import net.ys.bean.BusEnum;
import net.ys.bean.BusEnumValue;
import net.ys.dao.SystemDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class SystemService {

    @Resource
    private SystemDao systemDao;

    /**
     * 获取系统配置
     *
     * @return
     */
    public BusConfig queryBusConfig(int code) {
        return systemDao.queryBusConfig(code);
    }

    /**
     * 获取系统配置列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<BusConfig> queryBusConfigs(int page, int pageSize) {
        return systemDao.queryBusConfigs(page, pageSize);
    }

    /**
     * 获取系统配置个数
     *
     * @return
     */
    public long queryBusConfigCount() {
        return systemDao.queryBusConfigCount();
    }

    /**
     * 修改系统配置
     *
     * @param busConfig
     * @return
     */
    public boolean updateBusConfig(BusConfig busConfig) {
        return systemDao.updateBusConfig(busConfig);
    }

    /**
     * 新增系统配置
     *
     * @param busConfig
     * @return
     */
    public BusConfig addBusConfig(BusConfig busConfig) {
        int currCode = systemDao.queryBusConfigCurrCode();
        busConfig.setCode(currCode);
        busConfig.setId(UUID.randomUUID().toString());
        boolean flag = systemDao.addBusConfig(busConfig);
        if (flag) {
            return busConfig;
        }
        return null;
    }

    /**
     * 获取系统枚举列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<BusEnum> queryBusEnums(int page, int pageSize) {
        return systemDao.queryBusEnums(page, pageSize);
    }

    /**
     * 获取系统枚举个数
     *
     * @return
     */
    public long queryBusEnumCount() {
        return systemDao.queryBusEnumCount();
    }

    /**
     * 获取系统枚举
     *
     * @return
     */
    public BusEnum queryBusEnum(int code) {
        return systemDao.queryBusEnum(code);
    }

    /**
     * 修改系统枚举
     *
     * @param busEnum
     * @return
     */
    public boolean updateBusEnum(BusEnum busEnum) {
        return systemDao.updateBusEnum(busEnum);
    }

    public BusEnum addBusEnum(BusEnum busEnum) {
        int currCode = systemDao.queryBusEnumCurrCode();
        busEnum.setCode(currCode);
        busEnum.setId(UUID.randomUUID().toString());
        boolean flag = systemDao.addBusEnum(busEnum);
        if (flag) {
            return busEnum;
        }
        return null;
    }

    public long queryBusEnumValueCount(int code) {
        return systemDao.queryBusEnumValueCount(code);
    }

    public List<BusEnumValue> queryBusEnumValues(int code, int page, int pageSize) {
        return systemDao.queryBusEnumValues(code, page, pageSize);
    }

    public BusEnumValue queryBusEnumValue(String id) {
        return systemDao.queryBusEnumValue(id);
    }

    public boolean updateBusEnumValue(BusEnumValue busEnumValue) {
        return systemDao.updateBusEnumValue(busEnumValue);
    }

    public BusEnumValue addBusEnumValue(BusEnumValue busEnumValue) {
        busEnumValue.setId(UUID.randomUUID().toString());
        boolean flag = systemDao.addBusEnumValue(busEnumValue);
        if (flag) {
            return busEnumValue;
        }
        return null;
    }
}
