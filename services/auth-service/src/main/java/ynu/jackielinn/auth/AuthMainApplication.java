package ynu.jackielinn.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"ynu.jackielinn.auth", "ynu.jackielinn.common.utils","ynu.jackielinn.common.filter"})
public class AuthMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainApplication.class, args);
    }
}
