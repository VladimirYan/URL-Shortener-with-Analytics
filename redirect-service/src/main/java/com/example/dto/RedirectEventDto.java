package com.example.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedirectEventDto {
    private String shortKey;
    private Long shortUrlId;
    private OffsetDateTime occurredAt;
    private String originalUrl;
    private String ip;
    private String userAgent;
    private String referer;
}
