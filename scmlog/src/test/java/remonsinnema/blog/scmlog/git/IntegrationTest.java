package remonsinnema.blog.scmlog.git;

import static org.junit.Assert.*;

import static remonsinnema.blog.scmlog.log.Line.Type.*;

import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.function.Supplier;

import org.junit.Test;

import remonsinnema.blog.scmlog.html.ToHtml;
import remonsinnema.blog.scmlog.log.ChangeHunk;
import remonsinnema.blog.scmlog.log.Line;
import remonsinnema.blog.scmlog.log.Log;


public class IntegrationTest {

  private static final String NL = System.getProperty("line.separator");

  private final Supplier<Log> supplier = new GitLogCommand(new File("."));

  @Test
  public void shouldFindCommitInLog() {
    Log log = supplier.get();

    log.getCommits().forEachOrdered(System.out::println);

    assertTrue("Missing commit", log.getCommits()
        .filter(c -> "Add default Gradle tasks".equals(c.getSubject()))
        .findAny()
        .isPresent());
  }

  @Test
  public void shouldRenderDiffToHtml() {
    String context1 = "ape";
    String addition = "bear";
    String deletion = "cheetah";
    String context2 = "dingo";
    ChangeHunk chunk = new ChangeHunk(Arrays.asList(new Line(CONTEXT, context1), new Line(DELETION, deletion),
        new Line(ADDITION, addition), new Line(CONTEXT, context2)));
    StringWriter writer = new StringWriter();

    new ToHtml(chunk).writeTo(writer);

    assertEquals("HTML", "<table style='border: hidden; border-collapse: collapse; font-size: 10pt;'>" + NL
        + row(' ' + context1)
        + row('-' + deletion, "eaffea")
        + row('+' + addition, "ffecec")
        + row(' ' + context2)
        + "</table>" + NL,
        writer.toString());
  }

  private String row(String content) {
    return row(content, null);
  }

  private String row(String content, String backgroundColor) {
    StringBuilder result = new StringBuilder();
    result.append("<tr style='border: hidden;'><td style='font-family: Lucida Console; padding: 0;");
    if (backgroundColor != null) {
      result.append(" background-color: #").append(backgroundColor).append(';');
    }
    result.append("'>");
    if (content.isEmpty()) {
      result.append("&nbsp;");
    } else {
      result.append(content.replace(" ", "&nbsp;"));
    }
    result.append("</td></tr>").append(NL);
    return result.toString();
  }

}

