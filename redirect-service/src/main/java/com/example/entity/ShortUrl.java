package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "short_urls", indexes = {@Index(name = "idx_short_urls_short_key", columnList = "short_key")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_key", nullable = false, unique = true)
    private String shortKey;

    @Column(name = "original_url", nullable = false, columnDefinition = "text")
    private String originalUrl;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "clicks")
    private Long clicks;
}
