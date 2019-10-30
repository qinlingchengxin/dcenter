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

    public static String accessIpAddress;

    public static String ktrPath;

    public static String kjbPath;

    public static String attachmentPath;

    @Value("${access_ip_address}")
    public void setAccessIpAddress(String accessIpAddress) {
        this.accessIpAddress = accessIpAddress;
    }

    @Value("${ktr_path}")
    public void setKtrPath(String ktrPath) {
        File file = new File(ktrPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("ktrPath is invalid");
            }
        }
        this.ktrPath = file.getAbsolutePath().replace('\\', '/') + "/";
    }

    @Value("${kjb_path}")
    public void setKjbPath(String kjbPath) {
        File file = new File(kjbPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("kjbPath is invalid");
            }
        }
        this.kjbPath = file.getAbsolutePath().replace('\\', '/') + "/";
    }

    @Value("${attachment_path}")
    public void setAttachmentPath(String attachmentPath) {
        File file = new File(attachmentPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("attachmentPath is invalid");
            }
        }
        this.attachmentPath = file.getAbsolutePath().replace('\\', '/') + "/";
    }
}
