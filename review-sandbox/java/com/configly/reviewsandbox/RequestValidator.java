package com.configly.reviewsandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Review target: compare validation failures with fixtures/requests.json. */
public final class RequestValidator {
    private static final Set<String> METHODS = Set.of("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE");
    private static final Set<String> REGIONS = Set.of("eu-central", "eu-west", "us-east", "ap-south");

    public List<Violation> validate(ReviewRequest request) {
        var violations = new ArrayList<Violation>();
        if (!METHODS.contains(request.method())) {
            violations.add(new Violation("method", "unsupported", "Unsupported HTTP method"));
        }
        if (!REGIONS.contains(request.region())) {
            violations.add(new Violation("region", "unknown", "Unknown region"));
        }
        if (request.path().contains("..")) {
            violations.add(new Violation("path", "traversal", "Path contains a traversal segment"));
        }
        if (request.path().length() > 2048) {
            violations.add(new Violation("path", "too-long", "Path exceeds the sandbox limit"));
        }
        if (request.headers().size() > 32) {
            violations.add(new Violation("headers", "too-many", "Too many request headers"));
        }
        if (request.headers().entrySet().stream().anyMatch(entry -> entry.getKey().isBlank())) {
            violations.add(new Violation("headers", "empty-name", "Header name cannot be blank"));
        }
        return List.copyOf(violations);
    }

    public boolean accepts(ReviewRequest request) {
        return validate(request).isEmpty();
    }

    public String describe(ReviewRequest request) {
        var violations = validate(request);
        if (violations.isEmpty()) {
            return request.requestId() + ": accepted";
        }
        return request.requestId() + ": " + violations.stream()
                .map(value -> value.field() + "/" + value.code())
                .reduce((left, right) -> left + ", " + right)
                .orElse("rejected");
    }

    public record Violation(String field, String code, String message) { }
}
