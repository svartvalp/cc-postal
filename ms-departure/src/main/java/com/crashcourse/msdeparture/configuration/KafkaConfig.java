package com.crashcourse.msdeparture.configuration;

import com.crashcourse.msdeparture.dto.DepartureDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Slf4j
@Configuration
public class KafkaConfig {

    @Value("${kafka.bootstrap.server}")
    private String bootstrapServerUrl;

    @Value("{kafka.group.id}")
    private String groupId;

    @Value("${kafka.topic.departure.result}")
    private String departureInTopic;

    @Value("${kafka.topic.departure.compute}")
    private String departureOutTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic departureInTopic() {
        return new NewTopic(departureInTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic departureOutTopic() {
        return new NewTopic(departureOutTopic, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, DepartureDto> departureDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig(), new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, DepartureDto> departureDtoKafkaTemplate(ProducerFactory<String, DepartureDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, DepartureDto> departureDtoConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(DepartureDto.class).trustedPackages("*").ignoreTypeHeaders());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DepartureDto> departureDtoKafkaListenerContainerFactory(ConsumerFactory<String, DepartureDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, DepartureDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl);
        config.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 6000);
        return config;
    }

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }
}
