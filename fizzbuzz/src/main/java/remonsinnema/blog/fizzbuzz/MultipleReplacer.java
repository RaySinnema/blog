package remonsinnema.blog.fizzbuzz;


public class MultipleReplacer {

  private final int value;
  private final String text;

  public MultipleReplacer(int value, String text) {
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
