package com.vdshb.spring_client.service;

import com.vdshb.spring_client.client_messages_handlers.PushMessagesSender;
import com.vdshb.spring_client.domain.ChatTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientMessaging {
    @Autowired
    private MessageVault messageVault;
    @Autowired
    private PushMessagesSender pushMessagesSender;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToAllClients(ChatTextMessage message) throws IOException {
        messageVault.addMessage(message);
        pushMessagesSender.pushMessageToAllWebSocketSubscribers(message);
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }
}
