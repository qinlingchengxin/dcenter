package net.ys.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 系统配置类
 * User: NMY
 * Date: 16-8-28
 */
@Component
public class SysConfig {

    public static String platformCode;

    public static String transType;

    public static String decenterUrl;

    public static String attachmentPath;

    public static String attachmentTmp;

    public static String attachmentRead;

    public static String dataPath;

    public static String dataPathSuccess;

    public static String dataPathFailed;

    @Value("${platform_code}")
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Value("${decenter_url}")
    public void setDecenterUrl(String decenterUrl) {
        this.decenterUrl = decenterUrl;
    }

    @Value("${trans_type}")
    public void setTransType(String transType) {
        this.transType = transType;
    }

    @Value("${attachment_path}")
    public void setAttachmentPath(String attachmentPath) {
        File file = new File(attachmentPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("attachmentPath is invalid");
            }
        }

        String basePath = file.getAbsolutePath().replace('\\', '/');

        this.attachmentPath = basePath + "/";

        file = new File(basePath + "_tmp");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("attachmentTmp is invalid");
            }
        }
        this.attachmentTmp = basePath + "_tmp/";

        file = new File(basePath + "_read");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("attachmentRead is invalid");
            }
        }
        this.attachmentRead = basePath + "_read/";
    }

    @Value("${data_path}")
    public void setKjbPath(String dataPath) {
        File file = new File(dataPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("dataPath is invalid");
            }
        }

        String basePath = file.getAbsolutePath().replace('\\', '/');

        this.dataPath = basePath + "/";

        file = new File(basePath + "_success");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("dataPathSuccess is invalid");
            }
        }
        this.dataPathSuccess = basePath + "_success/";

        file = new File(basePath + "_failed");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("dataPathFailed is invalid");
            }
        }
        this.dataPathFailed = basePath + "_failed/";
    }
}
