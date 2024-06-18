package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Slf4j
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        ConsoleRunning.run();
    }
}
