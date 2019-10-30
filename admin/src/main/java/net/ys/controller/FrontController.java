package net.ys.controller;

import net.sf.json.JSONArray;
import net.ys.bean.*;
import net.ys.component.SysConfig;
import net.ys.service.*;
import net.ys.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "front")
public class FrontController {

    @Resource
    private FrontService frontService;

    @Resource
    private PlatService platService;

    @Resource
    private EntityService entityService;

    @Resource
    private FieldService fieldService;

    @Resource
    private CommonService commonService;

    @Resource
    private LogService logService;

    @Resource
    private TableService tableService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String doGet() {
        return "front/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView doPost(HttpSession session, String username, String password) {
        ModelAndView modelAndView = new ModelAndView("front/main");
        BusUser busUser = frontService.queryBusUser(username, password);
        if (busUser == null) {
            modelAndView.setViewName("front/login");
        } else {
            session.setAttribute("busUser", busUser);
            modelAndView.addObject("busUser", busUser);
        }
        return modelAndView;
    }

    @RequestMapping(value = "left")
    public ModelAndView left() {
        ModelAndView modelAndView = new ModelAndView("front/left");
        return modelAndView;
    }

    @RequestMapping(value = "top")
    public ModelAndView top() {
        ModelAndView modelAndView = new ModelAndView("front/top");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String now = simpleDateFormat.format(new Date());
        modelAndView.addObject("now", now);
        return modelAndView;
    }

    @RequestMapping(value = "footer")
    public ModelAndView footer() {
        ModelAndView modelAndView = new ModelAndView("front/footer");
        return modelAndView;
    }

    @RequestMapping(value = "busPlatform/edit")
    public ModelAndView busPlatformEdit(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("front/platformEdit");
        BusUser busUser = (BusUser) session.getAttribute("busUser");
        BusPlatform busPlatform = platService.queryPlat(busUser.getPlatformCode());
        modelAndView.addObject("busPlatform", busPlatform);
        return modelAndView;
    }

    //--------------------------数据查询-----------------------------------
    @RequestMapping(value = "queryData")
    public ModelAndView queryData(HttpSession session, @RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/entityDataList");
        if (page < 1) {
            page = 1;
        }
        BusUser busUser = (BusUser) session.getAttribute("busUser");

        String platformCode = busUser.getPlatformCode();

        List<Map<String, Object>> entities = entityService.queryEntityByPlatCode(platformCode);

        BusCustomEntity entity = entityService.queryEntity(tableName);

        if (entity == null) {
            modelAndView.addObject("count", 0);
            modelAndView.addObject("currPage", 1);
            modelAndView.addObject("totalPage", 0);
            modelAndView.addObject("tableName", tableName);
            modelAndView.addObject("fields", new ArrayList<SimpleField>());
            modelAndView.addObject("entityDataList", new ArrayList<Map<String, Object>>());
            modelAndView.addObject("entities", entities);
            return modelAndView;
        }

        List<SimpleField> fields = fieldService.queryEntityFields(tableName, 6);
        fields.add(new SimpleField("SYS__ID", "SYS__ID", "主键"));

        long count = commonService.queryEntityDataCount(entity.getRealTabName(), platformCode);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<Map<String, Object>> entityDataList;
        if ((page - 1) * pageSize < count) {
            entityDataList = commonService.queryEntityDataList(entity.getRealTabName(), fields, platformCode, page, pageSize);
        } else {
            entityDataList = new ArrayList<Map<String, Object>>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("fields", fields);
        modelAndView.addObject("entityDataList", entityDataList);
        modelAndView.addObject("entities", entities);
        return modelAndView;
    }

    @RequestMapping(value = "viewData")
    public ModelAndView viewData(@RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String sysId) {
        ModelAndView modelAndView = new ModelAndView("front/entityDataEdit");
        BusCustomEntity entity = entityService.queryEntity(tableName);
        if (entity == null) {
            return modelAndView;
        }

        List<SimpleField> fields = fieldService.queryEntityFields(tableName, Integer.MAX_VALUE);
        Map<String, Object> entityData = commonService.queryEntityData(entity.getRealTabName(), sysId, fields);
        modelAndView.addObject("fields", fields);
        modelAndView.addObject("entityData", entityData);
        return modelAndView;
    }


    //--------------------------数据传输日志-----------------------------------
    @RequestMapping(value = "dataTransLog/list")
    public ModelAndView dataTransLogList(HttpSession session, @RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "-1") int status, @RequestParam(defaultValue = "") String content,
                                         @RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String startTime, @RequestParam(defaultValue = "") String endTime, @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/dataTransLogList");
        if (page < 1) {
            page = 1;
        }

        BusUser busUser = (BusUser) session.getAttribute("busUser");

        String platformCode = busUser.getPlatformCode();

        long start = TimeUtil.toLongStart(startTime);
        long end = TimeUtil.toLongEnd(endTime);
        if (end == 0) {
            end = TimeUtil.doomsdayMillisecond();
        }

        List<Map<String, Object>> entities = entityService.queryEntityByPlatCode(platformCode);
        long count = 0;
        if (entities.size() > 0) {
            if (StringUtils.isBlank(tableName)) {
                tableName = String.valueOf(entities.get(0).get("TABLE_NAME"));
            }
            count = logService.queryDataTransLogCount(tableName, platformCode, type, status, content, start, end);
        }

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusDataTransLog> dataTransLogs;
        if ((page - 1) * pageSize < count) {
            dataTransLogs = logService.queryDataTransLogs(content, tableName, platformCode, type, status, page, pageSize, start, end);
        } else {
            dataTransLogs = new ArrayList<BusDataTransLog>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("type", type);
        modelAndView.addObject("status", status);
        modelAndView.addObject("dataTransLogs", dataTransLogs);
        modelAndView.addObject("entities", entities);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);
        return modelAndView;
    }

    /**
     * 数据详情
     *
     * @param logId
     * @return
     */
    @RequestMapping(value = "dataTransLog/dataDetail")
    public ModelAndView dataDetail(@RequestParam(defaultValue = "") String logId) {
        ModelAndView modelAndView = new ModelAndView("front/dataTransLogDataDetail");
        BusDataTransLog transLog;
        if ("".equals(logId)) {//新增
            transLog = new BusDataTransLog();
        } else {
            transLog = logService.queryDataTransLog(logId);
        }

        modelAndView.addObject("content", transLog.getContent());
        return modelAndView;
    }

    //--------------------------平台实体配置-----------------------------------
    @RequestMapping(value = "busCustomEntity/list")
    public ModelAndView busCustomEntityList(HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/busCustomEntities");

        BusUser busUser = (BusUser) session.getAttribute("busUser");

        if (page < 1) {
            page = 1;
        }
        long count = entityService.queryEntityCount(busUser.getPlatformCode());

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusCustomEntity> busCustomEntities;
        if ((page - 1) * pageSize < count) {
            busCustomEntities = entityService.queryEntities(busUser.getPlatformCode(), page, pageSize);

        } else {
            busCustomEntities = new ArrayList<BusCustomEntity>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("busCustomEntities", busCustomEntities);
        return modelAndView;
    }

    @RequestMapping(value = "busCustomEntity/dataTemplate")
    public ModelAndView busCustomEntityDataTmp(@RequestParam(defaultValue = "") String tableName) {
        ModelAndView modelAndView = new ModelAndView("front/entityDataTmp");
        JSONArray jsonArray = tableService.queryDataTemplate(tableName);
        modelAndView.addObject("dataTmp", jsonArray.toString());
        return modelAndView;
    }

    //--------------------------平台实体字段配置-----------------------------------
    @RequestMapping(value = "busCustomField/list")
    public ModelAndView busCustomFieldList(@RequestParam(defaultValue = "0") String tableName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/busCustomFields");
        if (page < 1) {
            page = 1;
        }
        long count = fieldService.queryFieldCount(tableName, "");

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusCustomField> busCustomFields;
        if ((page - 1) * pageSize < count) {
            busCustomFields = fieldService.queryFields(tableName, "", page, pageSize);
        } else {
            busCustomFields = new ArrayList<BusCustomField>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("busCustomFields", busCustomFields);
        return modelAndView;
    }

    //--------------------------附件列表-----------------------------------
    @RequestMapping(value = "attachments")
    public ModelAndView attachments(HttpSession session, @RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String fieldName, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/attachmentList");
        if (page < 1) {
            page = 1;
        }

        BusUser busUser = (BusUser) session.getAttribute("busUser");

        long count = commonService.queryAttCount(busUser.getPlatformCode(), tableName, fieldName);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusAttachment> attachments;
        if ((page - 1) * pageSize < count) {
            attachments = commonService.queryAttachments(busUser.getPlatformCode(), tableName, fieldName, page, pageSize);
        } else {
            attachments = new ArrayList<BusAttachment>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("attachments", attachments);
        return modelAndView;
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
