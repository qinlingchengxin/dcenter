package net.ys.bean;

import java.io.Serializable;

/**
 * 数据传输日志
 */
public class BusDataTransLog implements Serializable {

    private String id;    //主键

    private String platformCode;    //平台编码

    private String tableName;    //用户表

    private int transType;    //传输类型 0-上行/1-下行

    private String content;    //传输内容

    private String failedContent;    //传输内容

    private int status;    //传输状态 0-失败/1-成功

    private long createTime;    //上传或下载时间

    private String remarks;    //备注

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

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getTransType() {
        return this.transType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getFailedContent() {
        return failedContent;
    }

    public void setFailedContent(String failedContent) {
        this.failedContent = failedContent;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}