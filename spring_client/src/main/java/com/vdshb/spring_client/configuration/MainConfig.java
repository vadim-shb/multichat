package com.vdshb.spring_client.configuration;

import com.vdshb.spring_client.service.MessageVault;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public MessageVault messageVault() {
        return new MessageVault();
    }

}
