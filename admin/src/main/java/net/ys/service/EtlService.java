package net.ys.service;

import net.ys.bean.*;
import net.ys.dao.EtlDao;
import net.ys.utils.DBUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EtlService {

    @Resource
    private EtlDao etlDao;

    public long queryEtlEntityCount(String prjId) {
        return etlDao.queryEtlEntityCount(prjId);
    }

    public List<EtlEntity> queryEtlEntities(String prjId, int page, int pageSize) {
        return etlDao.queryEtlEntities(prjId, page, pageSize);
    }

    public EtlEntity queryEtlEntity(String id) {
        return etlDao.queryEtlEntity(id);
    }

    public boolean updateEtlEntity(EtlEntity etlEntity) {
        return etlDao.updateEtlEntity(etlEntity);
    }

    public EtlEntity addEtlEntity(EtlEntity etlEntity) {
        return etlDao.addEtlEntity(etlEntity);
    }

    public long queryEtlFieldCount(String entityId) {
        return etlDao.queryEtlFieldCount(entityId);
    }

    public List<EtlField> queryEtlFields(String entityId, int page, int pageSize) {
        return etlDao.queryEtlFields(entityId, page, pageSize);
    }

    /**
     * 生成映射文件的字段
     *
     * @param entityId
     * @return
     */
    public List<EtlField> queryEtlKtrFields(String entityId) {
        return etlDao.queryEtlKtrFields(entityId);
    }

    public EtlField addEtlField(EtlField etlField) {
        return etlDao.addEtlField(etlField);
    }

    public EtlProject queryEtlKtrProject(String entityId) {
        return etlDao.queryEtlKtrProject(entityId);
    }

    public long queryEtlProjectCount() {
        return etlDao.queryEtlProjectCount();
    }

    public List<EtlProject> queryEtlProjects(int page, int pageSize) {
        return etlDao.queryEtlProjects(page, pageSize);
    }

    public EtlProject queryEtlProject(String id) {
        return etlDao.queryEtlProject(id);
    }

    public boolean updateEtlProject(EtlProject etlProject) {
        return etlDao.updateEtlProject(etlProject);
    }

    public EtlProject addEtlProject(EtlProject etlProject) {
        return etlDao.addEtlProject(etlProject);
    }

    public List<EtlAllField> queryFields(EtlAllTable table) {
        return etlDao.queryFields(table);
    }

    public long queryEtlDataSourceCount() {
        return etlDao.queryEtlDataSourceCount();
    }

    public List<EtlDataSource> queryEtlDataSources(int page, int pageSize) {
        return etlDao.queryEtlDataSources(page, pageSize);
    }

    public EtlDataSource queryEtlDataSource(String id) {
        return etlDao.queryEtlDataSource(id);
    }

    public boolean updateEtlDataSource(EtlDataSource etlDataSource) {
        return etlDao.updateEtlDataSource(etlDataSource);
    }

    public EtlDataSource addEtlDataSource(EtlDataSource etlDataSource) {
        return etlDao.addEtlDataSource(etlDataSource);
    }

    public List<EtlAllTable> querySrcTables(EtlProject project) {
        return etlDao.queryEtlAllTables(project.getSrcDbId());
    }

    public List<EtlAllTable> queryDesTables(EtlProject project) {
        return etlDao.queryEtlAllTables(project.getDesDbId());
    }

    public List<EtlAllField> queryPrimaryKey(String tableId) {
        return etlDao.queryPrimaryKey(tableId);
    }

    public boolean testConnDs(EtlDataSource etlDataSource) {
        String dbType = etlDataSource.getDbType();
        boolean flag = false;
        if ("MySql".equals(dbType)) {
            flag = DBUtil.testConnMySql(etlDataSource);
        } else if ("Oracle".equals(dbType)) {
            flag = DBUtil.testConnOracle(etlDataSource);
        } else if ("MSSQL".equals(dbType)) {
            flag = DBUtil.testConnMSSQL(etlDataSource);
        }
        return flag;
    }

    public boolean entityDel(String id) {
        return etlDao.entityDel(id);
    }

    public boolean projectDel(String id) {
        return etlDao.projectDel(id);
    }

    public boolean fieldDel(String id) {
        return etlDao.fieldDel(id);
    }

    public boolean chgJobStatus(String entityId, int status) {
        return etlDao.chgJobStatus(entityId, status);
    }

    public EtlAllTable querySrcEtlAllTable(String prjId, String tabName) {
        return etlDao.querySrcEtlAllTable(prjId, tabName);
    }

    public EtlAllTable queryDesEtlAllTable(String prjId, String tabName) {
        return etlDao.queryDesEtlAllTable(prjId, tabName);
    }

    /**
     * 启动所有已开启的etl进行传输
     */
    public List<Map<String, Object>> getStartedEntities() {
        return etlDao.queryEtlStartedEntities();
    }
}
