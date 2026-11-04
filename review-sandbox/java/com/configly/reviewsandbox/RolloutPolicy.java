package com.configly.reviewsandbox;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/** Stable buckets keep a synthetic tenant in the same rollout cohort. */
public final class RolloutPolicy {
    private static final int BUCKET_COUNT = 100;

    public boolean includes(String tenantId, String routeId, int percentage) {
        if (percentage < 0 || percentage > BUCKET_COUNT) {
            throw new IllegalArgumentException("Percentage outside supported range");
        }
        if (percentage == 0) {
            return false;
        }
        if (percentage == BUCKET_COUNT) {
            return true;
        }
        return bucket(tenantId, routeId) < percentage;
    }

    public int bucket(String tenantId, String routeId) {
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("Tenant id is required");
        }
        if (routeId == null || routeId.isBlank()) {
            throw new IllegalArgumentException("Route id is required");
        }
        var checksum = new CRC32();
        var key = tenantId + "\\u0000" + routeId;
        checksum.update(key.getBytes(StandardCharsets.UTF_8));
        return (int) (checksum.getValue() % BUCKET_COUNT);
    }

    public String describe(String tenantId, RouteSpec route) {
        return "route=" + route.id()
                + ", bucket=" + bucket(tenantId, route.id())
                + ", rollout=" + route.rolloutPercentage()
                + ", included=" + includes(tenantId, route.id(), route.rolloutPercentage());
    }
}
