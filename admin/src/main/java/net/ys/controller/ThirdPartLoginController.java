package net.ys.controller;

import net.ys.bean.Admin;
import net.ys.service.AdminService;
import net.ys.utils.LogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 第三方登录
 */
@Controller
@RequestMapping
public class ThirdPartLoginController {

    @Resource
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession session, String username, String password) {
        try {
            Admin admin = adminService.queryAdmin(username, password);
            if (admin != null) {
                session.setAttribute("admin", admin);
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }
        return "redirect:/web/admin/main.do";
    }
}
