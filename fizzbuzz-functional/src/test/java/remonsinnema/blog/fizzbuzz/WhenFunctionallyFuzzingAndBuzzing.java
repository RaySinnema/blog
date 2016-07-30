package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFunctionallyFuzzingAndBuzzing {

  private final FizzBuzzer fizzBuzzer = new FizzBuzzer();

  @Test
  public void shouldReplaceMultiplesOfThreeWithFizzAndMultiplesOfFiveWithBuzz() {
    assertFizzBuzz("1", 1);
    assertFizzBuzz("2", 2);
    assertFizzBuzz("Fizz", 3);
    assertFizzBuzz("4", 4);
    assertFizzBuzz("Buzz", 5);
  }

  private void assertFizzBuzz(String expected, int value) {
    assertEquals(Integer.toString(value), expected, fizzBuzzer.apply(value));
  }

}
