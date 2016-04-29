package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;


public class FizzBuzz {

  private final Collection<Term> terms = Arrays.asList(new Term(3, "Fizz"), new Term(5, "Buzz"));

  public String get(int n) {
    StringBuilder result = new StringBuilder();
    for (Term term : terms) {
      term.textFor(n)
          .ifPresent(t -> result.append(t));
    }
    if (result.length() > 0) {
      return result.toString();
    }
    return Integer.toString(n);
  }

}
