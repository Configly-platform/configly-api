# Review scenario matrix

All examples are synthetic. Nothing here is required for the application build.

## 01. Single-line comment

Target: `RouteSpec.java`.

Comment on the enabled field and return to code.

Expected: no unrelated files or comments are changed.

## 02. Multiline code range

Target: `RouteSelector.java`.

Select matching filters and sorting as one range.

Expected: no unrelated files or comments are changed.

## 03. Word selection

Target: `RetryPolicy.java`.

Use Alt+Shift+Left/Right inside canRetry.

Expected: no unrelated files or comments are changed.

## 04. Long diff

Target: `fixtures/requests.json`.

Page down, search, and return to the selected line.

Expected: no unrelated files or comments are changed.

## 05. Multiple drafts

Target: `config/routes.staging.json`.

Queue several comments before opening the final preview.

Expected: no unrelated files or comments are changed.

## 06. Reply

Target: `A published sample thread`.

Write a reply while the original comment remains visible.

Expected: no unrelated files or comments are changed.

## 07. Reply and resolve

Target: `An unresolved sample thread`.

Queue resolution; confirm it only changes after Send.

Expected: no unrelated files or comments are changed.

## 08. Resolved thread

Target: `A resolved sample thread`.

Check the icon and status color in Threads.

Expected: no unrelated files or comments are changed.

## 09. Formatting

Target: `docs/formatting-samples.md`.

Post paragraphs, a list, and a fenced code block.

Expected: no unrelated files or comments are changed.

## 10. All files

Target: `Existing src/main application code`.

Browse unchanged code without checking out the PR branch.

Expected: no unrelated files or comments are changed.

## 11. General comment

Target: `An unchanged file in All files`.

Use the explicit general-comment conversion.

Expected: no unrelated files or comments are changed.

## 12. Persistence

Target: `Any draft`.

Close and reopen before sending; confirm the text remains.

Expected: no unrelated files or comments are changed.

## 13. Finish preview

Target: `Review & send`.

Verify file and range references before Send review.

Expected: no unrelated files or comments are changed.

## 14. No early publication

Target: `GitHub browser view`.

Confirm composing and leaving preview create no remote comments.

Expected: no unrelated files or comments are changed.

## 15. Summary

Target: `Review & send`.

Write a summary with more than one line.

Expected: no unrelated files or comments are changed.

## 16. Keyboard and mouse

Target: `Visible action row`.

Repeat the same flow once with keys and once with clicks.

Expected: no unrelated files or comments are changed.
