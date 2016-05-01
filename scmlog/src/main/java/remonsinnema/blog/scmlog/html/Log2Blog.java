package remonsinnema.blog.scmlog.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import remonsinnema.blog.scmlog.git.GitLogCommand;
import remonsinnema.blog.scmlog.log.ChangeHunk;
import remonsinnema.blog.scmlog.log.Commit;
import remonsinnema.blog.scmlog.log.Diff;


public class Log2Blog {

  public static void main(String[] args) {
    try {
      new Log2Blog().printLog(getPath(args));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  private static String getPath(String[] args) {
    return args.length < 1 ? "." : args[0];
  }

  private void printLog(String path) throws IOException {
    File dir = new File(path);
    if (!dir.isDirectory()) {
      throw new IOException("Not a valid directory: " + dir.getCanonicalPath());
    }
    try (OutputStream stream = new FileOutputStream(new File(dir, "blog.html"))) {
      try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream))) {
        printLog(dir, writer);
      }
    }
  }

  private void printLog(File dir, PrintWriter writer) {
    writer.println("<html><body>");
    new GitLogCommand(dir).get().getCommits().forEachOrdered(commit -> printCommit(commit, writer));
    writer.println("</body></html>");
  }

  private void printCommit(Commit commit, PrintWriter writer) {
    writer.print("<h3>");
    writer.print(commit.getSubject());
    writer.print(" (");
    writer.print(commit.getHash());
    writer.print(")</h3>");
    commit.getDiffs().forEachOrdered(diff -> printDiff(diff, writer));
  }

  private void printDiff(Diff diff, PrintWriter writer) {
    writer.print("<p>");
    writer.print(new File(diff.getFileName()).getName());
    writer.print("</p>");
    diff.getChangeHunks().forEachOrdered(chunk -> printChangeHunk(chunk, writer));
  }

  private void printChangeHunk(ChangeHunk chunk, PrintWriter writer) {
    new ToHtml(chunk).writeTo(writer);
  }

}
