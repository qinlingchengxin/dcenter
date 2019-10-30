package net.ys.constant;

import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public enum GenResult {
    SUCCESS(1000, "success"),

    FAILED(1001, "failed"),

    REQUEST_INVALID(1002, "invalid request"),

    PARAMS_ERROR(1003, "params error"),

    DB_ERROR(1004, "db error"),

    DATA_UP_LIMIT(1005, "upload data exceed limit"),

    NOT_EXIST_OR_RELEASE(1006, "table not exist or release"),

    NO_PRIVILEGE(1007, "no privilege"),

    TABLE_EXISTS(1008, "table exists"),

    TABLE_NAME_INVALID(1009, "table name invalid"),

    FIELD_EXISTS(1010, "field exists"),

    NO_DATA(1011, "data not exists"),

    NOT_INT(1012, "not int trans"),

    NO_MAP_FIELD(1013, "no mapping fields"),

    DOWN_LIMIT(1014, "download limit 20 records"),

    EXEC_ING(1015, "executing please refresh"),

    NO_ACCESS_PRIVILEGE(1016, "not in access ip list"),

    DECRYPT_DATA_ERROR(1017, "data decrypt error"),

    IS_NOT_JSON_ARRAY(1018, "data is not json array format"),

    NO_VALID_DATA(1019, "no valid data"),

    FIELD_NOT_EXIT(1021, "fields not exists"),

    UNKNOWN_ERROR(9999, "unknown error");

    public int msgCode;
    public String message;

    private GenResult(int msgCode, String message) {
        this.msgCode = msgCode;
        this.message = message;
    }

    public Map<String, Object> genResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        return map;
    }

    public Map<String, Object> genResult(Object data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        map.put("data", data);
        return map;
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        object.put("code", msgCode);
        object.put("msg", message);
        return object.toString();
    }

    public static GenResult fromCode(int msgCode) {
        GenResult[] results = GenResult.values();
        for (GenResult result : results) {
            if (result.msgCode == msgCode) {
                return result;
            }
        }
        return null;
    }
}
