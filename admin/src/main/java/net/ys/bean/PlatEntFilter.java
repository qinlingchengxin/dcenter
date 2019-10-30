package net.ys.bean;

import java.io.Serializable;

/**
 * 实体下载过滤条件
 */
public class PlatEntFilter implements Serializable {

    private String id;    //主键

    private String epId;    //实体权限ID

    private String tableName;    //表名

    private String field;    //字段

    private String condition;    //条件 =、>=、<=、!=、like

    private String an;    //条件组合  AND/OR

    private String conValue;    //条件值，均用字符串类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }
}