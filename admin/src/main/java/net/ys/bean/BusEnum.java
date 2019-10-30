package net.ys.bean;

import java.io.Serializable;

/**
 * 系统枚举表
 */
public class BusEnum implements Serializable {

    private String id;    //主键

    private int code;    //枚举code值

    private String enumName;    //枚举名称

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumName() {
        return this.enumName;
    }

}