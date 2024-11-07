package com.kramrs.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: kramrs
 * @Description: 七牛云配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
public class QiniuProperties {

    /**
     * url 或者 域名
     */
    private String url;

    /**
     * 存储桶名字
     */
    private String bucketName;

    /**
     * //区域 如huanan hubei
     */
    private String region;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

}
