package com.project.url_shortening.domain.service;

import com.project.url_shortening.domain.dto.ShortenUrlResponseDTO;
import com.project.url_shortening.domain.model.UrlShortener;
import com.project.url_shortening.domain.repository.UrlShortenerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ShortenUrlService {

    private final UrlShortenerRepository repository;

    public ShortenUrlService(UrlShortenerRepository repository) {
        this.repository = repository;
    }

    public ShortenUrlResponseDTO shortenUrl(String url) {
        LocalDateTime CurrentDate = LocalDateTime.now();
        String shortCode = generateShortUrl(url);

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setUrl(url);
        urlShortener.setShortCode(shortCode);
        urlShortener.setCreatedAt(CurrentDate);
        urlShortener.setUpdatedAt(CurrentDate);

        urlShortener = repository.save(urlShortener);
        return new  ShortenUrlResponseDTO(urlShortener);
    }

    private static String generateShortUrl(String url) {
        String characters = url.substring(url.lastIndexOf("/") + 1).replace("-", "");
        Random random = new Random();

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }

        return code.toString();
    }
}
