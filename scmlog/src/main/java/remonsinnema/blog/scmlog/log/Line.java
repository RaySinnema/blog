package remonsinnema.blog.scmlog.log;


public class Line {

  public enum Type {
    CONTEXT(' '), ADDITION('+'), DELETION('-');

    private final char symbol;

    private Type(char symbol) {
      this.symbol = symbol;
    }

    @Override
    public String toString() {
      return Character.toString(symbol);
    }

  }

  private final Type type;
  private final String text;

  public Line(Type type, String text) {
    this.type = type;
    this.text = text;
  }

  public Type getType() {
    return type;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + type.hashCode();
    result = prime * result + text.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (getClass() != obj.getClass()) {
      return false;
    }
    Line other = (Line)obj;
    if (text == null) {
      if (other.text != null) {
        return false;
      }
    } else if (!text.equals(other.text)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format("%s %s", type, text);
  }

}
