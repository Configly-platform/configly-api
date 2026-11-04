package com.configly.reviewsandbox;

import java.time.Duration;

/** Captures how a route timeout is limited by the remaining request budget. */
public record TimeoutPolicy(Duration minimum, Duration maximum, Duration reserve) {
    public TimeoutPolicy {
        if (minimum == null || minimum.isNegative() || minimum.isZero()) {
            throw new IllegalArgumentException("Minimum timeout must be positive");
        }
        if (maximum == null || maximum.compareTo(minimum) < 0) {
            throw new IllegalArgumentException("Maximum must cover the minimum");
        }
        if (reserve == null || reserve.isNegative()) {
            throw new IllegalArgumentException("Reserve cannot be negative");
        }
    }

    public Duration allocate(RouteSpec route, Duration remaining) {
        if (remaining == null || remaining.compareTo(reserve) <= 0) {
            return Duration.ZERO;
        }
        var available = remaining.minus(reserve);
        if (available.compareTo(minimum) < 0) {
            return Duration.ZERO;
        }
        var candidate = route.timeout().compareTo(maximum) < 0 ? route.timeout() : maximum;
        return candidate.compareTo(available) < 0 ? candidate : available;
    }

    public boolean hasBudget(RouteSpec route, Duration remaining) {
        return !allocate(route, remaining).isZero();
    }

    public static TimeoutPolicy defaults() {
        return new TimeoutPolicy(Duration.ofMillis(50), Duration.ofSeconds(10), Duration.ofMillis(25));
    }
}
