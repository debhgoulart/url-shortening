package com.project.url_shortening.domain.service;

import com.project.url_shortening.domain.dto.ShortenUrlRequestDTO;
import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.dto.UrlStatsResponseDTO;
import com.project.url_shortening.domain.model.UrlShortener;
import com.project.url_shortening.domain.repository.UrlShortenerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ShortenUrlService {

    private final UrlShortenerRepository repository;

    public ShortenUrlService(UrlShortenerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ShortenUrlResponseDTO shortenUrl(String url) {
        LocalDateTime CurrentDate = LocalDateTime.now();
        String shortCode = generateShortUrl();

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setUrl(url);
        urlShortener.setShortCode(shortCode);
        urlShortener.setCreatedAt(CurrentDate);
        urlShortener.setUpdatedAt(CurrentDate);
        urlShortener.setAccessCount(0);

        urlShortener = repository.save(urlShortener);
        return new  ShortenUrlResponseDTO(urlShortener);
    }

    private String generateShortUrl() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder code = new StringBuilder();
        do {
            code.setLength(0);
            for (int i = 0; i < 6; i++) {
                code.append(chars.charAt(random.nextInt(chars.length())));
            }
        } while (repository.findByShortCode(code.toString()).isPresent());

        return code.toString();
    }

    public ShortenUrlResponseDTO getUrl(Long id) {
        UrlShortener url = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));
        incrementAccessCount(url);

        return new ShortenUrlResponseDTO(url);
    }

    private void incrementAccessCount(UrlShortener url) {
        url.setAccessCount(url.getAccessCount() + 1);
        repository.save(url);
    }

    @Transactional
    public ShortenUrlResponseDTO updateUrl(Long id, ShortenUrlRequestDTO newUrl) {
        UrlShortener url = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));

        url.setUrl(newUrl.url());
        url.setUpdatedAt(LocalDateTime.now());
        url.setShortCode(generateShortUrl());

        return new  ShortenUrlResponseDTO(url);
    }

    @Transactional
    public void deleteUrl(Long id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));
        repository.deleteById(id);
    }

    public UrlStatsResponseDTO getStats(Long id) {
        UrlShortener url = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not exist with id: " + id));
        return new UrlStatsResponseDTO(url);
    }

    public String redirect(String shortCode) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new RuntimeException("URL not found."));
        incrementAccessCount(url);

        return url.getUrl();
    }
}
