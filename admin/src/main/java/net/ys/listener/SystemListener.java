package net.ys.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import net.ys.bean.BusMonitor;
import net.ys.component.AppContextUtil;
import net.ys.service.EtlService;
import net.ys.service.MonitorService;
import net.ys.threadpool.ThreadPoolManager;
import net.ys.utils.KettleUtil;
import net.ys.utils.LogUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * User: NMY
 * Date: 16-8-29
 */
@WebListener
public class SystemListener implements ServletContextListener {

    MonitorService monitorService;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadPoolManager.INSTANCE.destroy();
        if (monitorService != null) {
            monitorService.closeAllTimer();
        }
        try {//释放驱动并关闭线程
            Enumeration<Driver> enumeration = DriverManager.getDrivers();
            while (enumeration.hasMoreElements()) {
                DriverManager.deregisterDriver(enumeration.nextElement());
            }
            AbandonedConnectionCleanupThread.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--- contextDestroyed ---");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /**
         * 检查所有监控地址状况
         */
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    monitorService = AppContextUtil.getBean("monitorService", MonitorService.class);

                    List<BusMonitor> monitors = monitorService.queryMonitors(1, Integer.MAX_VALUE);

                    for (BusMonitor monitor : monitors) {
                        monitorService.scheduleCheck(monitor);
                    }
                } catch (Exception e) {
                    LogUtil.error(e);
                }
            }
        };
        ThreadPoolManager.INSTANCE.complexPool.doIt(r);

        /**
         * 启动所有已开启的etl进行传输
         */
        final String kjbPath = sce.getServletContext().getRealPath("/kjb") + "/";
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    EtlService etlService = AppContextUtil.getBean("etlService", EtlService.class);
                    List<Map<String, Object>> etlEntities = etlService.getStartedEntities();
                    if (etlEntities.size() > 0) {
                        for (Map<String, Object> etlEntity : etlEntities) {
                            String entityId = String.valueOf(etlEntity.get("ID"));
                            String etlId = String.valueOf(etlEntity.get("ETL_ID"));
                            KettleUtil.startJob(kjbPath + etlId + ".kjb", entityId);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.error(e);
                }
            }
        };
        ThreadPoolManager.INSTANCE.complexPool.doIt(r);

        System.out.println("--- contextInitialized ---");
    }
}