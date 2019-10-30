package net.ys.dao;

import net.ys.bean.BusMonitor;
import net.ys.bean.BusMonitorContact;
import net.ys.mapper.BusMonitorContactMapper;
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

    public List<BusMonitorContact> queryMonitorContacts(String mId, int page, int pageSize) {
        String sql = "SELECT ID, M_ID, NAME, TEL_NO, EMAIL, CREATE_TIME FROM BUS_MONITOR_CONTACT WHERE M_ID = ? LIMIT ?,?";
        return jdbcTemplate.query(sql, new BusMonitorContactMapper(), mId, (page - 1) * pageSize, pageSize);
    }

    public void updateCheckStatus(BusMonitor monitor, boolean flag) {
        String sql = "UPDATE BUS_MONITOR SET STATUS = ?, UP_AMOUNT = ?, DOWN_AMOUNT = ? WHERE ID = ?";
        jdbcTemplate.update(sql, flag ? 1 : 2, monitor.getUpAmount(), monitor.getDownAmount(), monitor.getId());
    }

    public List<Map<String, Object>> queryTransAmount(String platformCode) {
        String sql = "SELECT TRANS_TYPE, SUM(AMOUNT) AS AMOUNT FROM BUS_DATA_TRANS_LOG WHERE PLATFORM_CODE = ? AND CREATE_TIME BETWEEN ? AND ? GROUP BY TRANS_TYPE";
        return jdbcTemplate.queryForList(sql, platformCode, TimeUtil.todayStartMillisecond(), TimeUtil.todayEndMillisecond());
    }
}
