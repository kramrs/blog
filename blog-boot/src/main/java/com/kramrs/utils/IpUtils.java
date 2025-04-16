package com.kramrs.utils;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: kramrs
 * @Description: IP地址工具类
 */
@Slf4j
public class IpUtils {

    private static final Searcher SEARCHER;

    static {
        try (InputStream inputStream = IpUtils.class.getClassLoader()
                .getResourceAsStream("ipdb/ip2region.xdb")) {
            if (inputStream == null) {
                throw new RuntimeException("ip2region.xdb file not found in resources");
            }
            byte[] cBuff = new byte[inputStream.available()];
            inputStream.read(cBuff);
            SEARCHER = Searcher.newWithBuffer(cBuff);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize ip2region", e);
        }
    }

    /**
     * 获取用户真实IP地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }


    /**
     * 根据ip从 ip2region.xdb 中获取地理位置
     *
     * @param ip ip地址
     * @return 地理位置
     */
    public static String getIpSource(String ip) {
        try {
            String ipInfo = SEARCHER.search(ip);
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            log.error("getIpSource exception:, {}", e.getMessage());
        }
        return "未知";
    }
}
