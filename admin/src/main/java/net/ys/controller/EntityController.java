package net.ys.controller;

import net.sf.json.JSONArray;
import net.ys.bean.Admin;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.PlatEntFilter;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "web/entity")
public class EntityController {

    @Resource
    private EntityService entityService;

    @Resource
    private FieldService fieldService;

    @RequestMapping(value = "list")
    public ModelAndView list(@RequestParam(defaultValue = "") String tableName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("entity/list");
        if (page < 1) {
            page = 1;
        }
        long count = entityService.queryEntityCountByTabName(tableName);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusCustomEntity> busCustomEntities;
        if ((page - 1) * pageSize < count) {
            busCustomEntities = entityService.queryEntitiesByTab(tableName, page, pageSize);
        } else {
            busCustomEntities = new ArrayList<BusCustomEntity>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("busCustomEntities", busCustomEntities);
        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam(defaultValue = "") String tableName) {
        ModelAndView modelAndView = new ModelAndView("entity/edit");
        BusCustomEntity busCustomEntity;
        if ("".equals(tableName)) {//新增
            busCustomEntity = new BusCustomEntity();
        } else {
            busCustomEntity = entityService.queryEntity(tableName);
        }

        modelAndView.addObject("busCustomEntity", busCustomEntity);
        return modelAndView;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(HttpSession session, BusCustomEntity busCustomEntity) {
        Admin admin = (Admin) session.getAttribute("admin");
        busCustomEntity = entityService.updateEntity(admin, busCustomEntity);
        if (busCustomEntity == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busCustomEntity);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(HttpSession session, BusCustomEntity busCustomEntity) {
        try {

            if (StringUtils.isBlank(busCustomEntity.getInChinese())) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            if (StringUtils.isBlank(busCustomEntity.getTableName())) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            if (!busCustomEntity.getTableName().matches(SysRegex.TABLE_FIELD_NAME.regex)) {
                return GenResult.TABLE_NAME_INVALID.genResult();
            }

            busCustomEntity.setRealTabName("TAB__" + busCustomEntity.getTableName().toUpperCase());//转换成大写

            BusCustomEntity entity = entityService.queryEntity(busCustomEntity.getTableName());
            if (entity != null) {
                return GenResult.TABLE_EXISTS.genResult();
            }

            Admin admin = (Admin) session.getAttribute("admin");

            busCustomEntity = entityService.addEntity(admin, busCustomEntity);
            if (busCustomEntity == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(busCustomEntity);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "release")
    @ResponseBody
    public Map<String, Object> release(HttpSession session, String tableName) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");
            entityService.entityRelease(admin, tableName);
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "remove")
    @ResponseBody
    public Map<String, Object> remove(String tableName) {
        try {

            BusCustomEntity entity = entityService.queryEntity(tableName);
            if (entity.getReleaseTime() > 0) {
                return GenResult.FAILED.genResult();
            }

            boolean flag = entityService.removeEntity(tableName);
            if (flag) {
                return GenResult.SUCCESS.genResult();
            }
            return GenResult.FAILED.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "dataTemplate")
    public ModelAndView dataTemplate(HttpServletRequest request, @RequestParam(defaultValue = "") String tableName) {
        ModelAndView modelAndView = new ModelAndView("entity/dataTmp");
        String app = request.getServletContext().getContextPath();
        JSONArray jsonArray = entityService.queryDataTemplate(tableName);
        modelAndView.addObject("dataTmp", jsonArray.toString());
        modelAndView.addObject("app", app);
        return modelAndView;
    }

    @RequestMapping(value = "dataFilterList")
    public ModelAndView dataFilterList(@RequestParam(defaultValue = "") String epId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("dataFilter/list");
        if (page < 1) {
            page = 1;
        }
        long count = entityService.queryDataFilterCount(epId);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<PlatEntFilter> platEntFilters;
        if ((page - 1) * pageSize < count) {
            platEntFilters = entityService.queryPlatEntFilters(epId, page, pageSize);
        } else {
            platEntFilters = new ArrayList<PlatEntFilter>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("epId", epId);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("platEntFilters", platEntFilters);
        return modelAndView;
    }

    @RequestMapping(value = "dataFilterEdit")
    public ModelAndView dataFilterEdit(@RequestParam(defaultValue = "") String pnId, @RequestParam(defaultValue = "") String epId) {
        ModelAndView modelAndView = new ModelAndView("dataFilter/edit");
        PlatEntFilter platEntFilter;
        if ("".equals(pnId)) {//新增
            platEntFilter = new PlatEntFilter();
            platEntFilter.setEpId(epId);
        } else {
            platEntFilter = entityService.queryPlatEntFilter(pnId);
        }

        String tableName = entityService.queryTableNameByEp(epId);
        List<Map<String, Object>> fields = fieldService.queryFieldsFilter(tableName);

        modelAndView.addObject("platEntFilter", platEntFilter);
        modelAndView.addObject("fields", fields);
        return modelAndView;
    }

    @RequestMapping(value = "dataFilterSave")
    @ResponseBody
    public Map<String, Object> dataFilterSave(PlatEntFilter platEntFilter) {

        if (StringUtils.isBlank(platEntFilter.getConValue())) {
            return GenResult.PARAMS_ERROR.genResult();
        }

        boolean flag = entityService.updatePlatEntFilter(platEntFilter);
        if (!flag) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult();
    }

    @RequestMapping(value = "dataFilterAdd")
    @ResponseBody
    public Map<String, Object> dataFilterAdd(PlatEntFilter platEntFilter) {
        try {

            if (StringUtils.isBlank(platEntFilter.getConValue())) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            platEntFilter = entityService.addPlatEntFilter(platEntFilter);
            if (platEntFilter == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(platEntFilter);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "dataFilterRemove")
    @ResponseBody
    public Map<String, Object> dataFilterRemove(String id) {
        try {
            boolean flag = entityService.dataFilterRemove(id);
            if (!flag) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult();
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
