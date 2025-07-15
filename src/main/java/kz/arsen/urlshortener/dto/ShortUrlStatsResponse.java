package kz.arsen.urlshortener.dto;

import java.time.LocalDateTime;

public record ShortUrlStatsResponse(
    String shortCode,
    String originalUrl,
    int redirectCount,
    LocalDateTime createdAt
) {}