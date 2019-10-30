package net.ys.service;

import net.ys.bean.BusCustomField;
import net.ys.dao.FieldDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FieldService {

    @Resource
    private FieldDao fieldDao;

    public long queryFieldCount(String tableName, String fieldName) {
        return fieldDao.queryFieldCount(tableName, fieldName);
    }

    public List<BusCustomField> queryFields(String tableName, String fieldName, int page, int pageSize) {
        return fieldDao.queryFields(tableName, fieldName, page, pageSize);
    }

    public List<BusCustomField> queryFieldsFront(String tableName, int page, int pageSize) {
        return fieldDao.queryFieldsFront(tableName, page, pageSize);
    }

    public List<BusCustomField> queryFieldFront(String tableName) {
        return fieldDao.queryFieldFront(tableName);
    }

    public List<Map<String, Object>> queryFieldsFront(String tableName) {
        return fieldDao.queryFieldsFront(tableName);
    }
}
