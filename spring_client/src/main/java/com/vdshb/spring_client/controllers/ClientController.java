package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.TextMessage;
import com.vdshb.spring_client.service.MessageVault;
import com.vdshb.spring_client.service.RestMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    RestMessaging restMessaging;

    @Autowired
    MessageVault messageVault;

    @RequestMapping(value = "/send-text-message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody TextMessage msg) {
        msg.setTime(LocalDateTime.now());
        restMessaging.sendTextMessage(msg);
    }

    @RequestMapping(value = "/receive-text-message", method = RequestMethod.GET)
    public List<TextMessage> receiveTextMessage() {
        List<TextMessage> retval = new ArrayList<>();
        if (messageVault.getMessagesToDeliver().size() > 0) {
            retval.addAll(messageVault.getMessagesToDeliver());
            messageVault.clean();
        }
        return retval;
    }

}
