package net.ys.dao;

import net.ys.bean.BusMonitor;
import net.ys.bean.BusMonitorContact;
import net.ys.mapper.BusMonitorContactMapper;
import net.ys.mapper.BusMonitorMapper;
import net.ys.utils.TimeUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class MonitorDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<BusMonitor> queryMonitors(int page, int pageSize) {
        String sql = "SELECT bm.ID, bm.PLATFORM_CODE, bp.PLAT_NAME, bm.TYPE, bm.IP, bm. PORT, bm.INTERVAL_DAY, bm.INTERVAL_HOUR, bm.INTERVAL_MINUTE, bm.UP_AMOUNT, bm.DOWN_AMOUNT, bm.STATUS, bm.NOTICE, bm.CREATE_TIME FROM BUS_MONITOR bm LEFT JOIN BUS_PLATFORM bp ON bp.CODE = bm.PLATFORM_CODE LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusMonitorMapper(), (page - 1) * pageSize, pageSize);
    }

    public boolean updateMonitor(BusMonitor monitor) {
        String sql = "UPDATE BUS_MONITOR SET PLATFORM_CODE = ?, TYPE = ?, IP = ?, PORT = ?, INTERVAL_DAY = ?, INTERVAL_HOUR = ?, INTERVAL_MINUTE = ?, NOTICE = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, monitor.getPlatformCode(), monitor.getType(), monitor.getIp(), monitor.getPort(), monitor.getIntervalDay(), monitor.getIntervalHour(), monitor.getIntervalMinute(), monitor.getNotice(), monitor.getId()) >= 0;
    }

    public boolean addMonitor(BusMonitor monitor) {
        String sql = "INSERT INTO BUS_MONITOR ( ID, PLATFORM_CODE, TYPE, IP, PORT, INTERVAL_DAY, INTERVAL_HOUR, INTERVAL_MINUTE, NOTICE, CREATE_TIME) VALUES ( ?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, monitor.getId(), monitor.getPlatformCode(), monitor.getType(), monitor.getIp(), monitor.getPort(), monitor.getIntervalDay(), monitor.getIntervalHour(), monitor.getIntervalMinute(), monitor.getNotice(), monitor.getCreateTime()) > 0;
    }

    public long queryMonitorCount() {
        String sql = "SELECT COUNT(*) FROM BUS_MONITOR";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public long queryMonitorContactCount(String mId) {
        String sql = "SELECT COUNT(*) FROM BUS_MONITOR_CONTACT WHERE M_ID = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, mId);
    }

    public BusMonitor queryMonitor(String id) {
        String sql = "SELECT bm.ID, bm.PLATFORM_CODE, bp.PLAT_NAME, bm.TYPE, bm.IP, bm. PORT, bm.INTERVAL_DAY, bm.INTERVAL_HOUR, bm.INTERVAL_MINUTE, bm.NOTICE, bm.UP_AMOUNT, bm.DOWN_AMOUNT, bm.STATUS, bm.CREATE_TIME FROM BUS_MONITOR bm LEFT JOIN BUS_PLATFORM bp ON bp.CODE = bm.PLATFORM_CODE WHERE bm.ID = ?";
        List<BusMonitor> list = jdbcTemplate.query(sql, new BusMonitorMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<BusMonitorContact> queryMonitorContacts(String mId, int page, int pageSize) {
        String sql = "SELECT ID, M_ID, NAME, TEL_NO, EMAIL, CREATE_TIME FROM BUS_MONITOR_CONTACT WHERE M_ID = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusMonitorContactMapper(), mId, (page - 1) * pageSize, pageSize);
    }

    public BusMonitorContact queryMonitorContact(String id) {
        String sql = "SELECT ID, M_ID, NAME, TEL_NO, EMAIL, CREATE_TIME FROM BUS_MONITOR_CONTACT WHERE ID = ?";
        List<BusMonitorContact> list = jdbcTemplate.query(sql, new BusMonitorContactMapper(), id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean updateMonitorContact(BusMonitorContact contact) {
        String sql = "UPDATE BUS_MONITOR_CONTACT SET NAME = ?, TEL_NO = ?, EMAIL = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, contact.getName(), contact.getTelNo(), contact.getEmail(), contact.getId()) >= 0;
    }

    public boolean addMonitorContact(BusMonitorContact contact) {
        String sql = "INSERT INTO BUS_MONITOR_CONTACT ( ID, M_ID, NAME, TEL_NO, EMAIL, CREATE_TIME) VALUES ( ?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, contact.getId(), contact.getMonitorId(), contact.getName(), contact.getTelNo(), contact.getEmail(), contact.getCreateTime()) > 0;
    }

    public void updateCheckStatus(BusMonitor monitor, boolean flag) {
        String sql = "UPDATE BUS_MONITOR SET STATUS = ?, UP_AMOUNT = ?, DOWN_AMOUNT = ? WHERE ID = ?";
        jdbcTemplate.update(sql, flag ? 1 : 2, monitor.getUpAmount(), monitor.getDownAmount(), monitor.getId());
    }

    public boolean removeMonitor(String id) {
        String sql = "DELETE FROM BUS_MONITOR WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public boolean removeContact(String id) {
        String sql = "DELETE FROM BUS_MONITOR_CONTACT WHERE ID = ?";
        return jdbcTemplate.update(sql, id) >= 0;
    }

    public List<Map<String, Object>> queryTransAmount(String platformCode) {
        String sql = "SELECT TRANS_TYPE, SUM(AMOUNT) AS AMOUNT FROM BUS_DATA_TRANS_LOG WHERE PLATFORM_CODE = ? AND CREATE_TIME BETWEEN ? AND ? GROUP BY TRANS_TYPE";
        return jdbcTemplate.queryForList(sql, platformCode, TimeUtil.todayStartMillisecond(), TimeUtil.todayEndMillisecond());
    }
}
