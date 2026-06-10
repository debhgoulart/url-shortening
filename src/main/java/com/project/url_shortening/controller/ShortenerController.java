package com.project.url_shortening.controller;

import com.project.url_shortening.domain.dto.ShortenUrlRequestDTO;
import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.dto.UrlStatsResponseDTO;
import com.project.url_shortening.domain.service.ShortenUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shorten")
public class ShortenerController {

    private final ShortenUrlService service;

    public ShortenerController(ShortenUrlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShortenUrlResponseDTO> shortenUrl(@Valid @RequestBody ShortenUrlRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.shortenUrl(dto.url()));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDTO> getUrl(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.getUrl(shortCode));
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponseDTO> updateUrl(@PathVariable String shortCode, @Valid @RequestBody ShortenUrlRequestDTO dto) {
        return ResponseEntity.ok(service.updateUrl(shortCode, dto));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortCode) {
        service.deleteUrl(shortCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlStatsResponseDTO> getUrlStats(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.getStats(shortCode));
    }
}
