package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author  小艺
 * @date 2019/1/24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableZuulProxy
public class LyApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(LyApiGateway.class,args);
    }
}
