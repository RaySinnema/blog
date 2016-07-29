package remonsinnema.blog.fizzbuzz;

import java.util.function.Function;


public class FizzBuzzer implements Function<Integer, String> {

  @Override
  public String apply(Integer n) {
    return numberReplacerFor(n).apply(n);
  }

  private Function<Integer, String> numberReplacerFor(Integer n) {
    return n == 3
        ? i -> "Fizz"
        : i -> Integer.toString(i);
  }

}
