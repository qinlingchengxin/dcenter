package net.ys.service;

import net.ys.bean.BusMonitor;
import net.ys.bean.BusMonitorContact;
import net.ys.constant.SysRegex;
import net.ys.constant.X;
import net.ys.dao.MonitorDao;
import net.ys.threadpool.ThreadPoolManager;
import net.ys.utils.BatUtil;
import net.ys.utils.EmailUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    private Map<String, Timer> taskMap = new ConcurrentHashMap<String, Timer>();

    @Resource
    private MonitorDao monitorDao;

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
}
