package net.ys.bean;

import java.io.Serializable;

/**
 * 业务表
 */
public class BusCustomEntity implements Serializable {

    private String id;    //主键

    private String realTabName;    //真实表名

    private String tableName;    //表名

    private String inChinese;    //中文名称

    private String callbackUrl;    //回调地址

    private String description;    //描述

    private String createAid;    //创建人id

    private long createTime;    //创建时间

    private String updateAid;    //修改人id

    private long updateTime;    //修改时间

    private String releaseAid;    //发布人id

    private long releaseTime;    //发布时间

    public BusCustomEntity() {
    }

    public BusCustomEntity(String id, String realTabName, String tableName, String inChinese, String callbackUrl, String description, String createAid, long createTime, String updateAid, long updateTime, String releaseAid, long releaseTime) {
        this.id = id;
        this.realTabName = realTabName;
        this.tableName = tableName;
        this.inChinese = inChinese;
        this.callbackUrl = callbackUrl;
        this.description = description;
        this.createAid = createAid;
        this.createTime = createTime;
        this.updateAid = updateAid;
        this.updateTime = updateTime;
        this.releaseAid = releaseAid;
        this.releaseTime = releaseTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

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

    public void setInChinese(String inChinese) {
        this.inChinese = inChinese;
    }

    public String getInChinese() {
        return this.inChinese;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCreateAid(String createAid) {
        this.createAid = createAid;
    }

    public String getCreateAid() {
        return this.createAid;
    }

    public void setUpdateAid(String updateAid) {
        this.updateAid = updateAid;
    }

    public String getUpdateAid() {
        return this.updateAid;
    }

    public void setReleaseAid(String releaseAid) {
        this.releaseAid = releaseAid;
    }

    public String getReleaseAid() {
        return this.releaseAid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
}