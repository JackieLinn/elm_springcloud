package ynu.jackielinn.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrdersMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersMainApplication.class, args);
    }
}
