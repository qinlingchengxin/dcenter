package net.ys.bean;

import java.io.Serializable;

/**
 * 系统配置表
 */
public class BusConfig implements Serializable {

    private String id;    //主键

    private String cfgName;    //配置名称

    private int code;    //枚举代码

    private int vi;    //整形值

    private String vs;    //字符串值

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public String getCfgName() {
        return this.cfgName;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
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