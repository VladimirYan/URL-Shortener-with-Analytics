package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.Map;
import com.example.converter.JsonAttributeConverter;

@Entity
@Table(name = "redirect_events", indexes = {
        @Index(name = "idx_redirect_events_short_key", columnList = "short_key"),
        @Index(name = "idx_redirect_events_occurred_at", columnList = "occurred_at")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedirectEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url_id")
    private Long shortUrlId;

    @Column(name = "short_key", length = 128)
    private String shortKey;

    @Column(name = "occurred_at")
    private OffsetDateTime occurredAt;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_agent", columnDefinition = "text")
    private String userAgent;

    private String referer;

    private String country;

    private String region;

    private String city;

    private Double lat;

    private Double lon;

    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> headers;

    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> params;

    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;
}
