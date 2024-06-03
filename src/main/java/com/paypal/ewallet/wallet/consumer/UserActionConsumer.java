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
    Logger logger = LoggerFactory.getLogger(UserActionConsumer.class);//SLF4J: The Simple Logging Facade for Java (SLF4J)
    // is a logging API that provides a simple abstraction over various logging frameworks. LoggerFactory: A factory class
    // provided by SLF4J to create Logger instances. Logger: An interface provided by SLF4J that defines methods for
    // logging messages at different levels (e.g., info, debug, error).
    // LoggerFactory.getLogger(UserActionConsumer.class): creates a logger instance named after the UserActionConsumer class.
    @Autowired
    WalletService walletService;

    @KafkaListener(topics="${kafka.topic.user-created}", groupId = "walletGrp")//@KafkaListener continuously listens for
    // messages on a specified topic. Handles the background threading and message polling, allowing the method to be
    // triggered automatically upon message arrival. Consumers in the same group share the messages from the topic,
    // providing load balancing.
    public void consumeUserCreated(String message)// message will receive the event, here we gave it as String but it can
    // be any type even User object, and we should use serializer and deserializer if we are using an object
    {
        logger.info(String.format("Message received -> %s",message));//Logged Output INFO  [UserActionConsumer] - Message
        // received -> 52. The String.format method is used to create a formatted log entry, making it clear which
        // message (user ID) was received.

        walletService.createWallet(Long.valueOf(message));// here there is a chance of error so we have logged that exception
        //in the walletService as createWallet is method in WalletService so it can handle it
    }
    @KafkaListener(topics="${kafka.topic.user-deleted}", groupId = "walletGrp")
    public void consumeUserDeleted(String message){

        logger.info(String.format("Message received -> %s",message));
        walletService.deleteWallet(Long.valueOf(message));
    }
}
