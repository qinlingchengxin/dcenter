package net.ys.service;

import net.ys.bean.BusDataTransLog;
import net.ys.bean.BusTfChangeLog;
import net.ys.bean.EpChangeLog;
import net.ys.dao.LogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogService {

    @Resource
    private LogDao logDao;

    public long queryDataTransLogCount(String tableName, String platformCode, int type, int status, String content, long start, long end) {
        return logDao.queryDataTransLogCount(tableName, platformCode, type, status, content, start, end);
    }

    public List<BusDataTransLog> queryDataTransLogs(String content, String tableName, String platformCode, int type, int status, int page, int pageSize, long start, long end) {
        return logDao.queryDataTransLogs(content, tableName, platformCode, type, status, page, pageSize, start, end);
    }

    public long queryTfChangeLogCount() {
        return logDao.queryTfChangeLogCount();
    }

    public List<BusTfChangeLog> queryTfChangeLogLogs(int page, int pageSize) {
        return logDao.queryTfChangeLogLogs(page, pageSize);
    }

    public long queryEpChangeLogCount() {
        return logDao.queryEpChangeLogCount();
    }

    public List<EpChangeLog> queryEpChangeLogs(int page, int pageSize) {
        return logDao.queryEpChangeLogs(page, pageSize);
    }

    /**
     * 传输日志
     *
     * @param platformCode
     * @param tableName
     * @param result        传输结果
     * @param transType     传输类型
     * @param content       传输内容
     * @param failedContent 校验失败内容
     * @param remarks       备注
     * @param amount        成功条数
     */
    public void addTransLog(String platformCode, String tableName, int result, int transType, String content, String failedContent, String remarks, int amount) {
        logDao.addTransLog(platformCode, tableName, result, transType, content, failedContent, remarks, amount);
    }

    public BusDataTransLog queryDataTransLog(String id) {
        return logDao.queryDataTransLog(id);
    }
}
