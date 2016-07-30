package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;


public class FizzBuzzer implements Function<Integer, String> {

  private final Function<Integer, String> defaultReplacer
      = n -> Integer.toString(n);
  private final Collection<ReplaceMultipleWithFixedText> replacers = Arrays.asList(
      new ReplaceMultipleWithFixedText(3, "Fizz"),
      new ReplaceMultipleWithFixedText(5, "Buzz")
  );

  @Override
  public String apply(Integer n) {
    return numberReplacerFor(n).apply(n);
  }

  private Function<Integer, String> numberReplacerFor(Integer n) {
    return replacers.stream()
        .filter(replacer -> replacer.test(n))
        .map(replacer -> (Function<Integer, String>) replacer)
        .findFirst()
        .orElse(defaultReplacer);
  }

}
