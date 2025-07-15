package kz.arsen.urlshortener.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private final StringRedisTemplate redisTemplate;

    public void cacheOriginalUrl(String shortCode, String originalUrl) {
        redisTemplate.opsForValue().set(shortCode, originalUrl, Duration.ofHours(1)); // TTL = 1ч
    }

    public String getOriginalUrl(String shortCode) {
        return redisTemplate.opsForValue().get(shortCode);
    }
}