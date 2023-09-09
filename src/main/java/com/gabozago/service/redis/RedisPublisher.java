package com.gabozago.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    //inject a bean named "chatRedisTemplate" into the redisTemplate field.
    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate<String, Object> redisTemplate;

    //argument should be PublishMessage
    public void publish(ChannelTopic topic, String message){
        redisTemplate.convertAndSend(topic.getTopic(),message);
    }
}
