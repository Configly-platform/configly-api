package com.configly.reviewsandbox;

import java.time.Duration;
import java.util.Set;

/** A small standalone retry policy, deliberately outside application sources. */
public record RetryPolicy(int maxAttempts, Duration initialDelay, Duration maxDelay) {
    private static final Set<Integer> RETRYABLE_STATUSES = Set.of(429, 502, 503, 504);

    public RetryPolicy {
        if (maxAttempts < 1 || maxAttempts > 8) {
            throw new IllegalArgumentException("Attempts must be between 1 and 8");
        }
        if (initialDelay == null || initialDelay.isNegative()) {
            throw new IllegalArgumentException("Initial delay cannot be negative");
        }
        if (maxDelay == null || maxDelay.compareTo(initialDelay) < 0) {
            throw new IllegalArgumentException("Maximum delay is too small");
        }
    }

    public boolean canRetry(ReviewRequest request, int completedAttempts, int status) {
        if (completedAttempts < 1 || completedAttempts >= maxAttempts) {
            return false;
        }
        if (!RETRYABLE_STATUSES.contains(status)) {
            return false;
        }
        return request.isReadOnly() || request.hasIdempotencyKey();
    }

    public Duration delayAfter(int completedAttempts) {
        if (completedAttempts < 1) {
            throw new IllegalArgumentException("At least one attempt is required");
        }
        var delay = initialDelay;
        for (var attempt = 1; attempt < completedAttempts; attempt++) {
            if (delay.compareTo(maxDelay.dividedBy(2)) > 0) {
                return maxDelay;
            }
            delay = delay.multipliedBy(2);
        }
        return delay.compareTo(maxDelay) > 0 ? maxDelay : delay;
    }

    public static RetryPolicy defaults() {
        return new RetryPolicy(3, Duration.ofMillis(100), Duration.ofSeconds(2));
    }
}
