package com.project.url_shortening.domain.repository;

import com.project.url_shortening.domain.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {
    Optional<UrlShortener> findByShortCode(String shortCode);

    Long findIdByShortCode(String shortCode);
}
