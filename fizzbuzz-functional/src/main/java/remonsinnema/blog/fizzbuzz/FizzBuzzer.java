package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;


public class FizzBuzzer implements Function<Integer, String> {

  private final Function<Integer, String> replaceNumberWithStringRepresentation
      = n -> Integer.toString(n);
  private final Collection<ReplaceNumberWithFixedText> replacers = Arrays.asList(
      new ReplaceNumberWithFixedText(3, "Fizz"),
      new ReplaceNumberWithFixedText(5, "Buzz")
  );

  @Override
  public String apply(Integer n) {
    return numberReplacerFor(n).apply(n);
  }

  private Function<Integer, String> numberReplacerFor(Integer n) {
    for (ReplaceNumberWithFixedText replacer : replacers) {
      if (replacer.test(n)) {
        return replacer;
      }
    }
    return replaceNumberWithStringRepresentation;
  }

}
