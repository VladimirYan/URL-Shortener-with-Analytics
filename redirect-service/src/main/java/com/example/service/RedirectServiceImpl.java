package com.example.service;

import com.example.dto.RedirectEventDto;
import com.example.entity.ShortUrl;
import com.example.repository.ShortUrlRepository;
import com.example.service.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedirectServiceImpl implements RedirectService {

    private static final String REDIS_PREFIX = "short:";

    private final StringRedisTemplate stringRedisTemplate;
    private final ShortUrlRepository shortUrlRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String redirectTopic = "redirects";

    @Override
    @Transactional(readOnly = true)
    public String resolveLongUrl(String shortKey) {
        String redisKey = REDIS_PREFIX + shortKey;
        String original = stringRedisTemplate.opsForValue().get(redisKey);
        if (original != null) {
            publishEvent(shortKey, original);
            return original;
        }

        ShortUrl su = shortUrlRepository.findByShortKey(shortKey).orElse(null);
        if (su == null) return null;

        if (su.getExpiresAt() != null && su.getExpiresAt().isBefore(OffsetDateTime.now())) {
            return null;
        }

        original = su.getOriginalUrl();

        if (su.getExpiresAt() != null) {
            long ttlSeconds = su.getExpiresAt().toEpochSecond() - OffsetDateTime.now().toEpochSecond();
            if (ttlSeconds > 0) {
                stringRedisTemplate.opsForValue().set(redisKey, original, ttlSeconds, TimeUnit.SECONDS);
            }
        } else {
            stringRedisTemplate.opsForValue().set(redisKey, original);
        }

        publishEvent(shortKey, original);

        return original;
    }

    private void publishEvent(String shortKey, String originalUrl) {
        RedirectEventDto event = RedirectEventDto.builder()
                .shortKey(shortKey)
                .occurredAt(OffsetDateTime.now())
                .originalUrl(originalUrl)
                .build();
        try {
            kafkaTemplate.send(redirectTopic, shortKey, event);
        } catch (Exception e) {
        }
    }
}
