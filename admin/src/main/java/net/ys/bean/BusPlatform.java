package net.ys.bean;

import java.io.Serializable;

/**
 * 平台（接入系统）
 */
public class BusPlatform implements Serializable {

    private String id;    //主键

    private String code;    //平台编码

    private String platName;    //平台名称

    private String area;    //地区

    private String areaId;    //地区枚举id

    private String contactUsername;    //联系人

    private String contactPhone;    //联系电话

    private int transType = 2;    //传输方式 0-共享库/1-共享目录/2-接口

    private String sharePath;    //共享目录

    private String publicKey;    //公钥

    private String privateKey;    //私钥

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatName() {
        return this.platName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public String getContactUsername() {
        return this.contactUsername;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getTransType() {
        return this.transType;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public String getSharePath() {
        return this.sharePath;
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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}