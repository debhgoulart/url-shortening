package com.project.url_shortening.domain.dto;

import com.project.url_shortening.domain.model.UrlShortener;

import java.time.LocalDateTime;

public record ShortenUrlResponseDTO(
        Long id,
        String url,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ShortenUrlResponseDTO(UrlShortener model) {
        this(
                model.getId(),
                model.getUrl(),
                model.getShortCode(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}
