package net.ys.controller;

import net.ys.bean.BusMonitor;
import net.ys.bean.BusMonitorContact;
import net.ys.constant.GenResult;
import net.ys.constant.SysRegex;
import net.ys.service.MonitorService;
import net.ys.service.PlatService;
import net.ys.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "web/monitor")
public class MonitorController {

    @Resource
    private MonitorService monitorService;

    @Resource
    private PlatService platService;

    @RequestMapping(value = "list")
    public ModelAndView list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("monitor/list");
        if (page < 1) {
            page = 1;
        }
        long count = monitorService.queryMonitorCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusMonitor> monitors;
        if ((page - 1) * pageSize < count) {
            monitors = monitorService.queryMonitors(page, pageSize);
        } else {
            monitors = new ArrayList<BusMonitor>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("monitors", monitors);
        return modelAndView;
    }

    @RequestMapping(value = "edit")
    public ModelAndView edit(@RequestParam(defaultValue = "") String id) {
        ModelAndView modelAndView = new ModelAndView("monitor/edit");
        BusMonitor busMonitor;
        if ("".equals(id)) {
            busMonitor = new BusMonitor();
        } else {
            busMonitor = monitorService.queryMonitor(id);
        }

        List<Map<String, Object>> platforms = platService.queryPlatformList();

        modelAndView.addObject("busMonitor", busMonitor);
        modelAndView.addObject("platforms", platforms);
        return modelAndView;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(BusMonitor monitor) {

        String ip = monitor.getIp();

        if (StringUtils.isNotBlank(ip)) {
            if (!ip.matches(SysRegex.IP.regex)) {
                return GenResult.PARAMS_ERROR.genResult();
            }
        }

        if (monitor.getType() == 1) {//telnet
            int port = monitor.getPort();
            if (port < 1 || port > 65536) {
                return GenResult.PARAMS_ERROR.genResult();
            }
        }

        int intervalDay = monitor.getIntervalDay();
        int intervalHour = monitor.getIntervalHour();
        int intervalMinute = monitor.getIntervalMinute();

        if (intervalDay == 0 && intervalHour == 0 && intervalMinute == 0) {
            return GenResult.PARAMS_ERROR.genResult();
        }

        boolean flag = monitorService.updateMonitor(monitor);
        if (flag) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @RequestMapping(value = "remove")
    @ResponseBody
    public Map<String, Object> remove(String id) {

        boolean flag = monitorService.removeMonitor(id);
        if (flag) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public Map<String, Object> add(BusMonitor monitor) {
        try {

            String ip = monitor.getIp();

            if (StringUtils.isNotBlank(ip)) {
                if (!ip.matches(SysRegex.IP.regex)) {
                    return GenResult.PARAMS_ERROR.genResult();
                }
            }

            if (monitor.getType() == 1) {//telnet
                int port = monitor.getPort();
                if (port < 1 || port > 65536) {
                    return GenResult.PARAMS_ERROR.genResult();
                }
            }

            int intervalDay = monitor.getIntervalDay();
            int intervalHour = monitor.getIntervalHour();
            int intervalMinute = monitor.getIntervalMinute();

            if (intervalDay == 0 && intervalHour == 0 && intervalMinute == 0) {
                return GenResult.PARAMS_ERROR.genResult();
            }

            monitor = monitorService.addMonitor(monitor);
            if (monitor == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(monitor);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "contact/list")
    public ModelAndView contactList(@RequestParam(defaultValue = "") String mId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("monitor_contact/list");
        if (page < 1) {
            page = 1;
        }
        long count = monitorService.queryMonitorContactCount(mId);

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusMonitorContact> contacts;
        if ((page - 1) * pageSize < count) {
            contacts = monitorService.queryMonitorContacts(mId, page, pageSize);
        } else {
            contacts = new ArrayList<BusMonitorContact>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("contacts", contacts);
        modelAndView.addObject("mId", mId);
        return modelAndView;
    }

    @RequestMapping(value = "contact/edit")
    public ModelAndView contactEdit(@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String mId) {
        ModelAndView modelAndView = new ModelAndView("monitor_contact/edit");
        BusMonitorContact contact;
        if ("".equals(id)) {
            contact = new BusMonitorContact();
            contact.setMonitorId(mId);
        } else {
            contact = monitorService.queryMonitorContact(id);
        }

        modelAndView.addObject("contact", contact);
        return modelAndView;
    }

    @RequestMapping(value = "contact/save")
    @ResponseBody
    public Map<String, Object> contactSave(BusMonitorContact contact) {

        String telNo = contact.getTelNo();

        if (StringUtils.isNotBlank(telNo)) {
            if (!telNo.matches(SysRegex.PHONE_NUMBER.regex)) {
                return GenResult.PARAMS_ERROR.genResult();
            }
        }

        String email = contact.getEmail();

        if (StringUtils.isNotBlank(email)) {
            if (!email.matches(SysRegex.EMAIL.regex)) {
                return GenResult.PARAMS_ERROR.genResult();
            }
        }

        boolean flag = monitorService.updateMonitorContact(contact);
        if (flag) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @RequestMapping(value = "contact/add")
    @ResponseBody
    public Map<String, Object> contactAdd(BusMonitorContact contact) {
        try {
            contact = monitorService.addMonitorContact(contact);
            if (contact == null) {
                return GenResult.FAILED.genResult();
            }
            return GenResult.SUCCESS.genResult(contact);
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "contact/remove")
    @ResponseBody
    public Map<String, Object> contactRemove(String id) {

        boolean flag = monitorService.removeContact(id);
        if (flag) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }
}
