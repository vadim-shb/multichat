package com.vdshb.spring_client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vdshb.spring_client.domain.WsMessage;
import com.vdshb.spring_client.domain.WsMessagePack;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WsMessageJsonSerializer {

    public String toJson(WsMessage message) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(message);
    }

    public String toJson(WsMessagePack messages) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(messages);
    }

    public WsMessage fromJson(String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, WsMessage.class);
    }
}
