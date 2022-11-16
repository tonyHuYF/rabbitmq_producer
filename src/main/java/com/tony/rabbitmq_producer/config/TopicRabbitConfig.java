package com.tony.rabbitmq_producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    public static final String man = "topic.man";
    public static final String woman = "topic.woman";

    @Bean
    public Queue firstQueue() {
        return new Queue(man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(woman);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    @Bean
    public Binding bindingWithTopicMan() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with("topic.man");
    }

    @Bean
    public Binding bindingWithTopicAll() {
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }
}
