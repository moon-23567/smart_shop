package guat.lxy.bigdata.smart_shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("guat.lxy.bigdata.smart_shop.mapper")
public class SmartShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartShopApplication.class, args);
    }
}

