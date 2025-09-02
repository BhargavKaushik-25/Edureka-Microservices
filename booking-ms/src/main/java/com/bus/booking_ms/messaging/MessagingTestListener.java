package com.bus.booking_ms.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MessagingTestListener {
    private static final Logger log = LoggerFactory.getLogger(MessagingTestListener.class);

    @JmsListener(destination = "booking.test")
    public void receiveTestMessage(String payload) {
        log.info("Received JMS message from 'booking.test' -> {}", payload);
    }
}
