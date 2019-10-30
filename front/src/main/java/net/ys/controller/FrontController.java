package net.ys.controller;

import net.sf.json.JSONArray;
import net.ys.bean.BusCustomEntity;
import net.ys.bean.BusCustomField;
import net.ys.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }

    //--------------------------平台实体配置-----------------------------------
    @RequestMapping(value = "busCustomEntity/list")
    public ModelAndView busCustomEntityList(HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("front/busCustomEntities");

        if (page < 1) {
            page = 1;
        }
        long count = entityService.queryEntityCountByTabName("");

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusCustomEntity> busCustomEntities;
        if ((page - 1) * pageSize < count) {
            busCustomEntities = entityService.queryEntitiesFront(page, pageSize);
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
    public ModelAndView busCustomFieldList(@RequestParam(defaultValue = "0") String tableName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int pageSize) {
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
            busCustomFields = fieldService.queryFieldsFront(tableName, page, pageSize);
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

    @RequestMapping(value = "main")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }

    @RequestMapping(value = "left")
    public ModelAndView left() {
        ModelAndView modelAndView = new ModelAndView("left");
        return modelAndView;
    }

    @RequestMapping(value = "top")
    public ModelAndView top() {
        ModelAndView modelAndView = new ModelAndView("top");
        return modelAndView;
    }

    @RequestMapping(value = "footer")
    public ModelAndView footer() {
        ModelAndView modelAndView = new ModelAndView("footer");
        return modelAndView;
    }
}
