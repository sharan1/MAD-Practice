public class InClass01a {

  public static void printNumbers(Integer num) {
    while (num <= 100) {
      if (num % 3 == 0 && num % 5 == 0) {
        System.out.println("FizzBuzz");
      } else if (num % 3 == 0) {
        System.out.println("Fizz");
      } else if (num % 5 == 0) {
        System.out.println("Buzz");
      } else {
        System.out.println(num);
      }
      num++;
    }
  }

  public static void main(String[] args) {
    new InClass01a().printNumbers(1);
  }
}
