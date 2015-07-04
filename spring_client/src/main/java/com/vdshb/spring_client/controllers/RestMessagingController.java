package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.ChatTextMessage;
import com.vdshb.spring_client.service.MessageVault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/messaging/rest")
public class RestMessagingController {

    @Autowired
    MessageVault messageVault;

    @Autowired
    PushMessagesSender pushMessagesSender;

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody ChatTextMessage msg) throws IOException {
        System.out.println("======= received ========");
        System.out.println(msg);
        System.out.println("=========================");
        messageVault.addMessage(msg);
        pushMessagesSender.pushWebSocketMessagesToAllSubscribers();
    }

}
