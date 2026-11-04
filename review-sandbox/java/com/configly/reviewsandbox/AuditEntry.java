package com.configly.reviewsandbox;

import java.time.Instant;
import java.util.Map;

/** Only synthetic ids and metadata are included in this fixture. */
public record AuditEntry(
        Instant occurredAt,
        String requestId,
        String tenantId,
        String routeId,
        String outcome,
        Map<String, String> metadata) {

    public AuditEntry {
        metadata = Map.copyOf(metadata);
    }

    public static AuditEntry accepted(ReviewRequest request, RouteSpec route, Instant time) {
        return new AuditEntry(time, request.requestId(), request.tenantId(), route.id(), "accepted",
                Map.of("method", request.method(), "region", request.region()));
    }

    public static AuditEntry rejected(ReviewRequest request, String reason, Instant time) {
        return new AuditEntry(time, request.requestId(), request.tenantId(), "none", "rejected",
                Map.of("reason", reason, "region", request.region()));
    }

    public String correlationKey() {
        return tenantId + ":" + requestId;
    }

    public boolean successful() {
        return outcome.equals("accepted");
    }
}
