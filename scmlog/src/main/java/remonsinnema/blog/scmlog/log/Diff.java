package remonsinnema.blog.scmlog.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Diff {

  private final String fileName;
  private final Collection<ChangeHunk> changeHunks;

  public Diff(String fileName, Collection<ChangeHunk> changeHunks) {
    this.fileName = fileName;
    this.changeHunks = new ArrayList<>(changeHunks);
  }

  public String getFileName() {
    return fileName;
  }

  public Stream<ChangeHunk> getChangeHunks() {
    return changeHunks.stream();
  }

  @Override
  public String toString() {
    return String.format("%c %s\n%s", 187, getFileName(), getChangeHunks()
        .map(ch -> ch.toString())
        .collect(Collectors.joining("\n")));
  }

}
