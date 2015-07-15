package com.vdshb.spring_client.service;

import com.vdshb.spring_client.domain.ChatTextMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MessageVault {
    private Map<Long, ChatTextMessage> messagesToDeliver = new HashMap<>();

    private AtomicLong lastId = new AtomicLong(-1L);

    public void addMessage(ChatTextMessage message) {
        if (message != null) messagesToDeliver.put(lastId.incrementAndGet(), message);
    }

    public MessagesToDeliver getMessagesToDeliver(long from) {
        long to = lastId.get();
        int quantity = to - from > 10000 ? 10000 : (int) (to - from);
        if (quantity < 0) quantity = 0;
        to = from + quantity;

        List<ChatTextMessage> messages = new ArrayList<>(quantity);
        for (long i = from + 1; i <= to; i++) {
            messages.add(messagesToDeliver.get(i));
        }
        return new MessagesToDeliver(messages, to);
    }

    public static class MessagesToDeliver {
        public List<ChatTextMessage> messages;
        public long lastId;

        public MessagesToDeliver(List<ChatTextMessage> messages, long lastId) {
            this.messages = messages;
            this.lastId = lastId;
        }
    }

    public long getLastMessageId() {
        return lastId.get();
    }
}
