package com.configly.reviewsandbox;

import java.time.Duration;
import java.util.Set;

/** Synthetic route data for the ShiftSpace review sandbox. */
public record RouteSpec(
        String id,
        String pathPrefix,
        String region,
        Set<String> methods,
        int priority,
        int rolloutPercentage,
        Duration timeout,
        boolean enabled) {

    public RouteSpec {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Route id is required");
        }
        if (pathPrefix == null || !pathPrefix.startsWith("/")) {
            throw new IllegalArgumentException("Route prefix must be absolute");
        }
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("Region is required");
        }
        methods = Set.copyOf(methods);
        if (methods.isEmpty()) {
            throw new IllegalArgumentException("At least one method is required");
        }
        if (rolloutPercentage < 0 || rolloutPercentage > 100) {
            throw new IllegalArgumentException("Rollout must be between 0 and 100");
        }
        if (timeout == null || timeout.isNegative() || timeout.isZero()) {
            throw new IllegalArgumentException("Timeout must be positive");
        }
    }

    public boolean matches(ReviewRequest request) {
        return enabled
                && region.equals(request.region())
                && methods.contains(request.method())
                && matchesPath(request.path());
    }

    private boolean matchesPath(String path) {
        if (pathPrefix.equals("/")) {
            return path.startsWith("/");
        }
        return path.equals(pathPrefix) || path.startsWith(pathPrefix + "/");
    }

    public RouteSpec withEnabled(boolean value) {
        return new RouteSpec(id, pathPrefix, region, methods, priority,
                rolloutPercentage, timeout, value);
    }

    public RouteSpec withRolloutPercentage(int value) {
        return new RouteSpec(id, pathPrefix, region, methods, priority,
                value, timeout, enabled);
    }
}
