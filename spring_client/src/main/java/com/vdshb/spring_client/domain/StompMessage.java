package com.vdshb.spring_client.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StompMessage {
    String messageId;
    ChatTextMessage message;
}
