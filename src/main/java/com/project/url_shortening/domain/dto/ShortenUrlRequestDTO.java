package com.project.url_shortening.domain.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortenUrlRequestDTO(
        @NotBlank(message = "URL cannot be empty")
        @URL(message = "Invalid URL format")
        String url
) {
}
