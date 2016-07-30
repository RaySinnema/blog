package remonsinnema.blog.fizzbuzz;

import java.util.function.Function;


public class FizzBuzzer implements Function<Integer, String> {

  private final Function<Integer, String> replaceNumberWithStringRepresentation
      = n -> Integer.toString(n);
  private final Fizzer replaceNumberWithFizz = new Fizzer();

  @Override
  public String apply(Integer n) {
    return numberReplacerFor(n).apply(n);
  }

  private Function<Integer, String> numberReplacerFor(Integer n) {
    return replaceNumberWithFizz.test(n)
        ? replaceNumberWithFizz
        : replaceNumberWithStringRepresentation;
  }

}
