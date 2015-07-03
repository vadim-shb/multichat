package com.vdshb.spring_client.service;

import com.vdshb.spring_client.domain.TextMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageVault {
    private List<TextMessage> messagesToDeliver = new ArrayList<>();

    public void clean() {
        messagesToDeliver.clear();
    }

    public void addMessage(TextMessage message) {
        if (message != null) messagesToDeliver.add(message);
    }

    public void addMessages(List<TextMessage> messages) {
        if (messages != null)
            messages.forEach(message -> addMessage(message));
    }

    public List<TextMessage> getMessagesToDeliver() {
        return messagesToDeliver;
    }
}
