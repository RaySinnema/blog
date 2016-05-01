package remonsinnema.blog.fizzbuzz;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WhenFizzingAndBuzzing {

  private final FizzBuzz fizzbuzz = new FizzBuzz();

  @Test
  public void shouldReplaceWithFizzAndBuzz() {
    assertEquals("1", "1", fizzbuzz.get(1));
  }

}
