package net.ys.controller;

import net.ys.bean.Admin;
import net.ys.bean.BusUser;
import net.ys.constant.GenResult;
import net.ys.service.UserService;
import net.ys.utils.LogUtil;
import net.ys.utils.Tools;
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
import java.util.UUID;

@Controller
@RequestMapping(value = "web/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "list")
    public ModelAndView list(@RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("user/list");
        if (page < 1) {
            page = 1;
        }

        long count = userService.queryUserCount(platformCode);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusUser> busUsers;
        if ((page - 1) * pageSize < count) {
            busUsers = userService.queryUsers(platformCode, page, pageSize);
        } else {
            busUsers = new ArrayList<BusUser>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("platformCode", platformCode);
        modelAndView.addObject("busUsers", busUsers);
        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String platformCode) {
        ModelAndView modelAndView = new ModelAndView("user/edit");
        BusUser busUser;
        if ("".equals(id)) {//新增
            busUser = new BusUser();
            String pwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
            busUser.setPassword(pwd);
        } else {
            busUser = userService.queryUser(id);
        }

        modelAndView.addObject("busUser", busUser);
        modelAndView.addObject("platformCode", platformCode);
        return modelAndView;
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(HttpSession session, BusUser busUser) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            busUser.setId(UUID.randomUUID().toString());
            busUser.setPassword(Tools.genMD5(busUser.getPassword()));
            busUser.setCreateAid(admin.getId());
            busUser.setCreateTime(System.currentTimeMillis());

            boolean flag = userService.addUser(busUser);
            if (!flag) {
                return GenResult.FAILED.genResult();
            }

            return GenResult.SUCCESS.genResult(busUser);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "resetPassword")
    @ResponseBody
    public Map<String, Object> resetPassword(HttpSession session, String id) {
        try {
            Admin admin = (Admin) session.getAttribute("admin");

            boolean flag = userService.resetPassword(admin, id);
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
