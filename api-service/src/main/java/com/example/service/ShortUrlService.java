package com.example.service;

import com.example.dto.CreateShortRequest;
import com.example.entity.ShortUrl;

public interface ShortUrlService {
    ShortUrl createShortUrl(CreateShortRequest request);
    ShortUrl findByShortKey(String shortKey);
}
