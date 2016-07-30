package remonsinnema.blog.fizzbuzz;

import java.util.function.Function;
import java.util.function.Predicate;


public class Fizzer implements Function<Integer, String>, Predicate<Integer> {

  @Override
  public boolean test(Integer n) {
    return n == 3;
  }

  @Override
  public String apply(Integer n) {
    return "Fizz";
  }

}
