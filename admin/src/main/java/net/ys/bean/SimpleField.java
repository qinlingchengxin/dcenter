package net.ys.bean;

import java.io.Serializable;

/**
 * 实体字段简版
 */
public class SimpleField implements Serializable {

    private String fieldName;    //字段名称

    private String realFieldName;    //真实字段名称

    private String inChinese;    //中文名称

    public SimpleField(String fieldName, String realFieldName, String inChinese) {
        this.fieldName = fieldName;
        this.realFieldName = realFieldName;
        this.inChinese = inChinese;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRealFieldName() {
        return realFieldName;
    }

    public void setRealFieldName(String realFieldName) {
        this.realFieldName = realFieldName;
    }

    public String getInChinese() {
        return inChinese;
    }

    public void setInChinese(String inChinese) {
        this.inChinese = inChinese;
    }
}