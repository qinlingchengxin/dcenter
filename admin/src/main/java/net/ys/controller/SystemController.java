package net.ys.controller;

import net.ys.bean.BusConfig;
import net.ys.bean.BusEnum;
import net.ys.bean.BusEnumValue;
import net.ys.constant.GenResult;
import net.ys.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "web/sys")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * 系统配置
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "configList")
    public ModelAndView configList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("config/list");
        if (page < 1) {
            page = 1;
        }
        long count = systemService.queryBusConfigCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusConfig> busConfigs;
        if ((page - 1) * pageSize < count) {
            busConfigs = systemService.queryBusConfigs(page, pageSize);
        } else {
            busConfigs = new ArrayList<BusConfig>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("busConfigs", busConfigs);
        return modelAndView;
    }

    @RequestMapping(value = "configEdit")
    public ModelAndView configEdit(@RequestParam(defaultValue = "0") int code) {
        ModelAndView modelAndView = new ModelAndView("config/edit");
        BusConfig busConfig;
        if (code == 0) {//新增
            busConfig = new BusConfig();
        } else {
            busConfig = systemService.queryBusConfig(code);
        }
        modelAndView.addObject("busConfig", busConfig);
        return modelAndView;
    }

    @RequestMapping(value = "configSave")
    @ResponseBody
    public Map<String, Object> configSave(BusConfig busConfig) {
        boolean flag = systemService.updateBusConfig(busConfig);
        if (!flag) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult();
    }

    @RequestMapping(value = "configAdd")
    @ResponseBody
    public Map<String, Object> busConfigAdd(BusConfig busConfig) {
        busConfig = systemService.addBusConfig(busConfig);
        if (busConfig == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busConfig);
    }

    /**
     * 系统枚举列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "enumList")
    public ModelAndView enumList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("enum/list");
        if (page < 1) {
            page = 1;
        }
        long count = systemService.queryBusEnumCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusEnum> busEnums;
        if ((page - 1) * pageSize < count) {
            busEnums = systemService.queryBusEnums(page, pageSize);
        } else {
            busEnums = new ArrayList<BusEnum>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("busEnums", busEnums);
        return modelAndView;
    }

    @RequestMapping(value = "enumEdit")
    public ModelAndView enumEdit(@RequestParam(defaultValue = "0") int code) {
        ModelAndView modelAndView = new ModelAndView("enum/edit");
        BusEnum busEnum;
        if (code == 0) {//新增
            busEnum = new BusEnum();
        } else {
            busEnum = systemService.queryBusEnum(code);
        }
        modelAndView.addObject("busEnum", busEnum);
        return modelAndView;
    }

    @RequestMapping(value = "enumSave")
    @ResponseBody
    public Map<String, Object> enumSave(BusEnum busEnum) {
        boolean flag = systemService.updateBusEnum(busEnum);
        if (!flag) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult();
    }

    @RequestMapping(value = "enumAdd")
    @ResponseBody
    public Map<String, Object> enumAdd(BusEnum busEnum) {
        busEnum = systemService.addBusEnum(busEnum);
        if (busEnum == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busEnum);
    }

    /**
     * 系统枚举值
     *
     * @param code
     * @param enumName
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "evList")
    public ModelAndView evList(@RequestParam(defaultValue = "0") int code, @RequestParam(defaultValue = "") String enumName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("ev/list");
        if (page < 1) {
            page = 1;
        }
        long count = systemService.queryBusEnumValueCount(code);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusEnumValue> busEnumValues;
        if ((page - 1) * pageSize < count) {
            busEnumValues = systemService.queryBusEnumValues(code, page, pageSize);
        } else {
            busEnumValues = new ArrayList<BusEnumValue>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("code", code);
        modelAndView.addObject("enumName", enumName);
        modelAndView.addObject("busEnumValues", busEnumValues);
        return modelAndView;
    }

    @RequestMapping(value = "evEdit")
    public ModelAndView evEdit(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "0") int code) {
        ModelAndView modelAndView = new ModelAndView("ev/edit");
        BusEnumValue busEnumValue;
        if ("".equals(id)) {//新增
            busEnumValue = new BusEnumValue();
            busEnumValue.setEnumCode(code);
        } else {
            busEnumValue = systemService.queryBusEnumValue(id);
        }
        modelAndView.addObject("busEnumValue", busEnumValue);
        return modelAndView;
    }

    @RequestMapping(value = "evSave")
    @ResponseBody
    public Map<String, Object> evSave(BusEnumValue busEnumValue) {
        boolean flag = systemService.updateBusEnumValue(busEnumValue);
        if (!flag) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult();
    }

    @RequestMapping(value = "evAdd")
    @ResponseBody
    public Map<String, Object> evAdd(BusEnumValue busEnumValue) {
        busEnumValue = systemService.addBusEnumValue(busEnumValue);
        if (busEnumValue == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busEnumValue);
    }

}
