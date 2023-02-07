package com.frombooktobook.frombooktobookbackend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;


@RequiredArgsConstructor
@Component
public class RedisUtil {
    private final StringRedisTemplate redisTemplate;

    public String getData(String key) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value, long durationMin) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMinutes(durationMin);
        valueOperations.set(key,value,expireDuration);
    }
}
