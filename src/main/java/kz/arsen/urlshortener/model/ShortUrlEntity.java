package kz.arsen.urlshortener.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "short_url_seq", sequenceName = "short_url_seq", allocationSize = 1, initialValue = 10000)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int redirectCount;
}