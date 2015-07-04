package com.vdshb.spring_client.domain;

import com.vdshb.spring_client.domain.enums.ClientConnectionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
public class Subscriber {
    private ClientConnectionType connectionType;
    private long lastMessageId;
    private WebSocketSession wsSession;
    private String restSessionId;
}
