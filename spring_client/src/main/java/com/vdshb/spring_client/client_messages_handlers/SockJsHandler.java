package com.vdshb.spring_client.client_messages_handlers;

import com.vdshb.spring_client.domain.ChatTextMessage;
import com.vdshb.spring_client.domain.Subscriber;
import com.vdshb.spring_client.domain.WsMessage;
import com.vdshb.spring_client.domain.enums.ClientConnectionType;
import com.vdshb.spring_client.service.ClientMessaging;
import com.vdshb.spring_client.service.InterServerMessaging;
import com.vdshb.spring_client.service.SubscribersVault;
import com.vdshb.spring_client.service.WsMessageJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

@Component
public class SockJsHandler extends TextWebSocketHandler {

    @Autowired
    private WsMessageJsonSerializer jsonSerializer;
    @Autowired
    private InterServerMessaging interServerMessaging;
    @Autowired
    private SubscribersVault subscribersVault;
    @Autowired
    private ClientMessaging clientMessaging;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setConnectionType(ClientConnectionType.WEB_SOCKET);
        newSubscriber.setWsSession(session);
        subscribersVault.subscribe(newSubscriber);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage incomeMessage) throws Exception {

        WsMessage wsMessage = jsonSerializer.fromJson(incomeMessage.getPayload());
        if ("SEND_TEXT_MESSAGE".equals(wsMessage.getCommand())) {
            ChatTextMessage chatTextMessage = wsMessage.getMessage();
            chatTextMessage.setTime(LocalDateTime.now());
            interServerMessaging.sendTextMessage(chatTextMessage);
            clientMessaging.sendToAllClients(chatTextMessage);

            WsMessage answer = new WsMessage();
            answer.setCommand("SEND_TEXT_MESSAGE_RECEIVE");
            answer.setMessageId(wsMessage.getMessageId());
            answer.setCode(200);
            session.sendMessage(new TextMessage(jsonSerializer.toJson(answer)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        subscribersVault.unSubscribe(session);
    }
}
