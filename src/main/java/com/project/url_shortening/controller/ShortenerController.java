package com.project.url_shortening.controller;

import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.service.ShortenUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten")
public class ShortenerController {

    private final ShortenUrlService service;

    public ShortenerController(ShortenUrlService service) {
        this.service = service;
    }

    @PostMapping("/{url}")
    public ResponseEntity<ShortenUrlResponseDTO> shortenUrl(@PathVariable String url) {
        return ResponseEntity.ok(service.shortenUrl(url));
    }
}
