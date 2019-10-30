package net.ys.service;

import net.ys.bean.DbField;
import net.ys.bean.DbTable;
import net.ys.bean.EtlAllTable;
import net.ys.bean.EtlDataSource;
import net.ys.dao.DbDao;
import net.ys.utils.DBUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: LiWenC
 * Date: 18-4-24
 */

@Service
public class DbService {

    @Resource
    private DbDao dbDao;

    /**
     * 扫描数据源，获取表信息
     */
    public void genTable() {
        //1、读表获取数据源
        List<EtlDataSource> dataSources = dbDao.queryDataSources();

        //2、获取表信息，入库
        List<DbTable> tables = null;
        for (EtlDataSource dataSource : dataSources) {
            if ("MySql".equals(dataSource.getDbType())) {
                tables = DBUtil.getTablesMySql(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), dataSource.getDbUsername(), dataSource.getDbPwd());
            } else if ("Oracle".equals(dataSource.getDbType())) {
                tables = DBUtil.getTablesOracle(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), dataSource.getDbUsername(), dataSource.getDbPwd());
            } else if ("MSSQL".equals(dataSource.getDbType())) {
                tables = DBUtil.getTablesMSSQL(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), dataSource.getDbUsername(), dataSource.getDbPwd());
            }

            if (tables != null && tables.size() > 0) {
                dbDao.addTables(dataSource.getId(), tables);
            }
        }
    }

    /**
     * 扫描表，获取字段信息
     */
    public void genField() {

        List<EtlDataSource> dataSources = dbDao.queryDataSources();
        List<EtlAllTable> tables;
        List<DbField> fields = null;

        for (EtlDataSource dataSource : dataSources) {
            tables = dbDao.queryEtlAllTables(dataSource);
            for (EtlAllTable table : tables) {
                if ("MySql".equals(dataSource.getDbType())) {
                    fields = DBUtil.getFieldsMySql(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), table, dataSource.getDbUsername(), dataSource.getDbPwd());
                } else if ("Oracle".equals(dataSource.getDbType())) {
                    fields = DBUtil.getFieldsOracle(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), table, dataSource.getDbUsername(), dataSource.getDbPwd());
                } else if ("MSSQL".equals(dataSource.getDbType())) {
                    fields = DBUtil.getFieldsMSSQL(dataSource.getDbIp(), dataSource.getDbPort(), dataSource.getDbName(), table, dataSource.getDbUsername(), dataSource.getDbPwd());
                }

                if (fields != null && fields.size() > 0) {
                    dbDao.addFields(fields);
                }
            }
        }
    }
}