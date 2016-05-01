package remonsinnema.blog.fizzbuzz;


public class FizzBuzz {

  public String get(int n) {
    MultipleReplacer replacer = new MultipleReplacer(3, "Fizz");
    if (n == replacer.getValue()) {
      return replacer.getText();
    }
    replacer = new MultipleReplacer(5, "Buzz");
    if (n == replacer.getValue()) {
      return replacer.getText();
    }
    return Integer.toString(n);
  }

}
