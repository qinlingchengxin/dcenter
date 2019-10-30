package net.ys.utils;

import net.ys.bean.DbField;
import net.ys.bean.DbTable;
import net.ys.bean.EtlAllTable;
import net.ys.bean.EtlDataSource;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LiWenC
 * Date: 18-3-20
 */
public class DBUtil {

    public static List<DbTable> getTablesMySql(String ip, int port, String dbName, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = ?");
            statement.setString(1, dbName);
            rs = statement.executeQuery();
            List<DbTable> tables = new ArrayList<DbTable>();
            String tableName;
            String tableComment;
            while (rs.next()) {
                tableName = rs.getString("TABLE_NAME");
                tableComment = rs.getString("TABLE_COMMENT");
                if (StringUtils.isBlank(tableComment)) {
                    tableComment = tableName;
                }
                tables.add(new DbTable(tableName, tableComment));
            }
            rs.close();
            statement.close();
            connection.close();
            return tables;
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static List<DbTable> getTablesOracle(String ip, int port, String dbName, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@" + ip + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT TABLE_NAME, COMMENTS FROM user_tab_comments");
            rs = statement.executeQuery();
            List<DbTable> tables = new ArrayList<DbTable>();
            String tableName;
            String tableComment;
            while (rs.next()) {
                tableName = rs.getString("TABLE_NAME");
                tableComment = rs.getString("COMMENTS");
                if (StringUtils.isBlank(tableComment)) {
                    tableComment = tableName;
                }
                tables.add(new DbTable(tableName, tableComment));
            }
            rs.close();
            statement.close();
            connection.close();
            return tables;
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static List<DbTable> getTablesMSSQL(String ip, int port, String dbName, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT so.[ NAME ] AS TABLE_NAME, ds.[ VALUE ] AS COMMENTS FROM sysobjects so LEFT JOIN sys.extended_properties ds ON ds.major_id = so.id WHERE so.xtype = 'U'");
            rs = statement.executeQuery();
            List<DbTable> tables = new ArrayList<DbTable>();
            String tableName;
            String tableComment;
            while (rs.next()) {
                tableName = rs.getString("TABLE_NAME");
                tableComment = rs.getString("COMMENTS");
                if (StringUtils.isBlank(tableComment)) {
                    tableComment = tableName;
                }
                tables.add(new DbTable(tableName, tableComment));
            }
            rs.close();
            statement.close();
            connection.close();
            return tables;
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static List<DbField> getFieldsMySql(String ip, int port, String dbName, EtlAllTable table, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT COLUMN_NAME, COLUMN_COMMENT, IF (COLUMN_KEY = 'PRI', 1, 0) AS PRI_KEY FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?");
            statement.setString(1, dbName);
            statement.setString(2, table.getName());
            rs = statement.executeQuery();
            List<DbField> fields = new ArrayList<DbField>();
            String columnName;
            String columnComment;
            int priKey;
            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
                columnComment = rs.getString("COLUMN_COMMENT");
                priKey = rs.getInt("PRI_KEY");
                if (StringUtils.isBlank(columnComment)) {
                    columnComment = columnName;
                }
                fields.add(new DbField(table.getId(), columnName, columnComment, priKey));
            }
            rs.close();
            statement.close();
            connection.close();
            return fields;
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static List<DbField> getFieldsOracle(String ip, int port, String dbName, EtlAllTable table, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@" + ip + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT cc.COLUMN_NAME, cc.COMMENTS, CASE WHEN t1.COLUMN_NAME IS NULL THEN 0 ELSE 1 END AS PRI_KEY FROM user_col_comments cc LEFT JOIN ( SELECT cu.COLUMN_NAME, au.TABLE_NAME FROM user_cons_columns cu, user_constraints au WHERE cu.constraint_name = au.constraint_name AND au.constraint_type = 'P' ) t1 ON t1.TABLE_NAME = cc.TABLE_NAME AND t1.COLUMN_NAME = cc.COLUMN_NAME WHERE cc.TABLE_NAME = ?");
            statement.setString(1, table.getName());
            rs = statement.executeQuery();
            List<DbField> fields = new ArrayList<DbField>();
            String columnName;
            String columnComment;
            int priKey;
            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
                columnComment = rs.getString("COMMENTS");
                priKey = rs.getInt("PRI_KEY");
                if (StringUtils.isBlank(columnComment)) {
                    columnComment = columnName;
                }
                fields.add(new DbField(table.getId(), columnName, columnComment, priKey));
            }
            rs.close();
            statement.close();
            connection.close();
            return fields;
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static List<DbField> getFieldsMSSQL(String ip, int port, String dbName, EtlAllTable table, String userName, String pwd) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + dbName;
            connection = DriverManager.getConnection(url, userName, pwd);
            statement = connection.prepareStatement("SELECT a. NAME AS COLUMN_NAME, CAST(C. VALUE AS VARCHAR(100)) AS COMMENTS, CASE WHEN s.keyno IS NULL THEN 0 ELSE 1 END AS PRI_KEY FROM syscolumns a LEFT JOIN systypes b ON a.xtype = b.xtype LEFT JOIN sysindexkeys s ON s.id = a.id AND s.colid = a.colid LEFT JOIN sys.extended_properties C ON C.major_id = a.id AND C.minor_id = a.colid WHERE a.id = object_id (?)");
            statement.setString(1, table.getName());
            rs = statement.executeQuery();
            List<DbField> fields = new ArrayList<DbField>();
            String columnName;
            String columnComment;
            int priKey;
            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
                columnComment = rs.getString("COMMENTS");
                priKey = rs.getInt("PRI_KEY");
                if (StringUtils.isBlank(columnComment)) {
                    columnComment = columnName;
                }
                fields.add(new DbField(table.getId(), columnName, columnComment, priKey));
            }
            rs.close();
            statement.close();
            connection.close();
            return fields;
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return null;
    }

    public static boolean testConnMySql(EtlDataSource etlDataSource) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + etlDataSource.getDbIp() + ":" + etlDataSource.getDbPort() + "/" + etlDataSource.getDbName();
            connection = DriverManager.getConnection(url, etlDataSource.getDbUsername(), etlDataSource.getDbPwd());
            connection.close();
            return true;
        } catch (Exception e) {
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public static boolean testConnOracle(EtlDataSource etlDataSource) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@" + etlDataSource.getDbIp() + ":" + etlDataSource.getDbPort() + "/" + etlDataSource.getDbName();
            connection = DriverManager.getConnection(url, etlDataSource.getDbUsername(), etlDataSource.getDbPwd());
            connection.close();
            return true;
        } catch (Exception e) {
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public static boolean testConnMSSQL(EtlDataSource etlDataSource) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + etlDataSource.getDbIp() + ":" + etlDataSource.getDbPort() + ";DatabaseName=" + etlDataSource.getDbName();
            connection = DriverManager.getConnection(url, etlDataSource.getDbUsername(), etlDataSource.getDbPwd());
            connection.close();
            return true;
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
