package remonsinnema.blog.fizzbuzz;


public class Term {

  private final int value;
  private final String text;

  public Term(int value, String text) {
    this.value = value;
    this.text = text;
  }

  public int getValue() {
    return value;
  }

  public String getText() {
    return text;
  }

}
