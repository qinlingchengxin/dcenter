package net.ys.service;

import net.ys.bean.BusAttachment;
import net.ys.dao.CommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 附件
 */
@Service
public class AttService {

    @Resource
    private CommonDao commonDao;

    /**
     * 更新附件信息到具体表
     */
    public void updateData() throws UnsupportedEncodingException {
        List<BusAttachment> busAttachments = commonDao.queryAttachments();

        if (busAttachments.size() == 0) {
            return;
        }

        String[] sql = new String[2];
        List<Map<String, Object>> fields;

        for (BusAttachment busAttachment : busAttachments) {
            fields = commonDao.queryRealFields(busAttachment);
            if (fields.size() != 2) {
                continue;
            }

            sql[0] = "UPDATE BUS_ATTACHMENT SET UPDATE_STATUS = 1 WHERE ID = '" + busAttachment.getId() + "'";
            sql[1] = "UPDATE TAB__" + busAttachment.getTableName().toUpperCase() + " SET " + fields.get(1).get("REAL_FIELD_NAME") + " = '" + busAttachment.getId() + "' WHERE " + fields.get(0).get("REAL_FIELD_NAME") + " = '" + busAttachment.getDataId() + "'";
            commonDao.updateData(sql);
        }
    }
}
