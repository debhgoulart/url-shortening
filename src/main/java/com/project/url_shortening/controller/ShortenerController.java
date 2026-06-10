package com.project.url_shortening.controller;

import com.project.url_shortening.domain.dto.ShortenUrlRequestDTO;
import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.dto.UrlStatsResponseDTO;
import com.project.url_shortening.domain.service.ShortenUrlService;
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
    public ResponseEntity<ShortenUrlResponseDTO> shortenUrl(@RequestBody ShortenUrlRequestDTO dto) {
        return ResponseEntity.ok(service.shortenUrl(dto.url()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortenUrlResponseDTO> getUrl(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUrl(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShortenUrlResponseDTO> updateUrl(@PathVariable Long id, @RequestBody ShortenUrlRequestDTO dto) {
        return ResponseEntity.ok(service.updateUrl(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUrl(@PathVariable Long id) {
        service.deleteUrl(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<UrlStatsResponseDTO> getUrlStats(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStats(id));
    }
}
