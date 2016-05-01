package remonsinnema.blog.scmlog.log;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Commit {

  private final String hash;
  private final String subject;
  private final Collection<Diff> diffs;

  public Commit(String hash, String subject, Collection<Diff> diffs) {
    this.hash = hash;
    this.subject = subject;
    this.diffs = diffs;
  }

  public String getHash() {
    return hash;
  }

  public String getSubject() {
    return subject;
  }

  public Stream<Diff> getDiffs() {
    return diffs.stream();
  }

  @Override
  public String toString() {
    return getHash() + ' ' + getSubject() + getDiffs()
        .map(d -> d.toString())
        .collect(Collectors.joining("\n"));
  }

}
