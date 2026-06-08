package com.project.url_shortening.domain.repository;

import com.project.url_shortening.domain.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {
}
