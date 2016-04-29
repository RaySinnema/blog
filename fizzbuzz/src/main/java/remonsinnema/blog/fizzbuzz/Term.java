package remonsinnema.blog.fizzbuzz;

import java.util.Optional;


public class Term {

  private final int value;
  private final String text;

  public Term(int value, String text) {
    this.value = value;
    this.text = text;
  }

  public Optional<String> textFor(int n) {
    return Optional.of(text).filter(t -> n % value == 0);
  }

}
