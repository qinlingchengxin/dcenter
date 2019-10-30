package net.ys.service;

import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusCustomField;
import net.ys.dao.EntityDao;
import net.ys.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EntityService {

    @Resource
    private EntityDao entityDao;

    public long queryEntityCountByTabName(String tableName) {
        return entityDao.queryEntityCountByTabName(tableName);
    }

    public List<BusCustomEntity> queryEntitiesFront(int page, int pageSize) {
        return entityDao.queryEntitiesFront(page, pageSize);
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

    public BusCustomEntity queryEntityFront(String tableName) {
        return entityDao.queryEntityFront(tableName);
    }

    public boolean queryEntityRelease(String tableName) {
        return entityDao.queryEntityRelease(tableName);
    }

    public String queryEntityRealName(String tableName) {
        return entityDao.queryEntityRealName(tableName);
    }
}
