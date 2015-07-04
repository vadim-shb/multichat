package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.Subscriber;
import com.vdshb.spring_client.domain.WsMessagePack;
import com.vdshb.spring_client.service.MessageVault;
import com.vdshb.spring_client.service.SubscribersVault;
import com.vdshb.spring_client.service.WsMessageJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Component
public class PushMessagesSender {
    @Autowired
    private MessageVault messageVault;
    @Autowired
    private WsMessageJsonSerializer jsonSerializer;
    @Autowired
    private SubscribersVault subscribersVault;

    public void pushWebSocketMessages(Subscriber subscriber) throws IOException {
        MessageVault.MessagesToDeliver messagesToDeliver = messageVault.getMessagesToDeliver(subscriber.getLastMessageId());
        WsMessagePack wsMessagePack = new WsMessagePack();
        wsMessagePack.setCommand("SEND_TEXT_MESSAGES");
        wsMessagePack.setMessages(messagesToDeliver.messages);
        subscriber.getWsSession().sendMessage(new TextMessage(jsonSerializer.toJson(wsMessagePack)));
        subscriber.setLastMessageId(messagesToDeliver.lastId);
    }

    public void pushWebSocketMessagesToAllSubscribers() throws IOException {
        for (Subscriber subscriber : subscribersVault.getWebSocketSubscribers()) {
            pushWebSocketMessages(subscriber);
        }
    }
}
