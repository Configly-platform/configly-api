# Long filename and long-line fixture

This nested path is intentionally long so the file tree and header can be checked.

The next configuration entry is intentionally kept on one line to make horizontal scrolling, word movement, range selection and copy operations easy to exercise: route=demo-route-024; tenant=demo-tenant-012; region=eu-central; method=POST; path=/v1/resource-24/item-048; timeoutMillis=1500; maxAttempts=3; initialDelayMillis=100; maximumDelayMillis=2000; reserveMillis=25; expectedOutcome=accepted-after-idempotency-check.

## Compatibility notes

- Development examples use a full rollout.
- Staging examples use a partial rollout.
- Production examples use a small rollout.
- These names describe fixture variants only.
- None of the variants are loaded by the API application.

## Selection exercise

Select timeoutMillis in the long line using word movement.
Extend the selection through maxAttempts and add one review comment.
Copy the selected text and compare it with the original line.

## Navigation exercise

Jump from a thread back to this file.
Keep the selected thread visible while checking its file and line reference.
Switch to All files, expand a different directory, then return to Changed files.
