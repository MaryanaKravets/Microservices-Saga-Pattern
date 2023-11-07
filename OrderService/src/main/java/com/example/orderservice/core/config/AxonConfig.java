//package com.example.orderservice.core.config;
//
//import com.rabbitmq.client.Channel;
//import org.axonframework.common.jpa.EntityManagerProvider;
//import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
//import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
//import org.axonframework.springboot.util.jpa.ContainerManagedEntityManagerProvider;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.Transactional;
//
//@Configuration
//public class AxonConfig {
//    private static final String AXON_QUEUE = "addressQueue";
//
//    @Bean
//    public EntityManagerProvider entityManagerProvider() {
//        return new ContainerManagedEntityManagerProvider();
//    }
//
//    // Queue
//
//    @Bean
//    @Qualifier("axonAmqp")
//    public Queue queue() {
//        return QueueBuilder.durable(AXON_QUEUE).build();
//    }
//
//    // Listen to RabbitMQ messages
//
//    @Bean
//    public SpringAMQPMessageSource axonQueueMessageSource(AMQPMessageConverter messageConverter) {
//        return new SpringAMQPMessageSource(messageConverter) {
//
//            @RabbitListener(queues = AXON_QUEUE)
//            @Transactional
//            @Override
//            public void onMessage(Message message, Channel channel) {
//                //log.debug("[AMQP] Processing message: {}, on channel: {}", message, channel);
//                super.onMessage(message, channel);
//            }
//        };
//    }
//}
