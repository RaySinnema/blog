package remonsinnema.blog.fizzbuzz;


public class FizzBuzz {

  public String get(int n) {
    if (n == 3) {
      return "Fizz";
    }
    if (n == 5) {
      return "Buzz";
    }
    return Integer.toString(n);
  }

}
