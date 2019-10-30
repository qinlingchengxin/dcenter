package net.ys.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ys.bean.BusCustomField;
import net.ys.constant.SysRegex;
import net.ys.dao.CommonDao;
import net.ys.utils.TimeUtil;
import net.ys.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CommonService {

    @Resource
    private CommonDao commonDao;

    @Resource
    private FieldService fieldService;

    /**
     * 数据校验
     *
     * @param tableName
     * @param array
     * @return
     */
    public Map<String, JSONArray> validateData(String tableName, JSONArray array) {
        List<BusCustomField> fields = fieldService.queryFieldFront(tableName);
        Set<String> errorFields = new HashSet<String>();
        JSONArray failedArray = new JSONArray();
        JSONArray successArray = new JSONArray();
        JSONObject temp;
        Object val;
        String strTmp;
        boolean flag;
        String valueField;
        List<String> valueFieldList;
        for (int i = 0, j = array.size(); i < j; i++) {
            flag = false;
            temp = array.getJSONObject(i);
            Set<String> keys = temp.keySet();
            for (String key : keys) {
                val = temp.get(key);
                strTmp = String.valueOf(val);

                for (BusCustomField field : fields) {
                    if (field.getFieldName().equals(key)) {
                        int type = field.getFieldType();
                        switch (type) {
                            case 1:
                                if (field.getRequired() == 1 && StringUtils.isBlank(strTmp)) {
                                    flag = true;
                                }

                                if (strTmp.length() > field.getFieldLength()) {
                                    flag = true;
                                }

                                break;
                            default:
                                if (!strTmp.matches(SysRegex.NUMBER_TYPE.regex)) {
                                    flag = true;
                                }
                                break;
                        }

                        valueField = field.getValueField();
                        if (StringUtils.isNotBlank(valueField)) {//值域
                            valueFieldList = Arrays.asList(valueField.split("@@"));
                            if (!valueFieldList.contains(strTmp)) {
                                flag = true;
                            }
                        }
                        break;
                    }
                }

                if (flag) {
                    errorFields.add(key);
                    break;
                }
            }
            if (flag) {
                failedArray.add(temp);
            } else {
                successArray.add(temp);
            }
        }
        Map<String, JSONArray> dealMap = new HashMap<String, JSONArray>();
        dealMap.put("success", successArray);
        dealMap.put("failed", failedArray);
        JSONArray errorFieldArray = new JSONArray();
        for (String errorField : errorFields) {
            errorFieldArray.add(errorField);
        }
        dealMap.put("errorFields", errorFieldArray);

        return dealMap;
    }

    /**
     * 获取字段map
     *
     * @param tableName
     * @param useCache  是否使用缓存-前端/后端
     * @return
     */
    public Map<String, String> queryFieldMap(String tableName, boolean useCache) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        List<BusCustomField> fields;
        if (useCache) {
            fields = fieldService.queryFields(tableName, "", 1, Integer.MAX_VALUE);
            for (BusCustomField field : fields) {
                fieldMap.put(field.getFieldName(), field.getRealFieldName());
            }
        } else {
            List<Map<String, Object>> f = fieldService.queryFieldsFront(tableName);
            for (Map<String, Object> map : f) {
                fieldMap.put(map.get("FIELD_NAME").toString(), map.get("REAL_FIELD_NAME").toString());
            }
        }
        return fieldMap;
    }

    /**
     * 上传数据
     *
     * @param array
     * @param tableName
     * @param needCode  是否需要平台吗
     * @param fieldMap  字段
     * @param needPack  是否需要打包
     * @return
     */
    public List<String> addDataList(JSONArray array, String tableName, String platformCode, boolean needCode, Map<String, String> fieldMap, boolean needPack) {
        List<String> idList = new ArrayList<String>();
        if (array.size() < 1) {
            return idList;
        }
        StringBuffer sql = new StringBuffer("INSERT INTO ").append(tableName).append(" (");
        JSONObject object = array.getJSONObject(0);
        Set<String> keys = object.keySet();
        if (keys.size() < 1) {
            return idList;
        }
        int fieldCount = keys.size() + 2;

        boolean existSysCreateTime = keys.contains("SYS__CREATE_TIME");
        if (existSysCreateTime) {
            fieldCount--;
        }

        for (String key : keys) {
            sql.append("`").append(fieldMap.get(key)).append("`,");
        }

        if (existSysCreateTime) {
            sql.append("`SYS__DATETIME_STAMP`, `SYS__ID`");
        } else {
            sql.append("`SYS__CREATE_TIME`,`SYS__DATETIME_STAMP`, `SYS__ID`");
        }

        if (needCode) {
            sql.append(",").append("`SYS__PLATFORM_CODE`");
            fieldCount++;
        }

        if (needPack) {
            sql.append(",").append("`PACK_STATUS`");
            fieldCount++;
        }

        sql.append(") VALUES (").append(Tools.genMark(fieldCount)).append(")");

        sql.append(" ON DUPLICATE KEY UPDATE ");

        for (String key : keys) {
            sql.append("`").append(fieldMap.get(key)).append("` = ?,");
        }

        if (existSysCreateTime) {
            sql.append(" SYS__DATETIME_STAMP = ?");
        } else {
            sql.append(" SYS__CREATE_TIME = ?, SYS__DATETIME_STAMP = ?");
        }

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> data;
        String id;
        for (int i = 0; i < array.size(); i++) {
            data = new LinkedHashMap<String, Object>();
            object = array.getJSONObject(i);
            keys = object.keySet();

            for (String key : keys) {
                String val = object.getString(key);
                data.put(key, val);
            }

            id = UUID.randomUUID().toString();
            idList.add(id);
            data.put("SYS__CREATE_TIME", System.currentTimeMillis());
            data.put("SYS__DATETIME_STAMP", TimeUtil.genCurYmdHms());
            data.put("SYS__ID", id);

            if (needCode) {
                data.put("SYS__PLATFORM_CODE", platformCode);
            }
            if (needPack) {
                data.put("PACK_STATUS", 1);
            }

            for (String key : keys) {
                String val = object.getString(key);
                data.put(key + "_u", val);
            }

            data.put("SYS__CREATE_TIME_u", System.currentTimeMillis());
            data.put("SYS__DATETIME_STAMP_u", TimeUtil.genCurYmdHms());

            dataList.add(data);
        }
        commonDao.batAddData(sql.toString(), dataList);
        return idList;
    }

    public List<Map<String, Object>> queryDataListFront(String platformCode, String tableName, String realTabName, int page, int pageSize) {

        List<BusCustomField> fields = fieldService.queryFieldsFront(tableName, 1, Integer.MAX_VALUE);

        if (fields.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }

        StringBuffer sb = new StringBuffer("SELECT ");
        String fieldName;
        for (BusCustomField field : fields) {
            fieldName = field.getFieldName();
            if (field.getExposed() == 1) {
                if ("SYS__PLATFORM_CODE".equals(fieldName) || "SYS__ID".equals(fieldName) || "SYS__CREATE_TIME".equals(fieldName) || "SYS__DATETIME_STAMP".equals(fieldName)) {
                    continue;
                }
                sb.append("`").append(field.getRealFieldName()).append("` AS ").append(fieldName).append(", ");
            }
        }

        sb.deleteCharAt(sb.length() - 2);

        sb.append("FROM ").append(realTabName).append(" WHERE SYS__PLATFORM_CODE = '").append(platformCode).append("' ");
        sb.append(" ORDER BY SYS__CREATE_TIME LIMIT ").append((page - 1) * pageSize).append(",").append(pageSize).append(" ");
        return commonDao.queryDataList(sb.toString());
    }
}
