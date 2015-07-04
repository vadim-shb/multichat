package com.vdshb.spring_client.service;

import com.vdshb.spring_client.domain.Subscriber;
import com.vdshb.spring_client.domain.enums.ClientConnectionType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubscribersVault {

    Map<WebSocketSession, Subscriber> wsSubscribers = new HashMap<>();
    Map<String, Subscriber> restSubscribers = new HashMap<>();

    public void subscribe(Subscriber newSubscriber) {
        if (newSubscriber.getConnectionType() == ClientConnectionType.REST) {
            restSubscribers.put(newSubscriber.getRestSessionId(), newSubscriber);
            return;
        }
        if (newSubscriber.getConnectionType() == ClientConnectionType.WEB_SOCKET) {
            wsSubscribers.put(newSubscriber.getWsSession(), newSubscriber);
        }
    }

    public void unSubscribe(String restSessionId) {
        restSubscribers.remove(restSessionId);
    }

    public void unSubscribe(WebSocketSession wsSession) {
        wsSubscribers.remove(wsSession);
    }

    public Subscriber getSubscriber(Session session) {
        return wsSubscribers.get(session);
    }

    public Subscriber getSubscriber(String restSessionId) {
        return restSubscribers.get(restSessionId);
    }

    public Collection<Subscriber> getWebSocketSubscribers() {
        return wsSubscribers.values();
    }
}
