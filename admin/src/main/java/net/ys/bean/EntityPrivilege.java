package net.ys.bean;

import java.io.Serializable;

/**
 * 实体访问权限
 */
public class EntityPrivilege implements Serializable {

    private String id;    //主键

    private String platformCode;    //平台编码

    private String platformName;    //平台名称

    private String tableName;    //表名称

    private int priType;    //权限类型 0-无权/1-上行/2-下行

    private String assignerAid;    //权限分配人id

    private long assignerTime;    //分配时间

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

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPriType(int priType) {
        this.priType = priType;
    }

    public int getPriType() {
        return this.priType;
    }

    public void setAssignerAid(String assignerAid) {
        this.assignerAid = assignerAid;
    }

    public String getAssignerAid() {
        return this.assignerAid;
    }

    public long getAssignerTime() {
        return assignerTime;
    }

    public void setAssignerTime(long assignerTime) {
        this.assignerTime = assignerTime;
    }
}