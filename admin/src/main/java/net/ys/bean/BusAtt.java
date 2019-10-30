package net.ys.bean;

import java.io.Serializable;

/**
 * 附件表-前置机用
 */
public class BusAtt implements Serializable {

    private long id;    //主键

    private String fileName;    //文件原名

    private String tableName;    //表名

    private String fieldName;    //字段名

    private String dataPriField;    //数据主键

    private String dataId;    //数据id

    private String platformCode;    //平台编码

    private int path;    //文件路径

    private String attName;    //文件名（后台生成）

    private int upStatus;    //上传状态 0-未上传/1-已上传

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public int getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
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

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}