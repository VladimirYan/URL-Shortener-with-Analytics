package com.example.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedirectEventDto {
    private String shortKey;
    private Long shortUrlId;
    private OffsetDateTime occurredAt;
    private String ip;
    private String userAgent;
    private String referer;
    private String country;
    private String region;
    private String city;
    private Double lat;
    private Double lon;
    private Map<String, Object> headers;
    private Map<String, Object> params;
}