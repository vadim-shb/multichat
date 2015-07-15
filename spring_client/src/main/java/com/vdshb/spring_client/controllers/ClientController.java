package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.ChatTextMessage;
import com.vdshb.spring_client.domain.Subscriber;
import com.vdshb.spring_client.domain.enums.ClientConnectionType;
import com.vdshb.spring_client.service.InterServerMessaging;
import com.vdshb.spring_client.service.MessageVault;
import com.vdshb.spring_client.service.SessionIdGenerator;
import com.vdshb.spring_client.service.SubscribersVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    InterServerMessaging interServerMessaging;
    @Autowired
    MessageVault messageVault;
    @Autowired
    SessionIdGenerator sessionIdGenerator;
    @Autowired
    SubscribersVault subscribersVault;

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connectToChat() {
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setConnectionType(ClientConnectionType.REST);
        newSubscriber.setLastMessageId(-1);
        newSubscriber.setRestSessionId(String.valueOf(sessionIdGenerator.generate()));
        subscribersVault.subscribe(newSubscriber);

        String charSessionId = newSubscriber.getRestSessionId();
        return "{\"charSessionId\": \"" + charSessionId + "\"}";
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
    public void connectToChat(@RequestParam("chatSessionId") String sessionId) {
        subscribersVault.unSubscribe(sessionId);
    }

    @RequestMapping(value = "/send-text-message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody ChatTextMessage msg) {
        msg.setTime(LocalDateTime.now());
        interServerMessaging.sendTextMessage(msg);
        messageVault.addMessage(msg);
    }

    @RequestMapping(value = "/receive-text-messages", method = RequestMethod.GET)
    public List<ChatTextMessage> receiveTextMessage(@RequestParam("chatSessionId") String sessionId) {
        Subscriber subscriber = subscribersVault.getSubscriber(sessionId);
        MessageVault.MessagesToDeliver messagesToDeliver = messageVault.getMessagesToDeliver(subscriber.getLastMessageId());
        subscriber.setLastMessageId(messagesToDeliver.lastId);
        return messagesToDeliver.messages;
    }

}
