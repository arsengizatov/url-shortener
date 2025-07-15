package kz.arsen.urlshortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import kz.arsen.urlshortener.dto.CreateShortUrlRequest;
import kz.arsen.urlshortener.dto.ShortUrlResponse;
import kz.arsen.urlshortener.dto.ShortUrlStatsResponse;
import kz.arsen.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    // 1. POST /shorten
    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody CreateShortUrlRequest request) {
        ShortUrlResponse response = urlShortenerService.createShortUrl(request);
        return ResponseEntity.ok(response);
    }

    // 2. GET /{shortCode}
    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
    }

    // 3. GET /{shortCode}/stats
    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<ShortUrlStatsResponse> getStats(@PathVariable String shortCode) {
        return ResponseEntity.ok(urlShortenerService.getStats(shortCode));
    }
}