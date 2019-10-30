package net.ys.constant;

/**
 * 系统正则
 * User: LiWenC
 * Date: 17-6-20
 */
public enum SysRegex {
    TABLE_FIELD_NAME("[a-zA-Z]{1}\\w+", "表名或字段名"),
    PHONE_NUMBER("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$", "手机号码"),
    EMAIL("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", "邮箱地址"),
    IP("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$", "IP地址"),
    NUMBER_TYPE("\\d+(\\.\\d+)*", "数字类型"),;

    public String regex;
    public String desc;

    private SysRegex(String regex, String desc) {
        this.regex = regex;
        this.desc = desc;
    }
}
