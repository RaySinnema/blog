package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;


public class FizzBuzz {

  private final Collection<MultipleReplacer> replacers = Arrays.asList(
      new MultipleReplacer(3, "Fizz"), new MultipleReplacer(5, "Buzz"));

  public String get(int n) {
    for (MultipleReplacer replacer : replacers) {
      if (n == replacer.getValue()) {
        return replacer.getText();
      }
    }
    return Integer.toString(n);
  }

}
