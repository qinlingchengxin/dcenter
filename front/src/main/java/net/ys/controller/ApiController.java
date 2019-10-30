package net.ys.controller;

import com.alibaba.fastjson.JSONArray;
import net.ys.bean.BusCustomEntity;
import net.ys.component.SysConfig;
import net.ys.constant.GenResult;
import net.ys.schedule.BuService;
import net.ys.service.CommonService;
import net.ys.service.EntityService;
import net.ys.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Resource
    private BuService buService;

    @Resource
    private CommonService commonService;

    @Resource
    private EntityService entityService;

    @RequestMapping(value = "syncTable")
    @ResponseBody
    public Map<String, Object> syncTable() {
        try {
            long start = System.currentTimeMillis();
            buService.syncTable();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "sliceSyncData")
    @ResponseBody
    public Map<String, Object> sliceSyncData() {
        try {
            long start = System.currentTimeMillis();
            buService.sliceSyncData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "syncUps", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncUps(@RequestParam(required = true, value = "table_name", defaultValue = "") String tableName, @RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode,
                                       @RequestParam(required = true, value = "data", defaultValue = "0") String data) {
        try {

            if (!"2".equals(SysConfig.transType)) {//非共享接口方式
                return GenResult.NOT_INT.genResult();
            }

            BusCustomEntity entity = entityService.queryEntityFront(tableName);

            if (entity == null) {
                return GenResult.NOT_EXIST_OR_RELEASE.genResult();
            }

            if (StringUtils.isBlank(data)) {
                return GenResult.FAILED.genResult();
            }

            JSONArray array = JSONArray.parseArray(data);
            if (array.size() < 1) {
                return GenResult.FAILED.genResult();
            }

            Map<String, JSONArray> dealData = commonService.validateData(tableName, array);//数据处理校验
            JSONArray success = dealData.get("success");
            JSONArray failed = dealData.get("failed");
            JSONArray errorFields = dealData.get("errorFields");

            if (success.size() == 0) {
                return GenResult.PARAMS_ERROR.genResult(errorFields);
            }

            Map<String, String> fieldMap = commonService.queryFieldMap(tableName, false);

            List<String> ids = commonService.addDataList(success, entity.getRealTabName(), platformCode, true, fieldMap, true);
            if (ids.size() == success.size()) {
                buService.addStock(platformCode, entity.getTableName(), success);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("failed_count", failed.size());
            map.put("failed_data", failed);
            map.put("error_fields", errorFields);
            return GenResult.SUCCESS.genResult(map);

        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    /**
     * 同步下载,api调用
     *
     * @param tableName
     * @param platformCode
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "down")
    @ResponseBody
    public Map<String, Object> down(@RequestParam(required = true, value = "table_name", defaultValue = "") String tableName, @RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode,
                                    @RequestParam(required = true, value = "page", defaultValue = "1") int page, @RequestParam(required = true, value = "page_size", defaultValue = "15") int pageSize) {
        try {
            boolean hasRelease = entityService.queryEntityRelease(tableName);

            if (!hasRelease) {
                return GenResult.NOT_EXIST_OR_RELEASE.genResult();
            }

            if (pageSize < 1 || pageSize > 20) {
                return GenResult.DOWN_LIMIT.genResult();
            }

            String realName = entityService.queryEntityRealName(tableName);

            List<Map<String, Object>> data = commonService.queryDataListFront(platformCode, tableName, realName, page, pageSize);
            return GenResult.SUCCESS.genResult(data);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
