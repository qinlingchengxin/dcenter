package net.ys.bean;

import java.io.Serializable;

/**
 * 实体权限修改记录表
 */
public class EpChangeLog implements Serializable {

    private String id;    //主键

    private String platformCode;    //平台编码

    private String platformName;    //平台名称

    private String tableName;    //表名

    private int chgType;    //修改类型 0-新增/1-取消

    private String updateAid;    //修改人id

    private long updateTime;    //修改时间

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformCode() {
        return this.platformCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setChgType(int chgType) {
        this.chgType = chgType;
    }

    public int getChgType() {
        return this.chgType;
    }

    public void setUpdateAid(String updateAid) {
        this.updateAid = updateAid;
    }

    public String getUpdateAid() {
        return this.updateAid;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}