package net.ys.bean;

import java.io.Serializable;

/**
 * 监控实体
 */
public class BusMonitor implements Serializable {

    private String id;    //主键

    private String platformCode;    //平台编码

    private String platName;    //平台名称

    private int type;    //监控方式 0-ping/1-telnet

    private String ip; //监控ip

    private int port; //监控端口

    private int intervalDay;    //间隔天

    private int intervalHour;    //间隔小时

    private int intervalMinute;    //间隔分钟

    private int notice;    //是否通知 0-否/1-是

    private int upAmount; //上传数量

    private int downAmount; //下载数量

    private int status; //监控状态 0-待检测/1-正常/2-挂掉

    private long createTime;    //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getIntervalDay() {
        return intervalDay;
    }

    public void setIntervalDay(int intervalDay) {
        this.intervalDay = intervalDay;
    }

    public int getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(int intervalHour) {
        this.intervalHour = intervalHour;
    }

    public int getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getUpAmount() {
        return upAmount;
    }

    public void setUpAmount(int upAmount) {
        this.upAmount = upAmount;
    }

    public int getDownAmount() {
        return downAmount;
    }

    public void setDownAmount(int downAmount) {
        this.downAmount = downAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}