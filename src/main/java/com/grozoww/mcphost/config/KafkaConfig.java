package com.grozoww.mcphost.config;

import com.grozoww.mcphost.model.KafkaChatMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String KAFKA_GROUP_ID_CONFIG = "chat-group";

    @Value("${kafka.bootstrap-servers}")
    private String kafkaUrl;

    @Bean
    public ConsumerFactory<String, KafkaChatMessage> kafkaChatMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonSerializer.TYPE_MAPPINGS,
                "chatMsg:com.skyscanner.mcphost.model.KafkaChatMessage");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(KafkaChatMessage.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaChatMessage> kafkaChatMessageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaChatMessageConsumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, KafkaChatMessage> kafkaChatMessageProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "chatMsg:com.skyscanner.mcphost.model.KafkaChatMessage");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, KafkaChatMessage> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaChatMessageProducerFactory());
    }
}
