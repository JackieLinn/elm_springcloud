package ynu.jackielinn.test2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Test2MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(Test2MainApplication.class, args);
    }
}
