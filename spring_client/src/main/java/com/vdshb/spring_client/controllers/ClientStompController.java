package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.ChatTextMessage;
import com.vdshb.spring_client.service.MessageVault;
import com.vdshb.spring_client.service.RestMessaging;
import com.vdshb.spring_client.service.SessionIdGenerator;
import com.vdshb.spring_client.service.SubscribersVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ClientStompController {

    @Autowired
    RestMessaging restMessaging;
    @Autowired
    MessageVault messageVault;
    @Autowired
    SessionIdGenerator sessionIdGenerator;
    @Autowired
    SubscribersVault subscribersVault;

    //    @RequestMapping(value = "/connect", method = RequestMethod.GET)
//    public String connectToChat() {
//        Subscriber newSubscriber = new Subscriber();
//        newSubscriber.setConnectionType(ClientConnectionType.REST);
//        newSubscriber.setLastMessageId(-1);
//        newSubscriber.setRestSessionId(String.valueOf(sessionIdGenerator.generate()));
//        subscribersVault.subscribe(newSubscriber);
//
//        String charSessionId = newSubscriber.getRestSessionId();
//        return "{\"charSessionId\": \"" + charSessionId + "\"}";
//    }
//
//    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
//    public void connectToChat(@RequestParam("chatSessionId") String sessionId) {
//        subscribersVault.unSubscribe(sessionId);
//    }
//
    @MessageMapping("/send-text-message")
    @SendTo("/topic/messages")
    public ChatTextMessage sendTextMessage(ChatTextMessage msg) {
        msg.setTime(LocalDateTime.now());
        restMessaging.sendTextMessage(msg);
        messageVault.addMessage(msg);
        return msg;
    }

//    @MessageMapping("/receive-text-messages")
//    @SendToUser
//    public List<ChatTextMessage> receiveTextMessage(@RequestParam("chatSessionId") String sessionId) {
//        Subscriber subscriber = subscribersVault.getSubscriber(sessionId);
//        MessageVault.MessagesToDeliver messagesToDeliver = messageVault.getMessagesToDeliver(subscriber.getLastMessageId());
//        subscriber.setLastMessageId(messagesToDeliver.lastId);
//        return messagesToDeliver.messages;
//    }

}
