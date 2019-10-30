package net.ys.bean;

/**
 * User: LiWenC
 * Date: 17-12-7
 */
public class SyncTable {

    private String realTabName;

    private String tableName;

    private long lastPackTime;

    public String getRealTabName() {
        return realTabName;
    }

    public void setRealTabName(String realTabName) {
        this.realTabName = realTabName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getLastPackTime() {
        return lastPackTime;
    }

    public void setLastPackTime(long lastPackTime) {
        this.lastPackTime = lastPackTime;
    }
}
