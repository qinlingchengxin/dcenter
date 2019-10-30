package net.ys.bean;

import java.io.Serializable;

/**
 * 实体或字段修改记录表
 */
public class BusTfChangeLog implements Serializable {

    private String id;    //主键

    private int chgType;    //修改类型 0-实体/1-字段

    private String objId;    //修改对象id

    private String updateAid;    //修改人id

    private long updateTime;    //修改时间

    private String content;    //修改说明

    public BusTfChangeLog() {
    }

    public BusTfChangeLog(String id, int chgType, String objId, String updateAid, long updateTime, String content) {
        this.id = id;
        this.chgType = chgType;
        this.objId = objId;
        this.updateAid = updateAid;
        this.updateTime = updateTime;
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setChgType(int chgType) {
        this.chgType = chgType;
    }

    public int getChgType() {
        return this.chgType;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjId() {
        return this.objId;
    }

    public void setUpdateAid(String updateAid) {
        this.updateAid = updateAid;
    }

    public String getUpdateAid() {
        return this.updateAid;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}