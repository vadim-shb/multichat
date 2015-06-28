package com.vdshb.spring_client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vdshb.spring_client.domain.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestMessaging {

    @Autowired
    private RestClient restClient;

    public void sendTextMessage(TextMessage textMessage) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(textMessage);
            restClient.post("/api/messaging/rest/message", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

