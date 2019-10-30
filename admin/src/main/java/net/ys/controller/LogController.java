package net.ys.controller;

import net.ys.bean.BusDataTransLog;
import net.ys.bean.BusTfChangeLog;
import net.ys.bean.EpChangeLog;
import net.ys.service.EntityService;
import net.ys.service.LogService;
import net.ys.service.PlatService;
import net.ys.utils.TimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "web/log")
public class LogController {

    @Resource
    private LogService logService;

    @Resource
    private EntityService entityService;

    @Resource
    private PlatService platService;

    /**
     * 数据传输日志
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "dataTrans")
    public ModelAndView dataTrans(@RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "-1") int status, @RequestParam(defaultValue = "") String content, @RequestParam(defaultValue = "") String tableName,
                                  @RequestParam(defaultValue = "") String platformCode, @RequestParam(defaultValue = "") String startTime, @RequestParam(defaultValue = "") String endTime, @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("dtl/list");
        if (page < 1) {
            page = 1;
        }

        long start = TimeUtil.toLongStart(startTime);
        long end = TimeUtil.toLongEnd(endTime);
        if (end == 0) {
            end = TimeUtil.doomsdayMillisecond();
        }

        long count = logService.queryDataTransLogCount(tableName, platformCode, type, status, content, start, end);

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

        List<Map<String, Object>> entities = entityService.queryEntities();
        List<Map<String, Object>> platforms = platService.queryPlatformList();

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("type", type);
        modelAndView.addObject("status", status);
        modelAndView.addObject("dataTransLogs", dataTransLogs);
        modelAndView.addObject("entities", entities);
        modelAndView.addObject("platforms", platforms);
        modelAndView.addObject("tableName", tableName);
        modelAndView.addObject("platformCode", platformCode);
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
    @RequestMapping(value = "dataDetail")
    public ModelAndView dataDetail(@RequestParam(defaultValue = "") String logId) {
        ModelAndView modelAndView = new ModelAndView("dtl/dataDetail");
        BusDataTransLog transLog;
        if ("".equals(logId)) {//新增
            transLog = new BusDataTransLog();
        } else {
            transLog = logService.queryDataTransLog(logId);
        }

        modelAndView.addObject("content", transLog.getContent());
        return modelAndView;
    }

    /**
     * 表、字段变更日志
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "tfChangeLogs")
    public ModelAndView tfChangeLogs(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("tcl/list");
        if (page < 1) {
            page = 1;
        }
        long count = logService.queryTfChangeLogCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<BusTfChangeLog> tfChangeLogs;
        if ((page - 1) * pageSize < count) {
            tfChangeLogs = logService.queryTfChangeLogLogs(page, pageSize);
        } else {
            tfChangeLogs = new ArrayList<BusTfChangeLog>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("tfChangeLogs", tfChangeLogs);
        return modelAndView;
    }

    /**
     * 权限变更日志
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "epChangeLogs")
    public ModelAndView epChangeLogs(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("ecl/list");
        if (page < 1) {
            page = 1;
        }
        long count = logService.queryEpChangeLogCount();

        long t = count / pageSize;
        int k = count % pageSize == 0 ? 0 : 1;
        int totalPage = (int) (t + k);

        if (page > totalPage && count > 0) {
            page = totalPage;
        }

        List<EpChangeLog> epChangeLogs;
        if ((page - 1) * pageSize < count) {
            epChangeLogs = logService.queryEpChangeLogs(page, pageSize);
        } else {
            epChangeLogs = new ArrayList<EpChangeLog>();
        }

        modelAndView.addObject("count", count);
        modelAndView.addObject("currPage", page);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("epChangeLogs", epChangeLogs);
        return modelAndView;
    }
}
