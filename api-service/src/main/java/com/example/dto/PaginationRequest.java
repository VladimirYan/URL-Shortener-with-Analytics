package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequest {
    private int page = 0;
    private int size = 20;
    private String sort = "createdAt,desc";
}
