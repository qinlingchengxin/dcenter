package net.ys.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.ys.bean.Admin;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusCustomField;
import net.ys.bean.PlatEntFilter;
import net.ys.dao.EntityDao;
import net.ys.threadpool.ThreadPoolManager;
import net.ys.utils.Tools;
import net.ys.utils.req.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EntityService {

    @Resource
    private FieldService fieldService;

    @Resource
    private EntityDao entityDao;

    public long queryEntityCountByTabName(String tableName) {
        return entityDao.queryEntityCountByTabName(tableName);
    }

    /**
     * 前置机用拥有权限的表的个数
     *
     * @param platformCode
     * @return
     */
    public long queryEntityCount(String platformCode) {
        return entityDao.queryEntityCount(platformCode);
    }

    public List<BusCustomEntity> queryEntitiesByTab(String tableName, int page, int pageSize) {
        return entityDao.queryEntitiesByTab(tableName, page, pageSize);
    }

    public List<BusCustomEntity> queryEntities(String platformCode, int page, int pageSize) {
        return entityDao.queryEntities(platformCode, page, pageSize);
    }

    public BusCustomEntity queryEntity(String tableName) {
        return entityDao.queryEntity(tableName);
    }

    public BusCustomEntity updateEntity(Admin admin, BusCustomEntity busCustomEntity) {
        busCustomEntity.setUpdateAid(admin.getId());
        BusCustomEntity entity = queryEntity(busCustomEntity.getTableName());
        boolean chgUpt = false; //是否更新《更新时间》
        if (!entity.getCallbackUrl().equals(busCustomEntity.getCallbackUrl())) {
            chgUpt = true;
        }

        boolean flag = entityDao.updateEntity(busCustomEntity, chgUpt);
        if (flag) {
            entity.setUpdateAid(admin.getUsername());
            entity.setInChinese(busCustomEntity.getInChinese());
            entity.setDescription(busCustomEntity.getDescription());
            entity.setCallbackUrl(busCustomEntity.getCallbackUrl());
            if (chgUpt) {
                entity.setUpdateTime(System.currentTimeMillis());
            }
            return entity;
        }
        return null;
    }

    public BusCustomEntity addEntity(Admin admin, BusCustomEntity busCustomEntity) {
        long time = System.currentTimeMillis();
        busCustomEntity.setCreateAid(admin.getId());
        busCustomEntity.setId(UUID.randomUUID().toString());

        boolean flag = entityDao.addEntity(busCustomEntity, time);
        if (flag) {
            busCustomEntity.setCreateAid(admin.getUsername());
            busCustomEntity.setCreateTime(time);
            busCustomEntity.setUpdateAid(admin.getUsername());
            busCustomEntity.setUpdateTime(time);
            busCustomEntity.setReleaseTime(0);
            //添加系统内置字段
            addSysField(busCustomEntity.getTableName(), admin, time);
            return busCustomEntity;
        }
        return null;
    }

    private void addSysField(String tableName, Admin admin, long time) {
        BusCustomField id = new BusCustomField(UUID.randomUUID().toString(), tableName, "SYS__ID", "SYS__ID", "主键", 1, 40, 0, 1, "", 0, 1, "主键字段", admin.getId(), time, admin.getId(), time);
        BusCustomField createTime = new BusCustomField(UUID.randomUUID().toString(), tableName, "SYS__CREATE_TIME", "SYS__CREATE_TIME", "创建时间", 2, 20, 0, 1, "", 0, 1, "系统创建时间", admin.getId(), time, admin.getId(), time);
        BusCustomField platformCode = new BusCustomField(UUID.randomUUID().toString(), tableName, "SYS__PLATFORM_CODE", "SYS__PLATFORM_CODE", "数据上传平台编码", 1, 50, 0, 1, "", 0, 1, "数据来源", admin.getId(), time, admin.getId(), time);
        BusCustomField sysDateTimeStamp = new BusCustomField(UUID.randomUUID().toString(), tableName, "SYS__DATETIME_STAMP", "SYS__DATETIME_STAMP", "数据入库时间", 1, 20, 0, 1, "", 0, 1, "yyyyMMddHHmmss", admin.getId(), time, admin.getId(), time);
        fieldService.addField(admin, id);
        fieldService.addField(admin, createTime);
        fieldService.addField(admin, platformCode);
        fieldService.addField(admin, sysDateTimeStamp);
    }

    public boolean updateEntityUpdateTime(String tableName) {
        return entityDao.updateEntityUpdateTime(tableName);
    }

    public boolean entityRelease(Admin admin, String tableName) {
        //检索实体 发布时间为""则为创建，否则为更新
        BusCustomEntity entity = queryEntity(tableName);

        //检索更改字段
        List<BusCustomField> fields = queryUpdateFields(tableName);
        boolean flag = false;
        if (entity.getReleaseTime() == 0) {
            createTable(entity, fields, false);
            flag = true;
        } else if (entity.getUpdateTime() > entity.getReleaseTime()) {
            updateTable(entity, fields);
            flag = true;
        }
        if (flag) {
            long time = System.currentTimeMillis();
            flag = entityDao.updateReleaseStatus(admin, tableName, time);
        }
        return flag;
    }

    public void updateTable(BusCustomEntity entity, List<BusCustomField> fields) {

        if (fields.size() == 0) {
            return;
        }

        StringBuffer sb = new StringBuffer("ALTER TABLE `");
        StringBuffer ukSb = new StringBuffer();
        sb.append(entity.getRealTabName()).append("` ");
        for (BusCustomField field : fields) {
            sb.append("ADD COLUMN `").append(field.getRealFieldName()).append("` ");
            switch (field.getFieldType()) {
                case 0:
                    sb.append("int(").append(field.getFieldLength()).append(")");
                    break;
                case 1:
                    if (field.getFieldLength() > 500) {
                        sb.append("mediumtext COLLATE utf8mb4_unicode_ci ");
                    } else {
                        sb.append("varchar(").append(field.getFieldLength()).append(") COLLATE utf8mb4_unicode_ci ");
                    }
                    break;
                case 2:
                    sb.append("bigint(").append(field.getFieldLength()).append(")");
                    break;
                case 3:
                    sb.append("decimal(").append(field.getFieldLength()).append(",").append(field.getPrecisions()).append(")");
                    break;
                default:
                    break;
            }

            switch (field.getFieldType()) {
                case 0:
                case 2:
                case 3:
                    sb.append(" DEFAULT 0 ");
                    break;
                case 1:
                    if (field.getFieldLength() <= 500) {
                        sb.append(" DEFAULT '' ");
                    }
                    break;
            }

            if (StringUtils.isNotBlank(field.getRemarks())) {
                sb.append(" COMMENT '").append(field.getRemarks()).append("'");
            } else {
                sb.append(" COMMENT '' ");
            }

            sb.append(",");

            if (field.getUniqueKey() == 1) {
                if (ukSb.length() == 0) {
                    ukSb.append(" ADD UNIQUE INDEX `UK_").append(Tools.randomString(8).toUpperCase()).append("` (`").append(field.getRealFieldName()).append("`");
                } else {
                    ukSb.append(", `").append(field.getRealFieldName()).append("`");
                }
            }

        }

        if (ukSb.length() > 0) {
            ukSb.append(") USING BTREE ");
            sb.append(ukSb);
        } else {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append(";");

        entityDao.executeSql(sb.toString());
    }

    public void createTable(BusCustomEntity entity, List<BusCustomField> fields, boolean isFront) {
        StringBuffer sb = new StringBuffer();
        StringBuffer indexSb = new StringBuffer(",PRIMARY KEY (`SYS__ID`), INDEX `IDX_CREATE_TIME` (`SYS__CREATE_TIME`) USING BTREE , INDEX `IDX_PLATFORM_CODE` (`SYS__PLATFORM_CODE`) USING BTREE ");
        StringBuffer ukSb = new StringBuffer();
        sb.append("CREATE TABLE `").append(entity.getRealTabName()).append("` (`SYS__ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键' ");
        for (BusCustomField field : fields) {
            if ("SYS__ID".equals(field.getRealFieldName())) {
                continue;
            }
            sb.append(",`").append(field.getRealFieldName()).append("` ");
            switch (field.getFieldType()) {
                case 0:
                    sb.append("int(").append(field.getFieldLength()).append(")");
                    break;
                case 1:
                    if (field.getFieldLength() > 500) {
                        sb.append("mediumtext COLLATE utf8mb4_unicode_ci ");
                    } else {
                        sb.append("varchar(").append(field.getFieldLength()).append(") COLLATE utf8mb4_unicode_ci ");
                    }
                    break;
                case 2:
                    sb.append("bigint(").append(field.getFieldLength()).append(")");
                    break;
                case 3:
                    sb.append("decimal(").append(field.getFieldLength()).append(",").append(field.getPrecisions()).append(")");
                    break;
            }

            switch (field.getFieldType()) {
                case 0:
                case 2:
                case 3:
                    sb.append(" DEFAULT 0 ");
                    break;
                case 1:
                    if (field.getFieldLength() <= 500) {
                        sb.append(" DEFAULT '' ");
                    }
                    break;
            }

            if (StringUtils.isNotBlank(field.getRemarks())) {
                sb.append(" COMMENT '").append(field.getRemarks()).append("'");
            } else {
                sb.append(" COMMENT '' ");
            }

            if (field.getUniqueKey() == 1) {
                if (ukSb.length() == 0) {
                    ukSb.append("UNIQUE INDEX `UK_").append(Tools.randomString(8).toUpperCase()).append("` (`").append(field.getRealFieldName()).append("`");
                } else {
                    ukSb.append(", `").append(field.getRealFieldName()).append("`");
                }
            }
        }

        if (isFront) {
            sb.append(", `PACK_STATUS` int(2) DEFAULT 0 COMMENT '打包状态 0-未打包/1-已打包' ");
        }

        if (ukSb.length() > 0) {
            ukSb.append(") USING BTREE ");

            indexSb.append(", ").append(ukSb);
        }

        StringBuffer sql = sb.append(indexSb).append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");
        entityDao.executeSql(sql.toString());

        if (isFront) {
            entityDao.addTablePack(entity.getTableName());
        }
    }

    private List<BusCustomField> queryUpdateFields(String tableName) {
        return entityDao.queryUpdateFields(tableName);
    }

    public boolean removeEntity(String tableName) {
        return entityDao.removeEntity(tableName);
    }

    public JSONArray queryDataTemplate(String tableName) {
        List<BusCustomField> fields = fieldService.queryFields(tableName, "", 1, Integer.MAX_VALUE);
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

    public List<Map<String, Object>> queryEntities() {
        return entityDao.queryEntityList();
    }

    public boolean queryEntityRelease(String tableName) {
        return entityDao.queryEntityRelease(tableName);
    }

    public void entityCallBack(final String tableName, final List<String> ids, final String callBackUrl) throws IOException {
        if (!"".equals(callBackUrl)) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        String idList = ids.toString().replaceAll("\\[|\\]|\\s", "");
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("table_name", tableName);
                        map.put("guid", idList);
                        HttpClient.doPost(callBackUrl, map);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
            ThreadPoolManager.INSTANCE.complexPool.doIt(r);
        }
    }

    public List<Map<String, Object>> queryEntityByPlatCode(String platformCode) {
        return entityDao.queryEntityByPlatCode(platformCode);
    }

    public long queryDataFilterCount(String epId) {
        return entityDao.queryDataFilterCount(epId);
    }

    public List<PlatEntFilter> queryPlatEntFilters(String epId, int page, int pageSize) {
        return entityDao.queryPlatEntFilters(epId, page, pageSize);
    }

    public PlatEntFilter queryPlatEntFilter(String pnId) {
        return entityDao.queryPlatEntFilter(pnId);
    }

    public String queryTableNameByEp(String epId) {
        return entityDao.queryTableNameByEp(epId);
    }

    public boolean updatePlatEntFilter(PlatEntFilter platEntFilter) {
        return entityDao.updatePlatEntFilter(platEntFilter);
    }

    public PlatEntFilter addPlatEntFilter(PlatEntFilter platEntFilter) {
        return entityDao.addPlatEntFilter(platEntFilter);
    }

    public boolean dataFilterRemove(String id) {
        return entityDao.dataFilterRemove(id);
    }
}
