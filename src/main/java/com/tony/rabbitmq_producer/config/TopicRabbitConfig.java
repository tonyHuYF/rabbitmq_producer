package com.tony.rabbitmq_producer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    public static final String man = "topic.man";
    public static final String woman = "topic.woman";
    public static final String ttl = "topic.ttl";
    public static final String test_dlx = "test_dlx.message";
    public static final String dlx = "dlx.message";

    @Bean
    public Queue firstQueue() {
        return new Queue(man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(woman);
    }

    @Bean
    public Queue thirdQueue() {
        //队列消息统一过期100s
        return QueueBuilder.durable(ttl).ttl(100000).build();
    }

    @Bean
    Queue fourthQueue() {
        //测试死信队列的队列，绑定死信交换机，死信队列路由键
        return QueueBuilder.durable(test_dlx).ttl(10000).deadLetterExchange("dlxExchange")
                .deadLetterRoutingKey("dlx.test").maxLength(10).build();
    }

    @Bean
    Queue fifthQueue() {
        //创建死信队列
        return QueueBuilder.durable(dlx).build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    @Bean
    public TopicExchange testDlxExchange() {
        //创建死信交换机
        return new TopicExchange("testDlxExchange", true, false);
    }


    @Bean
    public TopicExchange dlxExchange() {
        //创建死信交换机
        return new TopicExchange("dlxExchange", true, false);
    }


    @Bean
    public Binding bindingWithTopicMan() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with("topic.man");
    }

    @Bean
    public Binding bindingWithTopicAll() {
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }

    @Bean
    public Binding bindingWithTopicTTL() {
        return BindingBuilder.bind(thirdQueue()).to(topicExchange()).with("topic.ttl");
    }

    @Bean
    public Binding bindingWithTestDlx(){
        return BindingBuilder.bind(fourthQueue()).to(testDlxExchange()).with("test.#");
    }


    @Bean
    public Binding bindingWithDlx() {
        //绑定死信队列到死信交换机
        return BindingBuilder.bind(fifthQueue()).to(dlxExchange()).with("dlx.#");
    }

}
