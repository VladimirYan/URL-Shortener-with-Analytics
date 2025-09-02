package com.example.service;

import com.example.dto.CreateShortRequest;
import com.example.entity.ShortUrl;
import com.example.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {

    private static final String REDIS_PREFIX = "short:";
    private static final int KEY_LENGTH = 8;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final ShortUrlRepository repository;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public ShortUrl createShortUrl(CreateShortRequest request) {
        String key = request.getCustomAlias();
        if (key != null && !key.isBlank()) {
            if (repository.existsByShortKey(key)) {
                throw new IllegalArgumentException("Custom alias already taken");
            }
        } else {
            key = generateUniqueKey();
        }

        ShortUrl entity = ShortUrl.builder()
                .originalUrl(request.getUrl())
                .shortKey(key)
                .createdAt(OffsetDateTime.now())
                .expiresAt(request.getExpiresAt())
                .ownerId(request.getOwnerId() != null ? parseOwnerId(request.getOwnerId()) : null)
                .clicks(0L)
                .metadata(request.getMetadata())
                .build();
        ShortUrl saved = repository.save(entity);

        String redisKey = REDIS_PREFIX + saved.getShortKey();
        stringRedisTemplate.opsForValue().set(redisKey, saved.getOriginalUrl());

        return saved;
    }

    @Override
    public ShortUrl findByShortKey(String shortKey) {
        Optional<ShortUrl> maybe = repository.findByShortKey(shortKey);
        return maybe.orElse(null);
    }

    private String generateUniqueKey() {
        String key;
        int attempts = 0;
        do {
            key = generateRandomKey(KEY_LENGTH);
            attempts++;
            if (attempts > 10) {
                throw new IllegalStateException("Unable to generate unique key");
            }
        } while (repository.existsByShortKey(key));
        return key;
    }

    private static String generateRandomKey(int length) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private Long parseOwnerId(String ownerId) {
        try {
            return Long.parseLong(ownerId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}