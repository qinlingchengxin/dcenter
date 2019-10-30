package net.ys.bean;

/**
 * User: LiWenC
 * Date: 17-12-21
 */
public class SegFile {

    private long id;//主键

    private int index;//切分文件索引号

    private long startPoint;//起始点

    private String fileName; //原文件名

    private String fileNameTmp;//临时文件名

    private long fileNum;//切分文件总数

    private String path;//上传保存文件夹

    private long fileLen;//文件总长度

    private int upStatus;//上传状态

    public SegFile() {
    }

    public SegFile(int index, long startPoint, String fileName, String fileNameTmp, long fileNum, String path, long fileLen) {
        this.index = index;
        this.startPoint = startPoint;
        this.fileName = fileName;
        this.fileNameTmp = fileNameTmp;
        this.fileNum = fileNum;
        this.path = path;
        this.fileLen = fileLen;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(long startPoint) {
        this.startPoint = startPoint;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameTmp() {
        return fileNameTmp;
    }

    public void setFileNameTmp(String fileNameTmp) {
        this.fileNameTmp = fileNameTmp;
    }

    public long getFileNum() {
        return fileNum;
    }

    public void setFileNum(long fileNum) {
        this.fileNum = fileNum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getFileLen() {
        return fileLen;
    }

    public void setFileLen(long fileLen) {
        this.fileLen = fileLen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(int upStatus) {
        this.upStatus = upStatus;
    }
}
