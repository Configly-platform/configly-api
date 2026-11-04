# Synthetic routing design notes

This is a discussion document for review comments, not an application proposal.

## Route selection

Match enabled routes by method, path prefix, and region.
Apply a stable tenant rollout bucket before sorting eligible routes.
Prefer greater priority, then the route identifier for deterministic ties.

Review questions:

- Does a route prefix match a whole path segment?
- Should an unknown region produce validation feedback or an empty selection?
- What information belongs in the selection trace?
- Is a fixed bucket appropriate for this example?

## Retry budget

Retry a bounded set of transient responses.
Read requests can retry without an additional key.
Writes require an explicit idempotency key in the synthetic request.

Review questions:

- Is the attempt count the completed count or the next attempt number?
- Does the delay remain bounded after many attempts?
- Which statuses are considered transient?
- How would a server retry-after instruction fit this example?

## Timeout budget

Retain a small reserve for recording the result.
Never allocate more than the remaining request budget.
A budget below the minimum produces no upstream attempt.

Review questions:

- Is the exact boundary at the reserve documented clearly?
- Is zero duration an adequate representation of insufficient budget?
- Should queue time be included in the remaining duration?

## Audit data

Every identifier in these files starts with demo, request, route, or retry.
No user data, credentials, real customer identifiers or production endpoints
are included in the fixtures.

Review questions:

- Does a rejected request retain the same correlation key?
- Should selection traces be recorded on both success and failure?
- Which metadata is useful without copying raw request headers?
