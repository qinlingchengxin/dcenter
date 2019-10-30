package net.ys.bean;

import java.io.Serializable;

/**
 * 用户表
 */
public class BusUser implements Serializable {

    private String id;    //主键

    private String username;    //用户名

    private String password;    //用户密码

    private String platformCode;    //平台编码

    private String platformName;    //平台名称

    private String createAid;    //创建人id

    private long createTime;    //创建时间

    private String updateAid;    //修改人id

    private long updateTime;    //修改时间

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformCode() {
        return this.platformCode;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
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
}