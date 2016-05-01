package remonsinnema.blog.scmlog.git;

import static org.junit.Assert.*;

import static remonsinnema.blog.scmlog.log.Line.Type.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import remonsinnema.blog.scmlog.log.ChangeHunk;
import remonsinnema.blog.scmlog.log.Commit;
import remonsinnema.blog.scmlog.log.Diff;
import remonsinnema.blog.scmlog.log.Line;
import remonsinnema.blog.scmlog.log.Line.Type;


public class WhenParsingGitLogOutput {

  private final Random random = new Random();
  private final GitLogParser parser = new GitLogParser();

  @Test
  public void shouldExtractCommitHashAndSubject() {
    String hash1 = someHash();
    String subject1 = someSubject();
    String hash2 = someHash();
    String subject2 = someSubject();
    List<String> lines = Arrays.asList(commit(hash1, subject1), commit(hash2, subject2));

    Iterator<Commit> commits = parser.parse(lines).iterator();

    assertTrue("Missing commit #1", commits.hasNext());
    Commit commit = commits.next();
    assertEquals("Hash #1", hash1, commit.getHash());
    assertEquals("Subject #1", subject1, commit.getSubject());
    assertTrue("Missing commit #2", commits.hasNext());
    commit = commits.next();
    assertEquals("Hash #2", hash2, commit.getHash());
    assertEquals("Subject #2", subject2, commit.getSubject());
    assertFalse("Extra commits", commits.hasNext());
  }

  private String someHash() {
    String chars = "0123456789abcdef";
    return Stream.generate(() -> chars.charAt(random.nextInt(chars.length())))
        .limit(40)
        .map(c -> Character.toString(c))
        .collect(Collectors.joining());
  }

  private String someSubject() {
    return someWord() + ' ' + someWord();
  }

  private String someWord() {
    return random.ints('a', 'z')
        .limit(10)
        .mapToObj(i -> Character.toString((char)i))
        .collect(Collectors.joining());
  }

  private String commit(String hash, String subject) {
    return hash + ' ' + subject;
  }

  @Test
  public void shouldExtractDiffFileName() throws Exception {
    String fileName1 = someFileName();
    String fileName2 = someFileName();
    List<String> lines = Arrays.asList(someCommit(), diff(fileName1), someChangeHunkHeader(), diff(fileName2));

    Collection<Commit> commits = parser.parse(lines);

    Iterator<Diff> diffs = commits.iterator().next().getDiffs().iterator();
    assertTrue("Missing diff #1", diffs.hasNext());
    Diff diff = diffs.next();
    assertEquals("File name #1", fileName1, diff.getFileName());
    assertTrue("Missing diff #2", diffs.hasNext());
    diff = diffs.next();
    assertEquals("File name #2", fileName2, diff.getFileName());
    assertFalse("Extra diffs", diffs.hasNext());
  }

  private String someFileName() {
    return someWord() + ".java";
  }

  private String someCommit() {
    return commit(someHash(), someSubject());
  }

  private String diff(String fileName) {
    return String.format("diff --git %1$s %1$s", fileName);
  }

  @Test
  public void shouldExtractDiffChangeHunks() {
    String fileName = someFileName();
    String changeHunk1 = someWord();
    String changeHunk2 = someWord();
    String changeHunk3 = someWord();
    String changeHunk4 = someWord();
    List<String> lines = Arrays.asList(someCommit(), diff(fileName), someWord(), someWord(),
        someChangeHunkHeader(), ' ' + changeHunk1, '+' + changeHunk2, '-' + changeHunk3, ' ' + changeHunk4,
        someChangeHunkHeader(), '+' + someWord());

    Collection<Commit> commits = parser.parse(lines);

    Iterator<ChangeHunk> changeHunks = commits.iterator().next()
        .getDiffs().iterator().next()
        .getChangeHunks().iterator();
    assertTrue("Missing chunk #1", changeHunks.hasNext());
    ChangeHunk changeHunk = changeHunks.next();
    assertCollectionEquals("Chunks #1", lines(
        CONTEXT, changeHunk1, ADDITION, changeHunk2, DELETION, changeHunk3, CONTEXT, changeHunk4),
        changeHunk.getLines().iterator());
    assertTrue("Missing chunk #2", changeHunks.hasNext());
    changeHunk = changeHunks.next();
    assertFalse("Extra chunks", changeHunks.hasNext());
  }

  private String someChangeHunkHeader() {
    return String.format("@@ -1,%d +1,%d @@", someLineNo(), someLineNo());
  }

  private int someLineNo() {
    return 1 + random.nextInt(200);
  }

  private Iterator<Line> lines(Object... args) {
    Collection<Line> result = new ArrayList<>();
    for (int i = 0; i < args.length; i += 2) {
      Type type = Line.Type.class.cast(args[i]);
      String text = String.class.cast(args[i + 1]);
      result.add(new Line(type, text));
    }
    return result.iterator();
  }

  private <T> void assertCollectionEquals(String message, Iterator<T> expected, Iterator<T> actual) {
    while (expected.hasNext()) {
      T wanted = expected.next();
      assertTrue("Missing item: " + wanted, actual.hasNext());

      T gotten = actual.next();
      if (!Objects.equals(wanted, gotten)) {
        fail("Item: expected <" + wanted + ">, but got <" + gotten + '>');
      }
    }
    assertFalse("Extra items", actual.hasNext());
  }

  @Test
  public void shouldExtractInitialContextLineInChangeHunk() {
    String changeHunk1 = someWord();
    String changeHunk2 = someWord();
    List<String> lines = Arrays.asList(someCommit(), diff(someFileName()),
        someChangeHunkHeader() + changeHunk1, '+' + changeHunk2);

    Collection<Commit> commits = parser.parse(lines);

    ChangeHunk changeHunk = commits.iterator().next()
        .getDiffs().iterator().next()
        .getChangeHunks().iterator().next();
    assertCollectionEquals("Chunks", lines(CONTEXT, changeHunk1, ADDITION, changeHunk2),
        changeHunk.getLines().iterator());
  }

}
