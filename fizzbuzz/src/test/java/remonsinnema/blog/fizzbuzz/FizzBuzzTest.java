package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class FizzBuzzTest {

  private final FizzBuzz fizzbuzz = new FizzBuzz();

  @Test
  public void test() {
    assertEquals("1", "1", fizzbuzz.get(1));
  }

}
