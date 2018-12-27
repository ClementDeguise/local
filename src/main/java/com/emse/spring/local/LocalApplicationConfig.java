package com.emse.spring.local;

import com.emse.spring.local.hello.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class LocalApplicationConfig {

    @Bean
    public CommandLineRunner greetingCommandLine(GreetingService greeting) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                greeting.greet("Spring");
            }
        };
    }


}