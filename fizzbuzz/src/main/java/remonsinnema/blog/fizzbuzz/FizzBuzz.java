package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


public class FizzBuzz {

  private final Collection<MultipleReplacer> replacers = Arrays.asList(
      new MultipleReplacer(3, "Fizz"), new MultipleReplacer(5, "Buzz"));

  public String get(int n) {
    StringBuilder result = new StringBuilder();
    for (MultipleReplacer replacer : replacers) {
      Optional<String> replacement = replacer.textFor(n);
      if (replacement.isPresent()) {
        result.append(replacement.get());
      }
    }
    if (result.length() > 0) {
      return result.toString();
    }
    return Integer.toString(n);
  }

}
