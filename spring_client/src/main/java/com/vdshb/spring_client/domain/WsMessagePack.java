package com.vdshb.spring_client.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WsMessagePack {
    String command;
    List<ChatTextMessage> messages;
}
