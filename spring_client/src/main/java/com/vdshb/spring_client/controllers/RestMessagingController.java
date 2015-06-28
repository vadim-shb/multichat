package com.vdshb.spring_client.controllers;

import com.vdshb.spring_client.domain.TextMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messaging/rest")
public class RestMessagingController {

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public void sendTextMessage(@RequestBody TextMessage msg) {
        System.out.println("======= received========");
        System.out.println(msg);
        System.out.println("========================");
    }

}
