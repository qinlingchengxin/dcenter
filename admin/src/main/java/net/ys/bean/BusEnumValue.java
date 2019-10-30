package net.ys.bean;

import java.io.Serializable;

/**
 * 系统枚举值表
 */
public class BusEnumValue implements Serializable {

    private String id;    //主键

    private int enumCode;    //枚举CODE

    private int vi;    //整形值

    private String vs;    //字符串值

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setEnumCode(int enumCode) {
        this.enumCode = enumCode;
    }

    public int getEnumCode() {
        return this.enumCode;
    }

    public void setVi(int vi) {
        this.vi = vi;
    }

    public int getVi() {
        return this.vi;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public String getVs() {
        return this.vs;
    }

}