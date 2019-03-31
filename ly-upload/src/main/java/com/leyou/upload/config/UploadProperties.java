package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author njtech
 * @create 2019/3/30
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;

}