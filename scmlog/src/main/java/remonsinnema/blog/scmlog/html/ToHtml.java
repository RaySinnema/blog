package remonsinnema.blog.scmlog.html;

import java.io.PrintWriter;
import java.io.Writer;

import remonsinnema.blog.scmlog.log.ChangeHunk;
import remonsinnema.blog.scmlog.log.Line;


public class ToHtml {

  private static final String SPACE = "&nbsp;";
  private static final String GREEN = "ffecec";
  private static final String RED = "eaffea";

  private final ChangeHunk chunk;

  public ToHtml(ChangeHunk chunk) {
    this.chunk = chunk;
  }

  public void writeTo(Writer output) {
    PrintWriter writer = new PrintWriter(output);
    writer.println("<table style='border: hidden; border-collapse: collapse; font-size: 10pt;'>");
    chunk.getLines().forEachOrdered(line -> writeLine(line, writer));
    writer.println("</table>");
  }

  private void writeLine(Line line, PrintWriter writer) {
    writer.print("<tr style='border: hidden;'><td style='font-family: Lucida Console; padding: 0;");
    writeBackgroundColor(line, writer);
    writer.print("'>");
    writeText(line.getType().toString(), writer);
    writeText(line.getText(), writer);
    writer.println("</td></tr>");
  }

  private void writeBackgroundColor(Line line, PrintWriter writer) {
    switch (line.getType()) {
      case ADDITION:
        writeBackgroudColor(GREEN, writer);
        break;
      case DELETION:
        writeBackgroudColor(RED, writer);
        break;
      default:
        break;
    }
  }

  private void writeBackgroudColor(String color, PrintWriter writer) {
    writer.write(" background-color: #");
    writer.write(color);
    writer.write(';');
  }

  private void writeText(String text, PrintWriter writer) {
    writer.print(text.replace(" ", SPACE));
  }

}
