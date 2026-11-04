# Formatting examples

This file is deliberately rich in commentable prose.
Select any paragraph or a table row to practice a range comment.

## Paragraphs and blank lines

A review comment can contain a first paragraph.

It can then contain a second paragraph after a blank line.

**Expected:** ShiftSpace preserves those paragraph boundaries.

## Lists

- Preserve the selected path.
- Preserve the selected first and last line.
- Keep comments local until the review is sent.

1. Add a new comment.
2. Reply to an existing thread.
3. Send both in one review.

## Code fences

```java
var result = selector.select(request, routes);
if (result.isEmpty()) {
    return "No route selected";
}
```

```json
{
  "enabled": true,
  "percentage": 25,
  "note": "This is only a synthetic example"
}
```

## Tables

| Action | Before Send | After Send |
| --- | --- | --- |
| New comment | Local draft | Published comment |
| Reply | Local draft | Published reply |
| Resolve thread | Queued | Resolved after confirmation |

## Quoted feedback

> Please keep the timeout inside the remaining request budget.
> A second line should remain on its own line.

## Inline formatting

Try **bold**, *emphasis*, `inline code`, and a normal link to
[the sandbox guide](../README.md).

## Unicode

Zażółć gęślą jaźń — sample Polish text for terminal rendering.
Symbols: ✓ resolved, ● unresolved, ✎ draft, → next action.
