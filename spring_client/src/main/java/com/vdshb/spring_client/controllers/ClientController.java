package com.vdshb.spring_client.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    @RequestMapping(value = "/send-text-message", method = RequestMethod.POST)
    public String sendTextMessage(@RequestBody String msg) {
        System.out.println("===============");
        System.out.println(msg);
        System.out.println("===============");
        return msg + "!!!";
    }

}
