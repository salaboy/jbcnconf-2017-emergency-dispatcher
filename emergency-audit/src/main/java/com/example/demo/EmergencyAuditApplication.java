package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringBootApplication
public class EmergencyAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyAuditApplication.class, args);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("audit-exchange");
    }

    @Bean(name = "audit-binding")
    Binding bindingExecutor(@Qualifier(value = "audit-queue" )Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(System.getProperty("QUEUE_NAME", "audit-queue"));
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AuditReciever receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean(name = "audit-queue")
    Queue auditQueue() {
        return new Queue(System.getProperty("QUEUE_NAME", "audit-queue"), false);
    }



    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(System.getProperty("QUEUE_NAME", "audit-queue"));
        container.setMessageListener(listenerAdapter);
        return container;
    }


}
