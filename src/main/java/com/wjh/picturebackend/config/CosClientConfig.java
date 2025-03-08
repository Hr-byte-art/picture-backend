package com.wjh.picturebackend.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 
 * @Date 2025/2/5 17:52:40
 * @Description 腾讯云对象存储配置
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "cos.client")
public class CosClientConfig {

    /**
     * 腾讯云对象存储 -- 域名
     */
    private String host;

    /**
     * 腾讯云对象存储 -- secretId
     */
    private String secretId;

    /**
     * 腾讯云对象存储 -- 密钥
     */
    private String secretKey;

    /**
     * 腾讯云对象存储 -- 区域
     */
    private String region;

    /**
     * 腾讯云对象存储 -- 桶
     */
    private String bucket;

    @Bean
    public COSClient cosClient() {
    // 1 初始化用户身份信息（secretId, secretKey）。
// SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    ClientConfig clientConfig = new ClientConfig(new Region(region));
// 这里建议设置使用 https 协议
    // 3 生成 cos 客户端。
    return new COSClient(cred, clientConfig);
    }
}
