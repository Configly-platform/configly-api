# ShiftSpace review sandbox

**TEST ONLY — do not merge this PR.**

These are synthetic review fixtures. The configly-api application does not
compile, import, or load them. No production behavior is changed.

## Start in ShiftSpace

1. Attach the configly-api directory to your workspace if necessary.
2. Refresh Pull / Merge Requests and open this PR under Created by me.
3. Changed files should open by default.
4. Press F to browse All files, including existing application files.
5. Return to Changed files before creating an inline review comment.

## Add a batch of comments

1. Open RouteSelector.java and select several lines in matching().
2. Choose Add comment and write a multiline comment.
3. Choose Add to review, then repeat in RetryPolicy.java and a JSON file.
4. Open Review & send and check the queued comments.
5. Go back: nothing should have been posted to GitHub.
6. Reopen the PR to check that drafts were saved.
7. Open Review & send again, optionally add a summary, then Send review.

## Reply and resolve

A few published fixture threads are provided on the PR.
Reply to one, select Resolve thread when sending, and Add to review.
It should remain unresolved on GitHub until Send review succeeds.
Another thread starts resolved so the list contains both statuses.

## Browse and copy

- Alt+1 focuses Files, Alt+2 Code, Alt+3 Threads.
- Use arrows, Shift selection and Alt+Left/Right word movement.
- Compare Unified and Split views using Tab.
- Use requests.json to test a long vertical diff.
- Use the long compatibility filename to test horizontal space.
- Try copying a selected range and searching for a route identifier.

## Standalone fixture checks

The Java examples use only the standard library and Java 21 syntax.
Compile the Java files into a temporary output directory and run:

```text
com.configly.reviewsandbox.checks.ScenarioChecks
com.configly.reviewsandbox.checks.BoundaryChecks
```

These checks are separate from configly-api's application build.
