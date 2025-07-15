package kz.arsen.urlshortener.service;

import kz.arsen.urlshortener.service.cache.RedisCacheService;
import kz.arsen.urlshortener.dto.CreateShortUrlRequest;
import kz.arsen.urlshortener.dto.ShortUrlResponse;
import kz.arsen.urlshortener.dto.ShortUrlStatsResponse;
import kz.arsen.urlshortener.model.ShortUrlEntity;
import kz.arsen.urlshortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final RedisCacheService redisCacheService;
    private final Base62Encoder encoder;

    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
        // Step 1 — save original URL
        ShortUrlEntity saved = repository.save(ShortUrlEntity.builder()
                .originalUrl(request.originalUrl())
                .createdAt(LocalDateTime.now())
                .redirectCount(0)
                .shortCode("temp")
                .build());

        // Step 2 — encode ID
        String code = encoder.encodeId(saved.getId());
        saved.setShortCode(code);
        repository.save(saved);

        return new ShortUrlResponse(code, "http://localhost:8080/" + code);
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        String cached = redisCacheService.getOriginalUrl(shortCode);
        if (cached != null) {
            log.info("Cache hit for {}", shortCode);
            return cached;
        }

        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found"));

        entity.setRedirectCount(entity.getRedirectCount() + 1);
        redisCacheService.cacheOriginalUrl(shortCode, entity.getOriginalUrl());

        return entity.getOriginalUrl();
    }

    public ShortUrlStatsResponse getStats(String shortCode) {
        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found"));

        return new ShortUrlStatsResponse(
                entity.getShortCode(),
                entity.getOriginalUrl(),
                entity.getRedirectCount(),
                entity.getCreatedAt()
        );
    }
}