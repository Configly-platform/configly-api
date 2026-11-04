package com.configly.reviewsandbox;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Review target: select lines in matching(), then add a multiline comment. */
public final class RouteSelector {
    private final RolloutPolicy rollout;

    public RouteSelector(RolloutPolicy rollout) {
        this.rollout = rollout;
    }

    public List<RouteSpec> matching(ReviewRequest request, List<RouteSpec> routes) {
        return routes.stream()
                .filter(route -> route.matches(request))
                .filter(route -> rollout.includes(request.tenantId(), route.id(), route.rolloutPercentage()))
                .sorted(Comparator.comparingInt(RouteSpec::priority).reversed()
                        .thenComparing(RouteSpec::id))
                .toList();
    }

    public Optional<RouteSpec> select(ReviewRequest request, List<RouteSpec> routes) {
        return matching(request, routes).stream().findFirst();
    }

    public SelectionTrace trace(ReviewRequest request, List<RouteSpec> routes) {
        var eligible = matching(request, routes);
        var selected = eligible.stream().findFirst().map(RouteSpec::id).orElse("none");
        var disabled = routes.stream().filter(route -> !route.enabled()).count();
        var matchingRegion = routes.stream().filter(route -> route.region().equals(request.region())).count();
        return new SelectionTrace(request.requestId(), selected, eligible.size(), disabled, matchingRegion);
    }

    public record SelectionTrace(
            String requestId,
            String selectedRoute,
            int eligibleRoutes,
            long disabledRoutes,
            long routesInRegion) {

        public String summary() {
            return "request=" + requestId
                    + ", selected=" + selectedRoute
                    + ", eligible=" + eligibleRoutes
                    + ", disabled=" + disabledRoutes
                    + ", inRegion=" + routesInRegion;
        }
    }
}
