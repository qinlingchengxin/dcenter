package net.ys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ys.bean.BusAttachment;
import net.ys.bean.BusConfig;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusPlatform;
import net.ys.component.SysConfig;
import net.ys.constant.GenResult;
import net.ys.constant.SysEnumCode;
import net.ys.service.*;
import net.ys.utils.LogUtil;
import net.ys.utils.RSAUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Resource
    private EntityService entityService;

    @Resource
    private PlatService platService;

    @Resource
    private LogService logService;

    @Resource
    private SystemService systemService;

    @Resource
    private TableService tableService;

    @Resource
    private CommonService commonService;

    /**
     * 获取表同步总数据
     *
     * @param tableNames
     * @param platformCode
     * @param lastSyncTime
     * @return
     */
    @RequestMapping(value = "sliceSyncData")
    @ResponseBody
    public Map<String, Object> sliceSyncData(@RequestParam(required = true, value = "real_table_names", defaultValue = "") String realTableNames, @RequestParam(required = true, value = "table_names", defaultValue = "") String tableNames,
                                             @RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode, @RequestParam(required = true, value = "last_sync_time", defaultValue = "0") long lastSyncTime) {
        try {
            long currSyncTime = System.currentTimeMillis() - 1000;//检索一秒前的数据,本次同步时间

            List<Map<String, Object>> list = commonService.sliceSyncData(platformCode, tableNames, realTableNames, lastSyncTime, currSyncTime);

            if (list == null) {
                return GenResult.FAILED.genResult();
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("currSyncTime", currSyncTime);
            map.put("list", list);
            return GenResult.SUCCESS.genResult(map);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    /**
     * 同步下载
     *
     * @param tableName
     * @param platformCode
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "syncDown")
    @ResponseBody
    public Map<String, Object> syncDown(@RequestParam(required = true, value = "table_name", defaultValue = "") String tableName, @RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode,
                                        @RequestParam(required = true, value = "start_time", defaultValue = "0") long startTime, @RequestParam(required = true, value = "end_time", defaultValue = "0") long endTime,
                                        @RequestParam(required = true, value = "page", defaultValue = "1") int page, @RequestParam(required = true, value = "page_size", defaultValue = "15") int pageSize) {
        try {
            boolean hasRelease = entityService.queryEntityRelease(tableName);

            if (!hasRelease) {
                return GenResult.NOT_EXIST_OR_RELEASE.genResult();
            }

            String realTabName = commonService.queryTablePrivilege(tableName, platformCode, 2);
            if ("".equals(realTabName)) {
                return GenResult.NO_PRIVILEGE.genResult();
            }

            List<Map<String, Object>> data = commonService.queryDataList(platformCode, tableName, realTabName, startTime, endTime, page, pageSize);
            String result = JSONArray.toJSONString(data, SerializerFeature.WriteMapNullValue);
            logService.addTransLog(platformCode, tableName, 1, 1, result, "", "下载" + data.size() + "条数据", data.size());

            BusPlatform busPlatform = platService.queryPlat(platformCode);
            String encodedData = RSAUtil.encrypt(result, busPlatform.getPublicKey(), true);
            return GenResult.SUCCESS.genResult(encodedData);
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
            String realTabName = commonService.queryTablePrivilege(tableName, platformCode, 2);
            if ("".equals(realTabName)) {
                return GenResult.NO_PRIVILEGE.genResult();
            }

            List<Map<String, Object>> data = commonService.queryDataList(platformCode, tableName, realTabName, -1, -1, page, pageSize);

            String result = JSONArray.toJSONString(data, SerializerFeature.WriteMapNullValue);
            BusPlatform busPlatform = platService.queryPlat(platformCode);
            String encodedData = RSAUtil.encrypt(result, busPlatform.getPublicKey(), true);
            return GenResult.SUCCESS.genResult(encodedData);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    /**
     * 上传数据
     *
     * @param tableName
     * @param platformCode
     * @param data
     * @return
     */
    @RequestMapping(value = "syncUps", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncUps(@RequestParam(required = true, value = "table_name", defaultValue = "") String tableName, @RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode,
                                       @RequestParam(required = true, value = "data", defaultValue = "") String data) {
        String decryptData = "";
        try {
            boolean hasRelease = entityService.queryEntityRelease(tableName);

            if (!hasRelease) {
                return GenResult.NOT_EXIST_OR_RELEASE.genResult();
            }

            String realTableName = commonService.queryTablePrivilege(tableName, platformCode, 1);
            if ("".equals(realTableName)) {
                return GenResult.NO_PRIVILEGE.genResult();
            }

            if (StringUtils.isBlank(data)) {
                return GenResult.NO_DATA.genResult();
            }

            BusPlatform busPlatform = platService.queryPlat(platformCode);
            decryptData = RSAUtil.decrypt(data, busPlatform.getPublicKey(), true);

            if ("".equals(decryptData)) {
                return GenResult.DECRYPT_DATA_ERROR.genResult();
            }
            JSONArray array;
            try {
                array = JSONArray.parseArray(decryptData);
            } catch (Exception e) {
                return GenResult.IS_NOT_JSON_ARRAY.genResult();
            }

            if (array.size() < 1) {
                return GenResult.NO_DATA.genResult();
            }

            BusConfig busConfig = systemService.queryBusConfig(SysEnumCode.DATA_UP_LIMIT.code);
            if (array.size() > busConfig.getVi()) {
                return GenResult.DATA_UP_LIMIT.genResult(busConfig.getVi());
            }

            Map<String, JSONArray> dealData = commonService.validateData(tableName, array);//数据处理校验
            JSONArray success = dealData.get("success");
            JSONArray failed = dealData.get("failed");
            JSONArray errorFields = dealData.get("errorFields");

            if (success.size() == 0) {
                return GenResult.NO_VALID_DATA.genResult(errorFields);
            }

            Map<String, String> fieldMap = commonService.queryFieldMap(tableName, true);

            //判断字段是否存在
            JSONObject object = array.getJSONObject(0);
            Set<String> keys = object.keySet();
            if (keys.size() < 1) {
                return GenResult.PARAMS_ERROR.genResult();
            }
            StringBuffer errFields = new StringBuffer();
            for (String key : keys) {
                String val = fieldMap.get(key);
                if (val == null) {
                    errFields.append(key).append(",");
                }
            }

            if (errFields.length() > 0) {
                return GenResult.FIELD_NOT_EXIT.genResult(errorFields);
            }

            List<String> ids = commonService.addDataList(success, realTableName, platformCode, true, fieldMap, false);

            if (ids.size() > 0) {

                BusCustomEntity entity = entityService.queryEntity(tableName);
                String callBackUrl = entity.getCallbackUrl();
                if (callBackUrl.endsWith("?guid")) {
                    ids = getDataIds(success);
                    callBackUrl = callBackUrl.substring(0, callBackUrl.lastIndexOf("?"));
                }
                entityService.entityCallBack(tableName, ids, callBackUrl);
                logService.addTransLog(platformCode, tableName, 1, 0, decryptData, failed.toString(), "success:" + success.size() + " failed:" + failed.size(), success.size());
            } else {
                logService.addTransLog(platformCode, tableName, 0, 0, decryptData, failed.toString(), "", 0);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("failed_count", failed.size());
            map.put("failed_data", failed);
            map.put("error_fields", errorFields);
            return GenResult.SUCCESS.genResult(map);
        } catch (Exception e) {
            LogUtil.error(e);
            logService.addTransLog(platformCode, tableName, 0, 0, decryptData, "", "", 0);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    private List<String> getDataIds(JSONArray array) {
        List<String> idList = new ArrayList<String>();
        JSONObject object;
        for (int i = 0; i < array.size(); i++) {
            object = array.getJSONObject(i);
            Set<String> keys = object.keySet();
            for (String key : keys) {
                if ("guid".equals(key)) {
                    idList.add(object.getString(key));
                    break;
                }
            }
        }
        return idList;
    }

    /**
     * 同步表结构
     *
     * @param platformCode
     * @param syncTime
     * @return
     */
    @RequestMapping(value = "syncTableStructure")
    @ResponseBody
    public Map<String, Object> syncTableStructure(@RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode, @RequestParam(required = true, value = "sync_time", defaultValue = "0") long syncTime) {
        try {
            List<Map<String, Object>> data = tableService.syncTableStructureNew(platformCode, syncTime);
            return GenResult.SUCCESS.genResult(data);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    /**
     * 附件信息上传，信息
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(required = true, value = "platform_code", defaultValue = "") String platformCode, @RequestParam(required = true, value = "table_name", defaultValue = "") String tableName,
                                      @RequestParam(required = true, value = "field_name", defaultValue = "") String fieldName, @RequestParam(required = true, value = "data_pri_field", defaultValue = "") String dataPriField,
                                      @RequestParam(required = true, value = "data_id", defaultValue = "") String dataId, @RequestParam(required = true, value = "file_name") String fileName,
                                      @RequestParam(required = true, value = "att_name") String attName, @RequestParam(required = true) String path) {
        try {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            String fileId = UUID.randomUUID().toString();
            BusAttachment attachment = new BusAttachment(fileId, path, attName, fileName, platformCode, tableName, fieldName, dataPriField, dataId, System.currentTimeMillis());
            boolean flag = commonService.addAttachment(attachment);
            if (!flag) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(fileId);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @ResponseBody
    @RequestMapping(value = "upload/file", method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam(required = true, value = "date_path") String datePath, @RequestParam(required = true, value = "file_alias") String fileAlias, @RequestParam(required = true, value = "file_len") long fileLen,
                                          @RequestParam(required = true, value = "start_point") long startPoint, @RequestParam MultipartFile file) {
        try {

            String storePath = SysConfig.attachmentPath + "/" + datePath;
            File tmp = new File(storePath);
            if (!tmp.exists()) {
                tmp.mkdirs();
            }

            InputStream is = file.getInputStream();
            String targetFile = storePath + "/" + fileAlias;
            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
            if (!new File(targetFile).exists()) {
                randomAccessFile.setLength(fileLen);
            }
            randomAccessFile.seek(startPoint);
            int len;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) > 0) {
                randomAccessFile.write(bytes, 0, len);
            }
            randomAccessFile.close();
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.debug(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @ResponseBody
    @RequestMapping(value = "down/file", method = RequestMethod.GET)
    public void download(HttpServletRequest req, HttpServletResponse response, String id) throws IOException {

        BusAttachment file = commonService.queryAttachment(id);
        if (file == null) {
            return;
        }

        String filePath = SysConfig.attachmentPath + file.getPath() + "/" + file.getAttName();
        String srcName = file.getSrcName();
        String agent = req.getHeader("USER-AGENT").toLowerCase();
        if (agent.indexOf("firefox") > -1) {
            srcName = new String(srcName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            srcName = URLEncoder.encode(srcName, "UTF-8");
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + srcName + "\"");

        InputStream is = new FileInputStream(filePath);
        ServletOutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) > 0) {
            out.write(bytes, 0, len);
            out.flush();
        }
        out.close();
        is.close();
    }
}
