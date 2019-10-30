package net.ys.controller;

import net.ys.bean.Admin;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusCustomField;
import net.ys.constant.GenResult;
import net.ys.constant.SysRegex;
import net.ys.service.EntityService;
import net.ys.service.FieldService;
import net.ys.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "web/field")
public class FieldController {

    @Resource
    private FieldService fieldService;

    @Resource
    private EntityService entityService;

    @RequestMapping(value = "list")
    public ModelAndView list(@RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "") String fieldName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("field/list");
        if (page < 1) {
            page = 1;
        }
        long count = fieldService.queryFieldCount(tableName, fieldName);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusCustomField> busCustomFields;
        if ((page - 1) * pageSize < count) {
            busCustomFields = fieldService.queryFields(tableName, fieldName, page, pageSize);
        } else {
            busCustomFields = new ArrayList<BusCustomField>();
        }

        BusCustomEntity entity = entityService.queryEntity(tableName);
        int status = entity.getReleaseTime() > 0 ? 1 : 0;

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("fieldName", fieldName);
        modelAndView.addObject("releaseStatus", status);
        modelAndView.addObject("busCustomFields", busCustomFields);
        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam(defaultValue = "") String fieldName, @RequestParam(defaultValue = "") String tableName) {
        ModelAndView modelAndView = new ModelAndView("field/edit");
        BusCustomField busCustomField;
        BusCustomEntity busCustomEntity = entityService.queryEntity(tableName);
        long releaseTime = busCustomEntity.getReleaseTime();

        if ("".equals(fieldName)) {//新增
            busCustomField = new BusCustomField();
            busCustomField.setTableName(tableName);
        } else {
            busCustomField = fieldService.queryField(fieldName, tableName);
        }

        modelAndView.addObject("busCustomField", busCustomField);
        modelAndView.addObject("releaseTime", releaseTime);
        return modelAndView;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(HttpSession session, BusCustomField busCustomField) {
        Admin admin = (Admin) session.getAttribute("admin");
        busCustomField = fieldService.updateField(admin, busCustomField);
        if (busCustomField == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busCustomField);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(HttpSession session, BusCustomField busCustomField) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            if (StringUtils.isBlank(busCustomField.getInChinese())) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            if (StringUtils.isBlank(busCustomField.getFieldName())) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            if (!busCustomField.getFieldName().matches(SysRegex.TABLE_FIELD_NAME.regex)) {
                return GenResult.TABLE_NAME_INVALID.genResult();
            }

            busCustomField.setRealFieldName("COL__" + busCustomField.getFieldName().toUpperCase());

            if (StringUtils.isBlank(busCustomField.getRemarks())) {
                busCustomField.setRemarks(busCustomField.getInChinese());
            }
            BusCustomField field = fieldService.queryField(busCustomField.getFieldName(), busCustomField.getTableName());
            if (field != null) {
                return GenResult.FIELD_EXISTS.genResult();
            }

            busCustomField = fieldService.addField(admin, busCustomField);
            if (busCustomField == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(busCustomField);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "remove")
    @ResponseBody
    public Map<String, Object> remove(String tableName, String fieldName) {

        BusCustomEntity entity = entityService.queryEntity(tableName);
        if (entity.getReleaseTime() > 0) {
            return GenResult.FAILED.genResult();
        }

        if ("SYS__ID".equals(fieldName) || "SYS__PLATFORM_CODE".equals(fieldName) || "SYS__CREATE_TIME".equals(fieldName) || "SYS__DATETIME_STAMP".equals(fieldName)) {
            return GenResult.FAILED.genResult();
        }

        boolean flag = fieldService.removeField(tableName, fieldName);
        if (!flag) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult();
    }
}
