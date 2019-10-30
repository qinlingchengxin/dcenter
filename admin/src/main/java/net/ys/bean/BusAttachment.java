package net.ys.bean;

import java.io.Serializable;

/**
 * 附件表
 */
public class BusAttachment implements Serializable {

    private String id;    //主键

    private String path;    //路径,范围 [100-199]

    private String attName;    //文件名（后台生成）

    private String srcName;    //文件原名称

    private String platformCode;    //平台编码

    private String tableName;    //用户表

    private String fieldName;    //对应字段

    private String dataPriField;    //数据主键名称

    private String dataId;    //数据id

    private int updateStatus;    //更新表数据状态 0-未更新/1-已更新

    private long createTime;    //上传时间

    public BusAttachment() {
    }

    public BusAttachment(String id, String path, String attName, String srcName, String platformCode, String tableName, String fieldName, String dataPriField, String dataId, long createTime) {
        this.id = id;
        this.path = path;
        this.attName = attName;
        this.srcName = srcName;
        this.platformCode = platformCode;
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.dataPriField = dataPriField;
        this.dataId = dataId;
        this.createTime = createTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getAttName() {
        return this.attName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getSrcName() {
        return this.srcName;
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataPriField() {
        return dataPriField;
    }

    public void setDataPriField(String dataPriField) {
        this.dataPriField = dataPriField;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return this.createTime;
    }

}