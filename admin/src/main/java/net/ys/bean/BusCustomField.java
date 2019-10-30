package net.ys.bean;

import java.io.Serializable;

/**
 * 实体字段记录表
 */
public class BusCustomField implements Serializable {

    private String id;    //主键

    private String tableName;    //用户表名

    private String fieldName;    //字段名称

    private String realFieldName;    //真实字段名称

    private String inChinese;    //中文名称

    private int fieldType;    //字段类型 0-整形/1-字符串/2-时间戳/3-浮点型

    private int fieldLength = 11;    //字段长度

    private int precisions;    //字段精度

    private int required;    //是否必填 0-否/1-是

    private String valueField;    //值域

    private int uniqueKey = 0;    //是否去重 0-否/1-是

    private int exposed = 1;    //是否对外暴露 0-不暴露/1-暴露（对接系统是否访问）

    private String remarks;    //备注

    private String createAid;    //创建人id

    private long createTime;    //创建时间

    private String updateAid;    //修改人id

    private long updateTime;    //修改时间

    private long releaseTime;    //发布时间

    public BusCustomField() {
    }

    public BusCustomField(String id, String tableName, String fieldName, String realFieldName, String inChinese, int fieldType, int fieldLength, int precisions, int required, String valueField, int uniqueKey, int exposed, String remarks, String createAid, long createTime, String updateAid, long updateTime) {
        this.id = id;
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.realFieldName = realFieldName;
        this.inChinese = inChinese;
        this.fieldType = fieldType;
        this.fieldLength = fieldLength;
        this.precisions = precisions;
        this.required = required;
        this.valueField = valueField;
        this.uniqueKey = uniqueKey;
        this.exposed = exposed;
        this.remarks = remarks;
        this.createAid = createAid;
        this.createTime = createTime;
        this.updateAid = updateAid;
        this.updateTime = updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getRealFieldName() {
        return realFieldName;
    }

    public void setRealFieldName(String realFieldName) {
        this.realFieldName = realFieldName;
    }

    public void setInChinese(String inChinese) {
        this.inChinese = inChinese;
    }

    public String getInChinese() {
        return this.inChinese;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldType() {
        return this.fieldType;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public int getFieldLength() {
        return this.fieldLength;
    }

    public void setPrecisions(int precisions) {
        this.precisions = precisions;
    }

    public int getPrecisions() {
        return this.precisions;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public void setExposed(int exposed) {
        this.exposed = exposed;
    }

    public int getExposed() {
        return this.exposed;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return this.remarks;
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