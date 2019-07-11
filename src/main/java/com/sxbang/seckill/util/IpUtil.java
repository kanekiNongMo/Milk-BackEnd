package com.sxbang.seckill.util;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
/**
 * @author kaneki
 * @date 2019/6/28 13:06
 */
public class IpUtil {

	public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        String unknown = "unknown";
        int i =15;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                String localhost = "127.0.0.1";
                if (ipAddress.equals(localhost)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    if (inet != null){
                        ipAddress = inet.getHostAddress();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > i) {
                String comma = ",";
                if (ipAddress.indexOf(comma) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(comma));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }
}

