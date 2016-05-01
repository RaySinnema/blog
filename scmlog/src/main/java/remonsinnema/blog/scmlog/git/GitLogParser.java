package remonsinnema.blog.scmlog.git;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import remonsinnema.blog.scmlog.log.ChangeHunk;
import remonsinnema.blog.scmlog.log.Commit;
import remonsinnema.blog.scmlog.log.Diff;
import remonsinnema.blog.scmlog.log.Line;
import remonsinnema.blog.scmlog.util.Traversal;


public class GitLogParser {

  private static final Pattern COMMIT_PATTERN = Pattern.compile("([0-9a-f]{40}) (.+)");
  private static final Pattern DIFF_PATTERN = Pattern.compile("diff --git (?<name>.*) \\k<name>");
  private static final Pattern CHANGE_HUNK_PATTERN
      = Pattern.compile("@@\\s+\\-\\d+,\\d+\\s+\\+\\d+,\\d+\\s+@@(?<chunk>.+)?");

  public Collection<Commit> parse(List<String> gitLogOutput) {
    Collection<Commit> result = new ArrayList<>();
    addCommits(new Traversal<>(gitLogOutput), result);
    return result;
  }

  private void addCommits(Traversal<String> lines, Collection<Commit> commits) {
    while (lines.hasNext()) {
      String line = lines.next();
      Matcher matcher = COMMIT_PATTERN.matcher(line);
      if (matcher.matches()) {
        commits.add(parseCommit(matcher.group(1), matcher.group(2), lines));
      } else {
        throw new IllegalStateException("Expected a commit, but got: " + line);
      }
    }
  }

  private Commit parseCommit(String hash, String subject, Traversal<String> lines) {
    Collection<Diff> diffs = new ArrayList<>();
    while (lines.hasNext()) {
      String line = lines.next();
      Matcher matcher = DIFF_PATTERN.matcher(line);
      if (matcher.matches()) {
        diffs.add(parseDiff(matcher.group(1), lines));
      } else {
        lines.previous();
        break;
      }
    }
    return new Commit(hash, subject, diffs);
  }

  private Diff parseDiff(String fileName, Traversal<String> lines) {
    skipDiffPreamble(lines);
    Collection<ChangeHunk> changeHunks = new ArrayList<>();
    Collection<Line> changeHunkLines = new ArrayList<>();
    while (lines.hasNext()) {
      String line = lines.next();
      if (COMMIT_PATTERN.matcher(line).matches() || DIFF_PATTERN.matcher(line).matches()) {
        lines.previous();
        break;
      }
      if (CHANGE_HUNK_PATTERN.matcher(line).matches()) {
        changeHunks.add(new ChangeHunk(changeHunkLines));
        changeHunkLines.clear();
      } else {
        changeHunkLines.add(parseLine(line));
      }
    }
    if (!changeHunkLines.isEmpty()) {
      changeHunks.add(new ChangeHunk(changeHunkLines));
    }
    return new Diff(fileName, changeHunks);
  }

  private void skipDiffPreamble(Traversal<String> lines) {
    while (lines.hasNext()) {
      String line = lines.next();
      Matcher matcher = CHANGE_HUNK_PATTERN.matcher(line);
      if (matcher.matches()) {
        String chunk = matcher.group("chunk");
        if (chunk != null) {
          lines.replace(' ' + chunk.trim());
        }
        break;
      }
    }
  }

  private Line parseLine(String line) {
    for (Line.Type type : Line.Type.values()) {
      if (line.startsWith(type.toString())) {
        return new Line(type, line.substring(type.toString().length()).trim());
      }
    }
    throw new IllegalArgumentException("Invalid change hunk line: " + line);
  }

}
