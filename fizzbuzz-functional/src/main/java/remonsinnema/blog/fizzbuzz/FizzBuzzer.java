package remonsinnema.blog.fizzbuzz;

import java.util.function.Function;


public class FizzBuzzer implements Function<Integer, String> {

  @Override
  public String apply(Integer n) {
    return Integer.toString(n);
  }

}
