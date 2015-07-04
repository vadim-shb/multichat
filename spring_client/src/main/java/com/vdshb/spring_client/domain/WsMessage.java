package com.vdshb.spring_client.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WsMessage {
    String command;
    String messageId;
    int code;
    ChatTextMessage message;
}
