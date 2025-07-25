package com.sameer.zapurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZapurlBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZapurlBackendApplication.class, args);
    }

}
