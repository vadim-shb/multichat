package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.StompMessage;
import com.vdshb.spring_client.service.ClientMessaging;
import com.vdshb.spring_client.service.InterServerMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@MessageMapping("/app")
public class ClientStompController {

    @Autowired
    private InterServerMessaging interServerMessaging;
    @Autowired
    private ClientMessaging clientMessaging;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send-text-message")
    public void sendTextMessage(StompMessage msg) throws IOException {
        msg.getMessage().setTime(LocalDateTime.now());
        interServerMessaging.sendTextMessage(msg.getMessage());
        clientMessaging.sendToAllClients(msg.getMessage());
        simpMessagingTemplate.convertAndSend("/topic/receive-confirm/" + msg.getMessageId(), "OK");
    }

}
