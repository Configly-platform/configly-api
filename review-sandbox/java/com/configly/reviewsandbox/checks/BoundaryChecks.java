package com.configly.reviewsandbox.checks;

import com.configly.reviewsandbox.*;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

/** Boundary examples provide small, commentable code blocks. */
public final class BoundaryChecks {
    public static void main(String[] args) {
        var rollout = new RolloutPolicy();
        for (var index = 0; index < 200; index++) {
            var tenant = "demo-tenant-" + index;
            check(!rollout.includes(tenant, "demo-route", 0), "Zero rollout should exclude everyone");
            check(rollout.includes(tenant, "demo-route", 100), "Full rollout should include everyone");
            var bucket = rollout.bucket(tenant, "demo-route");
            check(bucket >= 0 && bucket < 100, "Bucket should stay in range");
            check(bucket == rollout.bucket(tenant, "demo-route"), "Bucket should be stable");
        }

        var retry = RetryPolicy.defaults();
        check(retry.delayAfter(1).equals(Duration.ofMillis(100)), "Initial delay should be preserved");
        check(retry.delayAfter(2).equals(Duration.ofMillis(200)), "Second delay should double");
        check(retry.delayAfter(8).equals(Duration.ofSeconds(2)), "Long retry sequence should be capped");

        var write = new ReviewRequest("request-002", "demo-tenant", "POST", "/v1/toggles", "eu-central", Map.of());
        check(!retry.canRetry(write, 1, 503), "Writes without an idempotency key should not retry");
        var keyed = new ReviewRequest("request-003", "demo-tenant", "POST", "/v1/toggles", "eu-central", Map.of("Idempotency-Key", "demo-key"));
        check(retry.canRetry(keyed, 1, 503), "A keyed write may retry");

        var route = new RouteSpec("demo", "/v1/toggles", "eu-central", Set.of("GET"), 1, 100, Duration.ofSeconds(2), true);
        check(TimeoutPolicy.defaults().allocate(route, Duration.ofMillis(30)).isZero(), "Tiny budgets should be rejected");
        System.out.println("Boundary checks passed");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
