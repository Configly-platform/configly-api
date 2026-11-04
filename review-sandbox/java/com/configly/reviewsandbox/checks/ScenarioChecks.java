package com.configly.reviewsandbox.checks;

import com.configly.reviewsandbox.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Run without dependencies: java com.configly.reviewsandbox.checks.ScenarioChecks. */
public final class ScenarioChecks {
    public static void main(String[] args) {
        var request = new ReviewRequest("request-001", "demo-tenant", "get", "/v1/toggles/demo", "eu-central", Map.of());
        var primary = route("primary", 100, true);
        var secondary = route("secondary", 20, true);
        var disabled = route("disabled", 200, false);
        var selector = new RouteSelector(new RolloutPolicy());

        check(selector.select(request, List.of(secondary, primary, disabled)).orElseThrow().id().equals("primary"),
                "Highest enabled priority should win");
        check(selector.matching(request, List.of(disabled)).isEmpty(),
                "Disabled routes should not be selected");
        check(new RequestValidator().accepts(request),
                "A normal read should pass validation");
        check(RetryPolicy.defaults().canRetry(request, 1, 503),
                "A read may retry a temporary failure");
        check(!RetryPolicy.defaults().canRetry(request, 3, 503),
                "Retry attempts must remain bounded");
        check(TimeoutPolicy.defaults().allocate(primary, Duration.ofMillis(500)).equals(Duration.ofMillis(475)),
                "Request budget should retain the reserve");

        var time = Instant.parse("2026-01-01T00:00:00Z");
        var entry = AuditEntry.accepted(request, primary, time);
        check(entry.successful(), "Accepted audit should report success");
        check(entry.correlationKey().equals("demo-tenant:request-001"),
                "Audit should retain request correlation");
        check(SandboxCatalog.routes().size() == 40, "Catalog should contain all synthetic routes");

        System.out.println("Scenario checks passed");
    }

    private static RouteSpec route(String id, int priority, boolean enabled) {
        return new RouteSpec(id, "/v1/toggles", "eu-central", Set.of("GET"), priority,
                100, Duration.ofSeconds(2), enabled);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
