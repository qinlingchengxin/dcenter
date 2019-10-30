package net.ys.controller;

import net.ys.bean.Admin;
import net.ys.bean.BusEnumValue;
import net.ys.bean.BusPlatform;
import net.ys.constant.GenResult;
import net.ys.service.PlatService;
import net.ys.service.SystemService;
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
@RequestMapping(value = "web/plat")
public class PlatController {

    @Resource
    private PlatService platService;

    @Resource
    private SystemService systemService;

    @RequestMapping(value = "list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("plat/list");
        if (page < 1) {
            page = 1;
        }
        long count = platService.queryPlatCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusPlatform> busPlatforms;
        if ((page - 1) * pageSize < count) {
            busPlatforms = platService.queryPlats(page, pageSize);
        } else {
            busPlatforms = new ArrayList<BusPlatform>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("busPlatforms", busPlatforms);
        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam(defaultValue = "") String code) {
        ModelAndView modelAndView = new ModelAndView("plat/edit");
        BusPlatform busPlatform;
        if ("".equals(code)) {
            busPlatform = new BusPlatform();
        } else {
            busPlatform = platService.queryPlat(code);
        }

        List<BusEnumValue> list = systemService.queryBusEnumValues(1000, 1, Integer.MAX_VALUE);
        modelAndView.addObject("busPlatform", busPlatform);
        modelAndView.addObject("areaList", list);
        return modelAndView;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(HttpSession session, BusPlatform busPlatform) {
        Admin admin = (Admin) session.getAttribute("admin");
        busPlatform = platService.updatePlat(admin, busPlatform);
        if (busPlatform == null) {
            return GenResult.FAILED.genResult();
        }
        return GenResult.SUCCESS.genResult(busPlatform);
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(HttpSession session, BusPlatform busPlatform) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            busPlatform = platService.addPlat(admin, busPlatform);
            if (busPlatform == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(busPlatform);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
