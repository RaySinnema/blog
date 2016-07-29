package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFunctionallyFuzzingAndBuzzing {

  private final FizzBuzzer fizzBuzzer = new FizzBuzzer();

  @Test
  public void shouldReplaceMultiplesOfThreeWithFizzAndMultiplesOfFiveWithBuzz() {
    assertEquals("1", "1", fizzBuzzer.apply(1));
  }

}
