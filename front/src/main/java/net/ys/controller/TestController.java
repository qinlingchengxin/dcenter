package net.ys.controller;

import net.ys.constant.GenResult;
import net.ys.schedule.BuService;
import net.ys.schedule.FileService;
import net.ys.schedule.FrontDataService;
import net.ys.schedule.ScheduleService;
import net.ys.utils.LogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value = "test")
public class TestController {

    @Resource
    private BuService buService;

    @Resource
    private FileService fileService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private FrontDataService frontDataService;

    @RequestMapping(value = "syncTable")
    @ResponseBody
    public Map<String, Object> syncTable() {
        try {
            long start = System.currentTimeMillis();
            buService.syncTable();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "sliceSyncData")
    @ResponseBody
    public Map<String, Object> sliceSyncData() {
        try {
            long start = System.currentTimeMillis();
            buService.sliceSyncData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "syncDownWaitData")
    @ResponseBody
    public Map<String, Object> syncDownWaitData() {
        try {
            long start = System.currentTimeMillis();
            scheduleService.syncDownWaitData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "syncDownFailedData")
    @ResponseBody
    public Map<String, Object> syncDownFailedData() {
        try {
            long start = System.currentTimeMillis();
            scheduleService.syncDownFailedData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "packData")
    @ResponseBody
    public Map<String, Object> packData() {
        try {
            long start = System.currentTimeMillis();
            scheduleService.packData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "syncUpWaitData")
    @ResponseBody
    public Map<String, Object> syncUpWaitData() {
        try {
            long start = System.currentTimeMillis();
            scheduleService.syncUpWaitData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "syncUpFailedData")
    @ResponseBody
    public Map<String, Object> syncUpFailedData() {
        try {
            long start = System.currentTimeMillis();
            scheduleService.syncUpFailedData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "execute")
    @ResponseBody
    public Map<String, Object> execute() {
        try {
            long start = System.currentTimeMillis();
            fileService.execute();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "upFile")
    @ResponseBody
    public Map<String, Object> upFile() {
        try {
            long start = System.currentTimeMillis();
            fileService.upFile();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }

    @RequestMapping(value = "upFileData")
    @ResponseBody
    public Map<String, Object> upFileData() {
        try {
            long start = System.currentTimeMillis();
            frontDataService.upFileData();
            return GenResult.SUCCESS.genResult("use time:" + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            LogUtil.error(e);
            return GenResult.UNKNOWN_ERROR.genResult();
        }
    }
}
