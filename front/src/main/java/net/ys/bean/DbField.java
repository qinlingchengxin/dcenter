package net.ys.bean;

/**
 * User: LiWenC
 * Date: 17-12-7
 */
public class DbField {

    private String tableId;//表id

    private String fieldName;//字段名

    private String comment;//注释

    private int priKey;//是否为主键

    public DbField(String fieldName, String comment) {
        this.fieldName = fieldName;
        this.comment = comment;
    }

    public DbField(String tableId, String fieldName, String comment, int priKey) {
        this.tableId = tableId;
        this.fieldName = fieldName;
        this.comment = comment;
        this.priKey = priKey;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPriKey() {
        return priKey;
    }

    public void setPriKey(int priKey) {
        this.priKey = priKey;
    }
}
