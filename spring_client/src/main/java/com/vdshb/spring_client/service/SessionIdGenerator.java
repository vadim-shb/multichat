package com.vdshb.spring_client.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class SessionIdGenerator {

    private AtomicLong lastId = new AtomicLong(-1L);

    public long generate() {
        return lastId.incrementAndGet();
    }

}
