package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;


public class FizzBuzz {

  private final Collection<Term> terms = Arrays.asList(new Term(3, "Fizz"), new Term(5, "Buzz"));

  public String get(int n) {
    return terms.stream()
        .map(term -> term.textFor(n))
        .filter(Optional::isPresent)
        .map(o -> o.get())
        .reduce((a, b) -> a + b)
        .orElse(Integer.toString(n));
  }

}
