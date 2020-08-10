package com.crashcourse.config;

import com.crashcourse.dto.UserListDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.server}")
    private String kafkaServer;
    @Value("${kafka.group_id}")
    private String kafkaGroupId;

    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserListDto> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserListDto> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(false);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserListDto> consumerFactory() {
        JsonDeserializer deserializer = new JsonDeserializer(UserListDto.class);
        deserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory(consumerConfigs(), new StringDeserializer(), deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        return new ConcurrentKafkaListenerContainerFactory();
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }
}
