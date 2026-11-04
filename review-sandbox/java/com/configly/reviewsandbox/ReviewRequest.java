package com.configly.reviewsandbox;

import java.util.Locale;
import java.util.Map;

/** Inputs are examples only; they contain no production tenant identifiers. */
public record ReviewRequest(
        String requestId,
        String tenantId,
        String method,
        String path,
        String region,
        Map<String, String> headers) {

    public ReviewRequest {
        if (requestId == null || requestId.isBlank()) {
            throw new IllegalArgumentException("Request id is required");
        }
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("Tenant id is required");
        }
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("Method is required");
        }
        if (path == null || !path.startsWith("/")) {
            throw new IllegalArgumentException("Path must be absolute");
        }
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("Region is required");
        }
        method = method.toUpperCase(Locale.ROOT);
        headers = Map.copyOf(headers);
    }

    public String stableKey() {
        return tenantId + ":" + requestId;
    }

    public boolean isReadOnly() {
        return method.equals("GET") || method.equals("HEAD");
    }

    public boolean hasIdempotencyKey() {
        return headers.entrySet().stream()
                .anyMatch(entry -> entry.getKey().equalsIgnoreCase("Idempotency-Key")
                        && !entry.getValue().isBlank());
    }
}
