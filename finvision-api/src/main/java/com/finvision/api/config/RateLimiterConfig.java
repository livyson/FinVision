package com.finvision.api.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfig {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Bean
    public Map<String, Bucket> rateLimiters() {
        // Default rate limit: 100 requests per minute
        buckets.put("default", createNewBucket(100, Duration.ofMinutes(1)));
        
        // Auth endpoints: 5 requests per minute
        buckets.put("auth", createNewBucket(5, Duration.ofMinutes(1)));
        
        // Admin endpoints: 50 requests per minute
        buckets.put("admin", createNewBucket(50, Duration.ofMinutes(1)));
        
        return buckets;
    }

    private Bucket createNewBucket(int tokens, Duration duration) {
        Bandwidth limit = Bandwidth.classic(tokens, Refill.intervally(tokens, duration));
        return Bucket4j.builder().addLimit(limit).build();
    }

    public Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, k -> buckets.get("default"));
    }
} 