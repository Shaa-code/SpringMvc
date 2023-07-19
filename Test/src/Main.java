@FunctionalInterface
interface B {
    String test(String a, String b);
}


public class Main {
    public static void main(String[] args) {
        System.out.println({(c,d) -> {
            return c + d;
        });
    }
}