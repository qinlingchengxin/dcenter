package net.ys.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ys.bean.BusCustomField;
import net.ys.dao.TableDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Resource
    private TableDao tableDao;

    @Resource
    private FieldService fieldService;

    /**
     * 获取本地同步时间
     *
     * @return
     */
    public long queryTableSyncTime() {
        return tableDao.queryTableSyncTime();
    }

    public List<String> queryTabFieldIds() {
        return tableDao.queryTabFieldIds();
    }

    public String queryDataSyncTime() {
        return tableDao.queryDataSyncTime();
    }

    public List<Map<String, Object>> queryTableNames() {
        return tableDao.queryTableNames();
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

    public void batExecuteSql(List<String> sqlList) {
        tableDao.batExecuteSql(sqlList);
    }
}
