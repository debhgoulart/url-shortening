package com.project.url_shortening.domain.service;

import com.project.url_shortening.domain.dto.ShortenUrlRequestDTO;
import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.dto.UrlStatsResponseDTO;
import com.project.url_shortening.domain.model.UrlShortener;
import com.project.url_shortening.domain.repository.UrlShortenerRepository;
import com.project.url_shortening.exception.UrlNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ShortenUrlResponseDTO getUrl(String shortCode) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL with shortCode '" + shortCode + "' not found"));
        incrementAccessCount(url);

        return new ShortenUrlResponseDTO(url);
    }

    private void incrementAccessCount(UrlShortener url) {
        url.setAccessCount(url.getAccessCount() + 1);
        repository.save(url);
    }

    @Transactional
    public ShortenUrlResponseDTO updateUrl(String shortCode, ShortenUrlRequestDTO newUrl) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL with shortCode '" + shortCode + "' not found"));

        url.setUrl(newUrl.url());
        url.setUpdatedAt(LocalDateTime.now());
        url.setShortCode(generateShortUrl());
        repository.save(url);

        return new  ShortenUrlResponseDTO(url);
    }

    @Transactional
    public void deleteUrl(String shortCode) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL with shortCode '" + shortCode + "' not found"));

        repository.delete(url);
    }

    public UrlStatsResponseDTO getStats(String shortCode) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL with shortCode '" + shortCode + "' not found"));
        return new UrlStatsResponseDTO(url);
    }

    public String redirect(String shortCode) {
        UrlShortener url = repository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("URL not found."));
        incrementAccessCount(url);

        return url.getUrl();
    }
}
