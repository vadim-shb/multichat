package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.ChatTextMessage;
import com.vdshb.spring_client.domain.Subscriber;
import com.vdshb.spring_client.domain.enums.ClientConnectionType;
import com.vdshb.spring_client.service.MessageVault;
import com.vdshb.spring_client.service.RestMessaging;
import com.vdshb.spring_client.service.SessionIdGenerator;
import com.vdshb.spring_client.service.SubscribersVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    RestMessaging restMessaging;

    @Autowired
    MessageVault messageVault;
    @Autowired
    SessionIdGenerator sessionIdGenerator;
    @Autowired
    SubscribersVault subscribersVault;

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public void connectToChat(HttpServletResponse response) {
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setConnectionType(ClientConnectionType.REST);
        newSubscriber.setLastMessageId(-1);
        newSubscriber.setRestSessionId(String.valueOf(sessionIdGenerator.generate()));
        subscribersVault.subscribe(newSubscriber);

        response.addCookie(new Cookie("ChatSessionId", newSubscriber.getRestSessionId()));
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
    public void connectToChat(@CookieValue("ChatSessionId") String sessionId, HttpServletResponse response) {
        subscribersVault.unSubscribe(sessionId);

        Cookie chatSessionCookie = new Cookie("ChatSessionId", null);
        chatSessionCookie.setHttpOnly(true);
        chatSessionCookie.setMaxAge(1000 * 60 * 60 * 24);
        response.addCookie(chatSessionCookie);
    }

    @RequestMapping(value = "/send-text-message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody ChatTextMessage msg) {
        msg.setTime(LocalDateTime.now());
        restMessaging.sendTextMessage(msg);
        messageVault.addMessage(msg);
    }

    @RequestMapping(value = "/receive-text-messages", method = RequestMethod.GET)
    public List<ChatTextMessage> receiveTextMessage(@CookieValue("ChatSessionId") String sessionId) {
        Subscriber subscriber = subscribersVault.getSubscriber(sessionId);
        MessageVault.MessagesToDeliver messagesToDeliver = messageVault.getMessagesToDeliver(subscriber.getLastMessageId());
        subscriber.setLastMessageId(messagesToDeliver.lastId);
        return messagesToDeliver.messages;
    }

}
