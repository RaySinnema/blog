package remonsinnema.blog.scmlog.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import remonsinnema.blog.scmlog.log.Commit;
import remonsinnema.blog.scmlog.log.Log;


public class GitLogCommand implements Supplier<Log> {

  private static final String[] GIT_LOG_COMMAND = { "git", "--no-pager", "log", "--format=oneline", "-U3",
      "--diff-algorithm=minimal", "--word-diff=none", "--no-color", "--no-prefix", "--reverse", "." };

  private final File dir;

  public GitLogCommand(File dir) {
    this.dir = dir;
  }

  @Override
  public Log get() {
    return new Log(getCommits());
  }

  private Collection<Commit> getCommits() {
    return new GitLogParser().parse(gitLogOutput());
  }

  private List<String> gitLogOutput() {
    List<String> result = new ArrayList<>();
    addGitLogLines(result);
    return result;
  }

  private void addGitLogLines(Collection<String> result) {
    try {
      Process process = runGitLogCommand();
      addProcessOutputLines(process, result);
      process.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private Process runGitLogCommand() throws IOException {
    ProcessBuilder builder = new ProcessBuilder(GIT_LOG_COMMAND);
    builder.directory(dir);
    return builder.start();
  }

  private void addProcessOutputLines(Process process, Collection<String> output) {
    try (Scanner scanner = new Scanner(process.getInputStream())) {
      while (scanner.hasNextLine()) {
        output.add(scanner.nextLine());
      }
    }
  }

}
