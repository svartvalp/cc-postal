package com.crashcourse.config;

import com.crashcourse.dto.UserListDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.max_block}")
    private String maxBlock;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap();
        props.put("bootstrap.servers", kafkaServer);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlock);
        return props;
    }

    @Bean
    public ProducerFactory<String, UserListDto> producerFactory() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory(producerConfigs(), new StringSerializer(), new JsonSerializer());
    }

    @Bean
    public KafkaTemplate<String, UserListDto> kafkaTemplate() {
        KafkaTemplate<String, UserListDto> template = new KafkaTemplate(producerFactory());
        return template;
    }
}
