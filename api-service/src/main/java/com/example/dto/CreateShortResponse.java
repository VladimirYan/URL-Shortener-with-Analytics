package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateShortResponse {
    private String shortKey;
    private String shortUrl;
    private String message;
}
