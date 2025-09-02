package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.Map;
import com.example.converter.JsonAttributeConverter;

@Entity
@Table(name = "short_urls", indexes = {
        @Index(name = "idx_short_urls_short_key", columnList = "short_key")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_key", nullable = false, length = 128, unique = true)
    private String shortKey;

    @Column(name = "original_url", nullable = false, columnDefinition = "text")
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "clicks", nullable = false)
    private Long clicks;

    @Column(length = 512)
    private String description;

    @Convert(converter = JsonAttributeConverter.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;
}
