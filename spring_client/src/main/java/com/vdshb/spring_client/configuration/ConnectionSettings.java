package com.vdshb.spring_client.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ConnectionSettings {

    private final String restConnectionUrl;

    @Autowired
    public ConnectionSettings(Environment environment) {
        restConnectionUrl = environment.getProperty("rest.connection.url");
    }

}
