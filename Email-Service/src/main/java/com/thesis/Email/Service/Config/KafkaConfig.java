package com.thesis.Email.Service.Config;

import com.thesis.Email.Service.Model.MailOrder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.mail.SimpleMailMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String kafkaHost;
    @Bean
    public ConsumerFactory<String, MailOrder> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaHost);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "sendmail");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "MailOrder:com.thesis.Email.Service.Model.MailOrder");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @LoadBalanced
    public ConcurrentKafkaListenerContainerFactory<String, MailOrder>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, MailOrder> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
