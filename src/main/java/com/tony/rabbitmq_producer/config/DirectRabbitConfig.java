package com.tony.rabbitmq_producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue testDirectQueue(){
        return new Queue("testDirectQueue",true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange",true,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(testDirectQueue()).to(directExchange()).with("testDirectRouting");
    }


}
