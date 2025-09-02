package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateShortRequest {
    @NotBlank
    private String url;
    private String ownerId;
    private OffsetDateTime expiresAt;
    private String customAlias;
    private Map<String, Object> metadata;
}
