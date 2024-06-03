package com.paypal.ewallet.wallet.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public ConsumerFactory<String,String> consumerFactory(){//ConsumerFactory  is responsible for creating kafka consumer
        //instances. The DefaultKafkaConsumerFactory is provided with consumer configuration map created in the consumer
        // config method. This factory will provide kafka consumers that use the configurations specified.
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    private Map<String, Object> consumerConfig() {//this method returns a Map<String, Object> containing configuration
        // settings for kafka consumer

        Map<String, Object> configs= new HashMap<>();// initializes an empty HashMap configs, that will be used to
        // store the configuration settings for a Kafka consumer.

        // Set the bootstrap servers property to connect to the Kafka cluster
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//ConsumerConfig is a class provided by the Apache Kafka client library. It contains a set of configuration keys that
// can be used to configure Kafka consumers. These keys are used to set various properties required for the consumer
// to function correctly like BOOTSTRAP_SERVERS_CONFIG, .KEY_DESERIALIZER_CLASS_CONFIG etc

         // Set the key deserializer class property
        // This tells Kafka to use StringDeserializer to deserialize keys
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Set the value deserializer class property
        // This tells Kafka to use StringDeserializer to deserialize values
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return configs;
    }

}
