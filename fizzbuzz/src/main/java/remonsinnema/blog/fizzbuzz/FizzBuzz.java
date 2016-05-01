package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


public class FizzBuzz {

  private final Collection<MultipleReplacer> replacers = Arrays.asList(
      new MultipleReplacer(3, "Fizz"), new MultipleReplacer(5, "Buzz"));

  public String get(int n) {
    for (MultipleReplacer replacer : replacers) {
      Optional<String> result = replacer.textFor(n);
      if (result.isPresent()) {
        return result.get();
      }
    }
    return Integer.toString(n);
  }

}
