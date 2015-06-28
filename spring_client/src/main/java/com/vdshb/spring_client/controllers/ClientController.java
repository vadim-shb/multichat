package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.TextMessage;
import com.vdshb.spring_client.service.RestMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    RestMessaging restMessaging;

    @RequestMapping(value = "/send-text-message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody TextMessage msg) {
        System.out.println("===============");
        System.out.println(msg);
        System.out.println("===============");
        restMessaging.sendTextMessage(msg);
    }

}
