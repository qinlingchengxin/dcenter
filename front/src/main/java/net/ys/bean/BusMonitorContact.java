package net.ys.bean;

import java.io.Serializable;

/**
 * 监控联系人
 */
public class BusMonitorContact implements Serializable {

    private String id;    //主键

    private String monitorId;    //监控id

    private String name;    //监控联系人姓名

    private String telNo;    //通知人手机号

    private String email;    //通知人邮箱

    private long createTime;    //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}