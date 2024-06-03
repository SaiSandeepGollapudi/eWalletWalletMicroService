package com.paypal.ewallet.wallet.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserActionConsumer {// we cannot just use kafkaTemplate to consume an event as we have to continuously keep
    //looking for event so we create a separate class

    Logger logger = LoggerFactory.getLogger(UserActionConsumer.class);
    @KafkaListener(topics="${kafka.topic.user-created}", groupId = "walletGrp")
    public void consumeUserCreated(String message)// message will receive the event
    {
        logger.info(String.format("Message received -> %s",message));
    }
}
