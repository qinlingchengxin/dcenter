package net.ys.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ys.bean.BusCustomField;
import net.ys.dao.TableDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Resource
    private TableDao tableDao;

    @Resource
    private FieldService fieldService;

    /**
     * 获取同步表结构信息
     *
     * @param platformCode
     * @param syncTime
     * @return
     */
    public List<Map<String, Object>> syncTableStructureNew(String platformCode, long syncTime) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> busCustomEntityList = tableDao.syncTableStructure(platformCode, syncTime);

        if (busCustomEntityList.size() == 0) {
            return data;
        }
        List<Map<String, Object>> busCustomFields = syncFields(busCustomEntityList);
        Map<String, Object> unit;
        List<Map<String, Object>> fields;
        for (Map<String, Object> map : busCustomEntityList) {
            unit = new HashMap<String, Object>();
            fields = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> busCustomField : busCustomFields) {
                if (busCustomField.get("TABLE_NAME").equals(map.get("TABLE_NAME"))) {
                    fields.add(busCustomField);
                }
            }
            unit.put("entity", map);
            unit.put("fields", fields);
            data.add(unit);
        }

        return data;
    }

    public List<Map<String, Object>> syncFields(List<Map<String, Object>> busCustomEntityList) {
        if (busCustomEntityList.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }
        StringBuffer sb = new StringBuffer("(");
        for (Map<String, Object> busCustomEntity : busCustomEntityList) {
            sb.append("'").append(busCustomEntity.get("TABLE_NAME")).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return tableDao.syncFields(sb.toString());
    }

    public JSONArray queryDataTemplate(String tableName) {
        List<BusCustomField> fields = fieldService.queryFieldsFront(tableName, 1, Integer.MAX_VALUE);
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();
        String fieldName;
        for (BusCustomField field : fields) {
            fieldName = field.getFieldName();
            if ("SYS__ID".equals(fieldName) || "SYS__PLATFORM_CODE".equals(fieldName) || "SYS__CREATE_TIME".equals(fieldName) || "SYS__DATETIME_STAMP".equals(fieldName)) {
                continue;
            }
            if (field.getFieldType() == 1) {
                object.put(fieldName, "a");
            } else {
                object.put(fieldName, 0);
            }
        }
        jsonArray.add(object);
        return jsonArray;
    }
}
