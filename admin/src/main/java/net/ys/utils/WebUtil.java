package net.ys.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * 控制器工具类
 * User: LiWenC
 * Date: 16-9-8
 */
public class WebUtil {

    /**
     * 获取请求ip地址
     *
     * @param request
     * @param transLocalIp whether to transfer 127.0.0.1 to real ip
     * @return
     */
    public static String getClientIP(HttpServletRequest request, boolean transLocalIp) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) && transLocalIp) {
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getLocalHost();
            } catch (Exception e) {
            }
            ip = inetAddress.getHostAddress();
        }
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
