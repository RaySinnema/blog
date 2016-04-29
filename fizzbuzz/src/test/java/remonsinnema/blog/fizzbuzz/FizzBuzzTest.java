package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class FizzBuzzTest {

  private final FizzBuzz fizzbuzz = new FizzBuzz();

  @Test
  public void test() {
    assertFizzBuzz("1", 1);
    assertFizzBuzz("2", 2);
    assertFizzBuzz("Fizz", 3);
    assertFizzBuzz("4", 4);
    assertFizzBuzz("Buzz", 5);
    assertFizzBuzz("Fizz", 6);
    assertFizzBuzz("7", 7);
    assertFizzBuzz("8", 8);
    assertFizzBuzz("Fizz", 9);
    assertFizzBuzz("Buzz", 10);
    assertFizzBuzz("11", 11);
    assertFizzBuzz("Fizz", 12);
    assertFizzBuzz("13", 13);
    assertFizzBuzz("14", 14);
    assertFizzBuzz("FizzBuzz", 15);
  }

  private void assertFizzBuzz(String expected, int n) {
    assertEquals(Integer.toString(n), expected, fizzbuzz.get(n));
  }

}
