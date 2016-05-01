package remonsinnema.blog.fizzbuzz;

import java.util.Optional;


public class MultipleReplacer {

  private final int value;
  private final String text;

  public MultipleReplacer(int value, String text) {
    this.value = value;
    this.text = text;
  }

  public Optional<String> textFor(int n) {
    if (n % value == 0) {
      return Optional.of(text);
    }
    return Optional.empty();
  }

}
