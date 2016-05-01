package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFizzingAndBuzzing {

  private final FizzBuzz fizzbuzz = new FizzBuzz();

  @Test
  public void shouldReplaceWithFizzAndBuzz() {
    assertFizzBuzz("1", 1);
    assertFizzBuzz("2", 2);
    assertFizzBuzz("Fizz", 3);
    assertFizzBuzz("4", 4);
    assertFizzBuzz("Buzz", 5);
    assertFizzBuzz("Fizz", 6);
  }

  private void assertFizzBuzz(String expected, int n) {
    assertEquals(Integer.toString(n), expected, fizzbuzz.get(n));
  }

}
