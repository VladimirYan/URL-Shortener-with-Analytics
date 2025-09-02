package com.example.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrlDto {
    private Long id;
    private String shortKey;
    private String originalUrl;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
    private Long clicks;
    private String ownerId;
    private Map<String, Object> metadata;
}
