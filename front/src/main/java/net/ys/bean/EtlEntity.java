package net.ys.bean;

import java.io.Serializable;

/**
 * User: LiWenC
 * Date: 17-12-7
 */
public class EtlEntity implements Serializable {

    private String id;

    private String prjId;

    private String srcTabName;

    private String desTabName;

    private String srcPrimaryKey;

    private String desPrimaryKey;

    private String description;

    private String etlId;

    private long createTime;

    private int repeat = 0;

    private int scheduleType = 0;

    private int intervalSecond = 0;

    private int intervalMinute = 30;

    private int fixedHour = 12;

    private int fixedMinute = 0;

    private int fixedWeekday = 0;

    private int fixedDay = 1;

    private int isExec = 0;

    private String condition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getSrcTabName() {
        return srcTabName;
    }

    public void setSrcTabName(String srcTabName) {
        this.srcTabName = srcTabName;
    }

    public String getDesTabName() {
        return desTabName;
    }

    public void setDesTabName(String desTabName) {
        this.desTabName = desTabName;
    }

    public String getSrcPrimaryKey() {
        return srcPrimaryKey;
    }

    public void setSrcPrimaryKey(String srcPrimaryKey) {
        this.srcPrimaryKey = srcPrimaryKey;
    }

    public String getDesPrimaryKey() {
        return desPrimaryKey;
    }

    public void setDesPrimaryKey(String desPrimaryKey) {
        this.desPrimaryKey = desPrimaryKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtlId() {
        return etlId;
    }

    public void setEtlId(String etlId) {
        this.etlId = etlId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    public int getIntervalSecond() {
        return intervalSecond;
    }

    public void setIntervalSecond(int intervalSecond) {
        this.intervalSecond = intervalSecond;
    }

    public int getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public int getFixedHour() {
        return fixedHour;
    }

    public void setFixedHour(int fixedHour) {
        this.fixedHour = fixedHour;
    }

    public int getFixedMinute() {
        return fixedMinute;
    }

    public void setFixedMinute(int fixedMinute) {
        this.fixedMinute = fixedMinute;
    }

    public int getFixedWeekday() {
        return fixedWeekday;
    }

    public void setFixedWeekday(int fixedWeekday) {
        this.fixedWeekday = fixedWeekday;
    }

    public int getFixedDay() {
        return fixedDay;
    }

    public void setFixedDay(int fixedDay) {
        this.fixedDay = fixedDay;
    }

    public int getExec() {
        return isExec;
    }

    public void setExec(int exec) {
        isExec = exec;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
