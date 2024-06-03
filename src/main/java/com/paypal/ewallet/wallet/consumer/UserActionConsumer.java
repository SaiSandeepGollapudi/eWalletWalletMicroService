package com.paypal.ewallet.wallet.consumer;

import com.paypal.ewallet.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserActionConsumer {// we cannot just use kafkaTemplate to consume an event as we have to continuously keep
    //listening to incoming messages so we create a separate class

    Logger logger = LoggerFactory.getLogger(UserActionConsumer.class);

    @Autowired
    WalletService walletService;

    @KafkaListener(topics="${kafka.topic.user-created}", groupId = "walletGrp")//@KafkaListener continuously listens for
    // messages on a specified topic. Handles the background threading and message polling, allowing the method to be
    // triggered automatically upon message arrival. Consumers in the same group share the messages from the topic,
    // providing load balancing.
    public void consumeUserCreated(String message)// message will receive the event, here we gave it as String but it can
    // be any type even User object, and we should use serializer and deserializer if we are using an object
    {
        logger.info(String.format("Message received -> %s",message));
        walletService.createWallet(Long.valueOf(message));// here there is a chance of error so we have log that exception
        //in the service as createWallet is method in WalletService so it can handle it
    }

}
