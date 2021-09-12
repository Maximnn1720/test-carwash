package com.aisa.itservice.testcarwash;

import com.aisa.itservice.testcarwash.Entites.User;
import com.aisa.itservice.testcarwash.Services.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestCarwashApplication  {
    static final Logger log =
            LoggerFactory.getLogger(TestCarwashApplication.class);

    @Autowired
    private static CustomUserDetailService userService;

    public static void main(String[] args) {
        SpringApplication.run(TestCarwashApplication.class, args);

        log.info("Before Starting application");
        log.debug("Starting my application in debug with {} args", args.length);
        log.info("Starting my application with {} args.", args.length);
    }

}



