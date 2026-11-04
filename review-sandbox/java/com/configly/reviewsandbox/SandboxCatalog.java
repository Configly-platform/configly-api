package com.configly.reviewsandbox;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/** A long catalog makes vertical scrolling and file navigation easy to exercise. */
public final class SandboxCatalog {
    private SandboxCatalog() { }

    public static List<RouteSpec> routes() {
        return List.of(
                route("route-001", "/v1/resource-01", "eu-central", 99, 100, true),
                route("route-002", "/v1/resource-02", "eu-west", 98, 100, true),
                route("route-003", "/v1/resource-03", "us-east", 97, 100, true),
                route("route-004", "/v1/resource-04", "ap-south", 96, 100, true),
                route("route-005", "/v1/resource-05", "eu-central", 95, 25, true),
                route("route-006", "/v1/resource-06", "eu-west", 94, 100, true),
                route("route-007", "/v1/resource-07", "us-east", 93, 100, true),
                route("route-008", "/v1/resource-08", "ap-south", 92, 100, true),
                route("route-009", "/v1/resource-09", "eu-central", 91, 100, true),
                route("route-010", "/v1/resource-10", "eu-west", 90, 25, true),
                route("route-011", "/v1/resource-11", "us-east", 89, 100, false),
                route("route-012", "/v1/resource-12", "ap-south", 88, 100, true),
                route("route-013", "/v1/resource-13", "eu-central", 87, 100, true),
                route("route-014", "/v1/resource-14", "eu-west", 86, 100, true),
                route("route-015", "/v1/resource-15", "us-east", 85, 25, true),
                route("route-016", "/v1/resource-16", "ap-south", 84, 100, true),
                route("route-017", "/v1/resource-17", "eu-central", 83, 100, true),
                route("route-018", "/v1/resource-18", "eu-west", 82, 100, true),
                route("route-019", "/v1/resource-19", "us-east", 81, 100, true),
                route("route-020", "/v1/resource-20", "ap-south", 80, 25, true),
                route("route-021", "/v1/resource-21", "eu-central", 79, 100, true),
                route("route-022", "/v1/resource-22", "eu-west", 78, 100, false),
                route("route-023", "/v1/resource-23", "us-east", 77, 100, true),
                route("route-024", "/v1/resource-24", "ap-south", 76, 100, true),
                route("route-025", "/v1/resource-25", "eu-central", 75, 25, true),
                route("route-026", "/v1/resource-26", "eu-west", 74, 100, true),
                route("route-027", "/v1/resource-27", "us-east", 73, 100, true),
                route("route-028", "/v1/resource-28", "ap-south", 72, 100, true),
                route("route-029", "/v1/resource-29", "eu-central", 71, 100, true),
                route("route-030", "/v1/resource-30", "eu-west", 70, 25, true),
                route("route-031", "/v1/resource-31", "us-east", 69, 100, true),
                route("route-032", "/v1/resource-32", "ap-south", 68, 100, true),
                route("route-033", "/v1/resource-33", "eu-central", 67, 100, false),
                route("route-034", "/v1/resource-34", "eu-west", 66, 100, true),
                route("route-035", "/v1/resource-35", "us-east", 65, 25, true),
                route("route-036", "/v1/resource-36", "ap-south", 64, 100, true),
                route("route-037", "/v1/resource-37", "eu-central", 63, 100, true),
                route("route-038", "/v1/resource-38", "eu-west", 62, 100, true),
                route("route-039", "/v1/resource-39", "us-east", 61, 100, true),
                route("route-040", "/v1/resource-40", "ap-south", 60, 25, true));
    }

    private static RouteSpec route(String id, String path, String region, int priority, int rollout, boolean enabled) {
        return new RouteSpec(id, path, region, Set.of("GET", "HEAD", "POST"), priority,
                rollout, Duration.ofSeconds(2), enabled);
    }
}
