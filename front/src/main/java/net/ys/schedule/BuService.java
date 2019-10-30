package net.ys.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ys.bean.*;
import net.ys.component.SysConfig;
import net.ys.dao.BuDao;
import net.ys.service.EntityService;
import net.ys.service.TableService;
import net.ys.utils.LogUtil;
import net.ys.utils.req.HttpClient;
import net.ys.utils.req.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class BuService {

    @Resource
    private TableService tableService;

    @Resource
    private EntityService entityService;

    @Resource
    private BuDao buDao;

    private static final int PAGE_SIZE = 100;//每页下载数量

    /**
     * 同步表结构
     */
    public void syncTable() throws IOException {
        System.out.println("syncTable----->" + System.currentTimeMillis());

        long syncTime = tableService.queryTableSyncTime();//获取本地同步时间
        String url = SysConfig.decenterUrl + "/api/syncTableStructure.do?platform_code=" + SysConfig.platformCode + "&sync_time=" + syncTime;
        HttpResponse response = HttpClient.doGet(url);
        if (response.getCode() == 200) {
            String resp = response.getValue();
            JSONObject jsonObject = JSONObject.parseObject(resp);
            if (jsonObject.getInteger("code") == 1000) {
                JSONArray entities = jsonObject.getJSONArray("data");
                if (entities.size() == 0) {
                    return;
                }
                long time = System.currentTimeMillis();
                List<String> sqlList = new ArrayList<String>();
                String entTemp;
                String fieTemp;
                List<String> ids = tableService.queryTabFieldIds();

                JSONObject object;
                JSONObject entityObj;
                JSONArray fieldListObj;
                JSONObject fieldObj;
                BusCustomEntity entity;
                List<BusCustomField> fields;
                BusCustomField fieldTmp;
                for (int i = 0; i < entities.size(); i++) {
                    object = entities.getJSONObject(i);
                    entityObj = object.getJSONObject("entity");
                    entity = new BusCustomEntity(entityObj.getString("ID"), entityObj.getString("REAL_TAB_NAME"), entityObj.getString("TABLE_NAME"), entityObj.getString("IN_CHINESE"), entityObj.getString("CALLBACK_URL"), entityObj.getString("DESCRIPTION"), entityObj.getString("CREATE_AID"), entityObj.getLong("CREATE_TIME"), entityObj.getString("UPDATE_AID"), entityObj.getLong("UPDATE_TIME"), entityObj.getString("RELEASE_AID"), entityObj.getLong("RELEASE_TIME"));
                    if (!ids.contains(entity.getId())) {
                        entTemp = "INSERT INTO BUS_CUSTOM_ENTITY ( ID, REAL_TAB_NAME, TABLE_NAME, IN_CHINESE, CALLBACK_URL, DESCRIPTION, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME, RELEASE_AID, RELEASE_TIME ) VALUES ('" + entity.getId() + "','" + entity.getRealTabName() + "','" + entity.getTableName() + "','" + entity.getInChinese().replaceAll("'", "\\\\'") + "','" + entity.getCallbackUrl() + "','" + entity.getDescription().replaceAll("'", "\\\\'") + "','" + entity.getCreateAid() + "'," + entity.getCreateTime() + ",'" + entity.getUpdateAid() + "'," + entity.getUpdateTime() + ",'" + entity.getReleaseAid() + "'," + time + ")";
                    } else {
                        entTemp = "UPDATE BUS_CUSTOM_ENTITY SET IN_CHINESE = '" + entity.getInChinese().replaceAll("'", "\\\\'") + "', CALLBACK_URL = '" + entity.getCallbackUrl() + "', DESCRIPTION = '" + entity.getDescription().replaceAll("'", "\\\\'") + "', UPDATE_AID = '" + entity.getUpdateAid() + "', UPDATE_TIME = " + entity.getUpdateTime() + ", RELEASE_AID = '" + entity.getReleaseAid() + "', RELEASE_TIME = " + time + " WHERE ID = '" + entity.getId() + "'";
                    }
                    sqlList.add(entTemp);

                    fieldListObj = object.getJSONArray("fields");
                    fields = new ArrayList<BusCustomField>();
                    for (int j = 0; j < fieldListObj.size(); j++) {
                        fieldObj = fieldListObj.getJSONObject(j);
                        fieldTmp = new BusCustomField(fieldObj.getString("ID"), fieldObj.getString("TABLE_NAME"), fieldObj.getString("FIELD_NAME"), fieldObj.getString("REAL_FIELD_NAME"), fieldObj.getString("IN_CHINESE"), fieldObj.getInteger("FIELD_TYPE"), fieldObj.getInteger("FIELD_LENGTH"), fieldObj.getInteger("PRECISIONS"), fieldObj.getInteger("REQUIRED"), fieldObj.getString("VALUE_FIELD"), fieldObj.getInteger("UNIQUE_KEY"), fieldObj.getInteger("EXPOSED"), fieldObj.getString("REMARKS"), fieldObj.getString("CREATE_AID"), fieldObj.getLong("CREATE_TIME"), fieldObj.getString("UPDATE_AID"), fieldObj.getLong("UPDATE_TIME"));
                        if (!ids.contains(fieldTmp.getId())) {
                            fieTemp = "INSERT INTO BUS_CUSTOM_FIELD ( ID, TABLE_NAME, FIELD_NAME, REAL_FIELD_NAME, IN_CHINESE, FIELD_TYPE, FIELD_LENGTH, PRECISIONS, VALUE_FIELD, UNIQUE_KEY, EXPOSED, REMARKS, CREATE_AID, CREATE_TIME, UPDATE_AID, UPDATE_TIME, RELEASE_TIME, REQUIRED ) VALUES ( '" + fieldTmp.getId() + "','" + fieldTmp.getTableName() + "','" + fieldTmp.getFieldName() + "','" + fieldTmp.getRealFieldName() + "','" + fieldTmp.getInChinese().replaceAll("'", "\\\\'") + "'," + fieldTmp.getFieldType() + "," + fieldTmp.getFieldLength() + "," + fieldTmp.getPrecisions() + ",'" + fieldTmp.getValueField().replaceAll("'", "\\\\'") + "'," + fieldTmp.getUniqueKey() + "," + fieldTmp.getExposed() + ",'" + fieldTmp.getRemarks().replaceAll("'", "\\\\'") + "','" + fieldTmp.getCreateAid() + "'," + fieldTmp.getCreateTime() + ",'" + fieldTmp.getUpdateAid() + "'," + fieldTmp.getUpdateTime() + "," + time + ", " + fieldTmp.getRequired() + " )";
                            fields.add(fieldTmp);
                        } else {
                            fieTemp = "UPDATE BUS_CUSTOM_FIELD SET IN_CHINESE = '" + fieldTmp.getInChinese().replaceAll("'", "\\\\'") + "', FIELD_TYPE = " + fieldTmp.getFieldType() + ", FIELD_LENGTH = " + fieldTmp.getFieldLength() + ", PRECISIONS = " + fieldTmp.getPrecisions() + ", REQUIRED = " + fieldTmp.getRequired() + ", VALUE_FIELD = '" + fieldTmp.getValueField().replaceAll("'", "\\\\'") + "', UNIQUE_KEY = " + fieldTmp.getUniqueKey() + ", EXPOSED = " + fieldTmp.getExposed() + ", REMARKS = '" + fieldTmp.getRemarks().replaceAll("'", "\\\\'") + "', UPDATE_AID = '" + fieldTmp.getUpdateAid() + "', UPDATE_TIME = " + fieldTmp.getUpdateTime() + ", RELEASE_TIME = " + time + " WHERE ID = '" + fieldTmp.getId() + "'";
                        }
                        sqlList.add(fieTemp);
                    }
                    if (!ids.contains(entity.getId())) {
                        entityService.createTable(entity, fields, true);
                    } else {
                        entityService.updateTable(entity, fields);
                    }
                }
                tableService.batExecuteSql(sqlList);
            }
        }
    }

    /**
     * 下载切分库
     */
    public void sliceSyncData() {
        try {
            System.out.println("sliceSyncData----->" + System.currentTimeMillis());

            List<Map<String, Object>> tables = tableService.queryTableNames();//获取有权限的表名
            if (tables.size() < 1) {
                return;
            }

            StringBuffer realTabNames = new StringBuffer(tables.get(0).get("REAL_TAB_NAME").toString());
            StringBuffer tabNames = new StringBuffer(tables.get(0).get("TABLE_NAME").toString());
            for (int i = 1, j = tables.size(); i < j; i++) {
                realTabNames.append(",").append(tables.get(i).get("REAL_TAB_NAME").toString());
                tabNames.append(",").append(tables.get(i).get("TABLE_NAME").toString());
            }

            String lastSyncTime = tableService.queryDataSyncTime();//获取本地表数据最后一次同步时间

            String url = SysConfig.decenterUrl + "/api/sliceSyncData.do";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("last_sync_time", lastSyncTime);
            map.put("platform_code", SysConfig.platformCode);
            map.put("real_table_names", realTabNames.toString());
            map.put("table_names", tabNames.toString());
            HttpResponse response = HttpClient.doPost(url, map);
            if (response.getCode() == 200) {
                String resp = response.getValue();
                JSONObject jsonObject = JSONObject.parseObject(resp);
                if (jsonObject.getInteger("code") == 1000) {
                    JSONObject result = jsonObject.getJSONObject("data");
                    addSliceSyncData(result, lastSyncTime);
                }
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    private void addSliceSyncData(JSONObject result, String lastSyncTime) {
        String currSyncTime = result.getString("currSyncTime");
        JSONArray array = result.getJSONArray("list");
        JSONObject tmp;
        String tableName;
        int dataCount;
        int page;
        List<String> sql = new ArrayList<String>();
        sql.add("UPDATE BUS_CONFIG SET VI = " + currSyncTime + " WHERE CODE = 1000");
        for (int i = 0; i < array.size(); i++) {
            tmp = array.getJSONObject(i);
            tableName = tmp.getString("TABLE_NAME");
            dataCount = tmp.getInteger("DATA_COUNT");
            if (dataCount == 0) {
                continue;
            }

            page = dataCount / PAGE_SIZE + (dataCount % PAGE_SIZE == 0 ? 0 : 1);
            for (int j = 1; j <= page; j++) {
                sql.add("INSERT INTO DOWN_STOCK ( ID, TABLE_NAME, START_TIME, END_TIME, PAGE, PAGE_SIZE, CREATE_TIME ) VALUES ('" + UUID.randomUUID().toString() + "','" + tableName + "'," + lastSyncTime + "," + currSyncTime + "," + j + "," + PAGE_SIZE + "," + System.currentTimeMillis() + ")");
            }
        }

        buDao.addSliceSyncData(sql);
    }

    public void addStock(String platformCode, String tableName, JSONArray success) {
        buDao.addStock(platformCode, tableName, success.toString());
    }

    /**
     * @param id
     * @param status
     * @return
     */
    public boolean chgStock(String id, int status) {
        return buDao.chgStock(id, status);
    }

    public List<SyncTable> queryAllTables() {
        return buDao.queryAllTables();
    }

    public String queryFields(String tableName) {
        List<String> fields = buDao.queryFields(tableName);
        StringBuffer sb = new StringBuffer();
        for (String field : fields) {
            if ("SYS__ID".equals(field) || "SYS__PLATFORM_CODE".equals(field) || "SYS__CREATE_TIME".equals(field) || "SYS__DATETIME_STAMP".equals(field)) {
                continue;
            }
            sb.append(field).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 获取需要打包的数据的总条数
     *
     * @param platformCode
     * @param tableName
     * @param lastPackTime
     * @param time
     * @return
     */
    public long queryDataPackCount(String platformCode, String tableName, long lastPackTime, long time) {
        return buDao.queryDataPackCount(platformCode, tableName, lastPackTime, time);
    }

    public List<Map<String, Object>> queryPackDataByPage(String platformCode, SyncTable table, long time, String fieldStr, int page, int pageSize) {
        return buDao.queryPackDataByPage(platformCode, table, time, fieldStr, page, pageSize);
    }

    public void chgStockPackStatus(SyncTable table, long time) {
        buDao.chgStockPackStatus(table, time);
    }

    /**
     * @param status 状态 0-待传输/1-正在传输/2-传输成功/3-传输失败
     * @return
     */
    public List<Stock> queryStocks(int status, long time) {
        return buDao.queryStocks(status, time);
    }

    public List<DownStock> queryDownStocks(int status, long now) {
        return buDao.queryDownStocks(status, now);
    }

    public void updateDownStockStatus(DownStock downStock, int status) {
        buDao.updateDownStockStatus(downStock, status);
    }
}
