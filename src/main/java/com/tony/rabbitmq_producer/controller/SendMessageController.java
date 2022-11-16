package com.tony.rabbitmq_producer.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sendMsg")
public class SendMessageController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/direct")
    public String sendDirect() {
        String messageId = UUID.randomUUID().toString();
        String messageData = "hello direct message";
        String createTime = DateUtil.now();
        Map<String, String> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("directExchange", "testDirectRouting", map);
        return "ok";
    }

    @GetMapping("/fanout")
    public String sendFanout() {
        String messageId = UUID.randomUUID().toString();
        String messageData = "hello fanout message";
        String createTime = DateUtil.now();
        Map<String, String> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }


    @GetMapping("/topic/1")
    public String sendTopic1() {
        String messageId = UUID.randomUUID().toString();
        String messageData = "hello topic message : topic.man";
        String createTime = DateUtil.now();
        Map<String, String> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("topicExchange", "topic.man", map);
        return "ok";
    }

    @GetMapping("/topic/2")
    public String sendTopic2() {
        String messageId = UUID.randomUUID().toString();
        String messageData = "hello topic message : topic.woman";
        String createTime = DateUtil.now();
        Map<String, String> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", map);
        return "ok";
    }
}
