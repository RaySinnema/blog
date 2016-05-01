package remonsinnema.blog.scmlog.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Log {

  private final Collection<Commit> commits;

  public Log(Collection<Commit> commits) {
    this.commits = new ArrayList<>(commits);
  }

  public Stream<Commit> getCommits() {
    return commits.stream();
  }

  @Override
  public String toString() {
    return getCommits()
        .map(commit -> commit.toString())
        .collect(Collectors.joining("\n"));
  }

}
