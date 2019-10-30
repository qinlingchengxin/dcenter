package net.ys.service;

import net.ys.bean.BusMonitor;
import net.ys.bean.BusMonitorContact;
import net.ys.constant.SysRegex;
import net.ys.constant.X;
import net.ys.dao.MonitorDao;
import net.ys.threadpool.ThreadPoolManager;
import net.ys.utils.BatUtil;
import net.ys.utils.EmailUtil;
import net.ys.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    private Map<String, Timer> taskMap = new ConcurrentHashMap<String, Timer>();

    @Resource
    private MonitorDao monitorDao;

    public long queryMonitorCount() {
        return monitorDao.queryMonitorCount();
    }

    public List<BusMonitor> queryMonitors(int page, int pageSize) {
        return monitorDao.queryMonitors(page, pageSize);
    }

    public BusMonitor queryMonitor(String code) {
        return monitorDao.queryMonitor(code);
    }

    public boolean updateMonitor(final BusMonitor monitor) {
        boolean flag = monitorDao.updateMonitor(monitor);

        if (flag) {
            String id = monitor.getId();
            Timer t = taskMap.get(id);
            if (t != null) {
                t.cancel();
                taskMap.remove(id);
            }

            scheduleCheck(monitor);
        }
        return flag;
    }

    public void scheduleCheck(final BusMonitor monitor) {

        long interval = monitor.getIntervalDay() * X.TIME.DAY_MILLISECOND + monitor.getIntervalHour() * X.TIME.HOUR_MILLISECOND + monitor.getIntervalMinute() * X.TIME.MINUTE_MILLISECOND;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                check(monitor);
            }
        }, 1000, interval);

        taskMap.put(monitor.getId(), timer);
    }

    private void check(final BusMonitor monitor) {

        int type = monitor.getType();
        boolean flag;
        if (type == 0) {
            flag = BatUtil.ping(monitor.getIp());
        } else {
            flag = BatUtil.telnet(monitor.getIp(), monitor.getPort());
        }

        List<Map<String, Object>> data = monitorDao.queryTransAmount(monitor.getPlatformCode());

        if (data.size() > 0) {
            for (Map<String, Object> map : data) {
                if ("0".equals(String.valueOf(map.get("TRANS_TYPE")))) {
                    monitor.setUpAmount(Integer.parseInt(String.valueOf(map.get("AMOUNT"))));
                } else if ("1".equals(String.valueOf(map.get("TRANS_TYPE")))) {
                    monitor.setDownAmount(Integer.parseInt(String.valueOf(map.get("AMOUNT"))));
                }
            }
        }

        if (!flag) {//通知
            final List<BusMonitorContact> contacts = monitorDao.queryMonitorContacts(monitor.getId(), 1, Integer.MAX_VALUE);

            if (contacts.size() > 0) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String email;
                            EmailUtil emailUtil = new EmailUtil(X.EMAIL.HOST, X.EMAIL.USERNAME, X.EMAIL.PASSWORD);
                            for (BusMonitorContact contact : contacts) {
                                email = contact.getEmail();
                                if (StringUtils.isNotBlank(email) && email.matches(SysRegex.EMAIL.regex)) {
                                    emailUtil.sendCommonEmail(X.EMAIL.SUBJECT, String.format(X.EMAIL.CONTENT, contact.getName(), monitor.getPlatName()), contact.getEmail());
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                };
                ThreadPoolManager.INSTANCE.complexPool.doIt(r);
            }
        }

        monitorDao.updateCheckStatus(monitor, flag);
    }

    public BusMonitor addMonitor(BusMonitor monitor) throws Exception {

        monitor.setId(UUID.randomUUID().toString());
        monitor.setCreateTime(System.currentTimeMillis());
        boolean flag = monitorDao.addMonitor(monitor);
        if (flag) {
            scheduleCheck(monitor);
            return monitor;
        }
        return null;
    }

    public long queryMonitorContactCount(String mId) {
        return monitorDao.queryMonitorContactCount(mId);
    }

    public List<BusMonitorContact> queryMonitorContacts(String mId, int page, int pageSize) {
        return monitorDao.queryMonitorContacts(mId, page, pageSize);
    }

    public BusMonitorContact queryMonitorContact(String id) {
        return monitorDao.queryMonitorContact(id);
    }

    public boolean updateMonitorContact(BusMonitorContact contact) {
        return monitorDao.updateMonitorContact(contact);
    }

    public BusMonitorContact addMonitorContact(BusMonitorContact contact) throws Exception {

        contact.setId(UUID.randomUUID().toString());
        contact.setCreateTime(System.currentTimeMillis());
        boolean flag = monitorDao.addMonitorContact(contact);
        if (flag) {
            return contact;
        }
        return null;
    }

    public boolean removeMonitor(String id) {
        boolean flag = monitorDao.removeMonitor(id);
        if (flag) {
            Timer t = taskMap.get(id);
            if (t != null) {
                t.cancel();
                taskMap.remove(id);
            }
        }
        return flag;
    }

    public boolean removeContact(String id) {
        return monitorDao.removeContact(id);
    }

    public void closeAllTimer() {
        LogUtil.debug("closeAllTimer,size：" + taskMap.size());
        if (taskMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Timer> entry : taskMap.entrySet()) {
            Timer timer = entry.getValue();
            timer.cancel();
            timer.purge();
        }
    }
}
