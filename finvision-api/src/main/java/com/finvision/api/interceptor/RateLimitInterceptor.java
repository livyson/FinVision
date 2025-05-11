package com.finvision.api.interceptor;

import com.finvision.api.config.RateLimiterConfig;
import com.finvision.api.exception.RateLimitExceededException;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiterConfig rateLimiterConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String key = getRateLimitKey(request);
        Bucket bucket = rateLimiterConfig.resolveBucket(key);

        if (bucket.tryConsume(1)) {
            return true;
        }

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(bucket.getAvailableTokens()));
        throw new RateLimitExceededException("Rate limit exceeded. Try again later.");
    }

    private String getRateLimitKey(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth")) {
            return "auth";
        } else if (path.startsWith("/api/admin")) {
            return "admin";
        }
        return "default";
    }
} 