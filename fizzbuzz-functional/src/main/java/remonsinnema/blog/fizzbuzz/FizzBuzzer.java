package remonsinnema.blog.fizzbuzz;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class FizzBuzzer implements Function<Integer, String> {

  private final Function<Integer, String> defaultReplacer
      = n -> Integer.toString(n);
  private final Collection<ReplaceMultipleWithFixedText> replacers = Arrays.asList(
      new ReplaceMultipleWithFixedText(3, "Fizz"),
      new ReplaceMultipleWithFixedText(5, "Buzz")
  );

  @Override
  public String apply(Integer n) {
    return numberReplacersFor(n)
        .map(function -> function.apply(n))
        .collect(Collectors.joining());
  }

  private Stream<Function<Integer, String>> numberReplacersFor(Integer n) {
    Iterator<Function<Integer, String>> result = replacers.stream()
        .filter(replacer -> replacer.test(n))
        .map(replacer -> (Function<Integer, String>) replacer)
        .iterator();
    return result.hasNext()
        ? StreamSupport.stream(Spliterators.spliteratorUnknownSize(result, 0), false)
        : Stream.of(defaultReplacer);
  }

}
