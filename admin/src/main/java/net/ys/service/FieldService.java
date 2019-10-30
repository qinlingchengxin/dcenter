package net.ys.service;

import net.ys.bean.Admin;
import net.ys.bean.BusCustomField;
import net.ys.bean.SimpleField;
import net.ys.dao.FieldDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FieldService {

    @Resource
    private EntityService entityService;

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

    public BusCustomField queryField(String fieldName, String tableName) {
        return fieldDao.queryField(fieldName, tableName);
    }

    public BusCustomField updateField(Admin admin, BusCustomField busCustomField) {
        int result = fieldDao.updateField(admin, busCustomField);
        if (result >= 0) {
            if (result > 0) {
                entityService.updateEntityUpdateTime(busCustomField.getTableName());
            }
            return busCustomField;
        }
        return null;
    }

    public BusCustomField addField(Admin admin, BusCustomField busCustomField) {
        long time = System.currentTimeMillis();
        busCustomField.setCreateAid(admin.getId());
        busCustomField.setId(UUID.randomUUID().toString());

        //0-整形/1-字符串/2-时间戳/3-浮点型
        if (busCustomField.getFieldLength() == 0) {
            switch (busCustomField.getFieldType()) {
                case 0:
                case 3:
                    busCustomField.setFieldLength(11);
                    break;
                case 1:
                    busCustomField.setFieldLength(200);
                    break;
                case 2:
                    busCustomField.setFieldLength(20);
                    break;
            }
        }

        boolean flag = fieldDao.addField(busCustomField, time);
        if (flag) {
            flag = entityService.updateEntityUpdateTime(busCustomField.getTableName());
            if (flag) {
                return busCustomField;
            }
        }
        return null;
    }

    public boolean removeField(String tableName, String fieldName) {
        return fieldDao.removeField(tableName, fieldName);
    }

    public List<SimpleField> queryEntityFields(String tableName, int fieldNumLimit) {
        return fieldDao.querySimpleFields(tableName, fieldNumLimit);
    }

    public List<BusCustomField> queryFieldFront(String tableName) {
        return fieldDao.queryFieldFront(tableName);
    }

    public List<Map<String, Object>> queryFieldsFront(String tableName) {
        return fieldDao.queryFieldsFront(tableName);
    }

    public List<Map<String, Object>> queryFieldsFilter(String tableName) {
        return fieldDao.queryFieldsFilter(tableName);
    }
}
