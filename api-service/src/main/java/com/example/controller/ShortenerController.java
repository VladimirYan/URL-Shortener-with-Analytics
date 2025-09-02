package com.example.controller;

import com.example.dto.CreateShortRequest;
import com.example.dto.CreateShortResponse;
import com.example.entity.ShortUrl;
import com.example.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shortener")
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortUrlService service;

    @Value("${app.shortener.base-url:http://localhost:8080}")
    private String baseUrl;

    @PostMapping
    public ResponseEntity<CreateShortResponse> create(@Valid @RequestBody CreateShortRequest request) {
        ShortUrl created = service.createShortUrl(request);
        String shortUrl = String.format("%s/r/%s", baseUrl.replaceAll("/$", ""), created.getShortKey());
        CreateShortResponse resp = CreateShortResponse.builder()
                .shortKey(created.getShortKey())
                .shortUrl(shortUrl)
                .message("created")
                .build();
        return ResponseEntity.ok(resp);
    }
}
