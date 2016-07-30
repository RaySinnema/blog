package remonsinnema.blog.fizzbuzz;

import java.util.function.Function;
import java.util.function.Predicate;


public class ReplaceMultipleWithFixedText implements Function<Integer, String>, Predicate<Integer> {

  private final int target;
  private final String replacement;

  public ReplaceMultipleWithFixedText(int target, String replacement) {
    this.target = target;
    this.replacement = replacement;
  }

  @Override
  public boolean test(Integer n) {
    return n % target == 0;
  }

  @Override
  public String apply(Integer n) {
    return replacement;
  }

}
