package com.example.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<T> content;
}
