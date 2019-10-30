package net.ys.controller;

import net.ys.bean.*;
import net.ys.constant.GenResult;
import net.ys.service.CommonService;
import net.ys.service.EntityService;
import net.ys.service.FieldService;
import net.ys.service.PlatService;
import net.ys.utils.LogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "web/common")
public class CommonController {

    @Resource
    private CommonService commonService;

    @Resource
    private EntityService entityService;

    @Resource
    private PlatService platService;

    @Resource
    private FieldService fieldService;

    /**
     * 平台表权限
     *
     * @param priType
     * @param platformCode
     * @param platformName
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "priList")
    public ModelAndView priList(@RequestParam(defaultValue = "0") int priType, @RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "") String platformName, @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("pri/list");
        if (page < 1) {
            page = 1;
        }
        long count = commonService.queryPriCount(platformCode, priType);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<EntityPrivilege> entityPrivileges;
        if ((page - 1) * pageSize < count) {
            entityPrivileges = commonService.queryPris(platformCode, priType, page, pageSize);
        } else {
            entityPrivileges = new ArrayList<EntityPrivilege>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("platformCode", platformCode);
        modelAndView.addObject("platformName", platformName);
        modelAndView.addObject("priType", priType);
        modelAndView.addObject("entityPrivileges", entityPrivileges);
        return modelAndView;
    }

    @RequestMapping(value = "priEdit")
    public ModelAndView priEdit(@RequestParam(defaultValue = "1") int priType, @RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "") String platformName) {
        ModelAndView modelAndView = new ModelAndView("pri/edit");
        EntityPrivilege entityPrivilege;
        entityPrivilege = new EntityPrivilege();
        entityPrivilege.setPlatformCode(platformCode);
        entityPrivilege.setPlatformName(platformName);

        List<Map<String, Object>> tableList = commonService.queryTableList(platformCode, priType);

        modelAndView.addObject("entityPrivilege", entityPrivilege);
        modelAndView.addObject("tableList", tableList);
        modelAndView.addObject("priType", priType);
        return modelAndView;
    }

    @RequestMapping(value = "priAdd")
    @ResponseBody
    public Map<String, Object> priAdd(HttpSession session, EntityPrivilege entityPrivilege) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            if (entityPrivilege.getTableName() == null) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            boolean flag = commonService.addPri(admin, entityPrivilege);
            if (!flag) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "removePri")
    @ResponseBody
    public Map<String, Object> removePri(HttpSession session, @RequestParam(defaultValue = "") String id) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            boolean flag = commonService.removePri(admin, id);
            if (!flag) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    /**
     * 数据查询
     *
     * @param tableName
     * @param platformCode
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "queryData")
    public ModelAndView queryData(@RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("entityData/list");
        if (page < 1) {
            page = 1;
        }

        List<Map<String, Object>> entities = entityService.queryEntities();
        List<Map<String, Object>> platforms = platService.queryPlatformList();
        BusCustomEntity entity = entityService.queryEntity(tableName);

        if (entity == null || entity.getReleaseTime() == 0) {
            modelAndView.addObject("count", 0);
            modelAndView.addObject("currPage", 1);
            modelAndView.addObject("totalPage", 0);
            modelAndView.addObject("tableName", tableName);
            modelAndView.addObject("platformCode", platformCode);
            modelAndView.addObject("fields", new ArrayList<SimpleField>());
            modelAndView.addObject("entityDataList", new ArrayList<Map<String, Object>>());
            modelAndView.addObject("entities", entities);
            modelAndView.addObject("platforms", platforms);
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
        modelAndView.addObject("platformCode", platformCode);
        modelAndView.addObject("fields", fields);
        modelAndView.addObject("entityDataList", entityDataList);
        modelAndView.addObject("entities", entities);
        modelAndView.addObject("platforms", platforms);
        return modelAndView;
    }

    @RequestMapping(value = "viewData")
    public ModelAndView viewData(@RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String sysId) {
        ModelAndView modelAndView = new ModelAndView("entityData/edit");
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

    /**
     * 附件列表
     *
     * @param platformCode
     * @param tableName
     * @param fieldName
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "attachments")
    public ModelAndView attachments(@RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String fieldName, @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("attachment/list");
        if (page < 1) {
            page = 1;
        }
        long count = commonService.queryAttCount(platformCode, tableName, fieldName);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusAttachment> attachments;
        if ((page - 1) * pageSize < count) {
            attachments = commonService.queryAttachments(platformCode, tableName, fieldName, page, pageSize);
        } else {
            attachments = new ArrayList<BusAttachment>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("attachments", attachments);
        return modelAndView;
    }
}
