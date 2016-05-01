package remonsinnema.blog.scmlog.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ChangeHunk {

  private final Collection<Line> lines;

  public ChangeHunk(Collection<Line> lines) {
    this.lines = new ArrayList<>(lines);
  }

  public Stream<Line> getLines() {
    return lines.stream();
  }

  @Override
  public String toString() {
    return "@@\n" + getLines().map(line -> line.toString()).collect(Collectors.joining("\n"));
  }

}
