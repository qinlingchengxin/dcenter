package net.ys.constant;

/**
 * 系统枚举code
 * User: LiWenC
 * Date: 16-9-6
 */
public enum SysEnumCode {

    DATA_UP_LIMIT(1000, "上传数据条数限制"),;

    public int code;
    public String desc;

    private SysEnumCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
