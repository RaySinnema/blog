package remonsinnema.blog.fizzbuzz;


public class FizzBuzz {

  public String get(int n) {
    Term term = new Term(3, "Fizz");
    if (n == term.getValue()) {
      return term.getText();
    }
    term = new Term(5, "Buzz");
    if (n == term.getValue()) {
      return term.getText();
    }
    return Integer.toString(n);
  }

}
