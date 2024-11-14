package com.example.demo.CORS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.text.SimpleDateFormat;

@Configuration
public class DateConfig {
    @Bean
    public SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }
}