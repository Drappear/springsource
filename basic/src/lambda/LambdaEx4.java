package lambda;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// 함수형 인터페이스 @FuntionalInterface

// 메소드는 프로토타입이 거의 일정하기 때문에 자주 쓰이는 형식의 메소드를 가지고 있는
// 함수형 인터페이스 선언 가능 => java.util.function 패키지에 정의

public class LambdaEx4 {
    public static void main(String[] args) {
        Supplier<Integer> s = () -> (int) (Math.random() * 100) + 1;

        System.out.println("1~100 : " + s.get());

        Supplier<LocalDateTime> s1 = () -> LocalDateTime.now();
        System.out.println(s1.get());

        Consumer<Integer> c = (i) -> System.out.println(i * i);
        c.accept(5);

        Consumer<String> c1 = (str) -> System.out.println(str);
        c1.accept("HELLO");

        Consumer<LocalDateTime> c2 = (d) -> System.out.println(d);
        c2.accept(LocalDateTime.now());

        Function<Integer, Integer> f1 = (i) -> i * i;
        System.out.println(f1.apply(5));

        // 일의 자리 없앤 후 리턴
        f1 = (i) -> i / 10 * 10;
        System.out.println(f1.apply(561));

        // Function<String, Integer> f2 = (str) -> Integer.parseInt(str);
        // Function<String, Integer> f2 = (str) -> Integer.valueOf(str);
        Function<String, Integer> f2 = Integer::parseInt;
        System.out.println(f2.apply("78"));

        Predicate<Integer> p1 = (i) -> i > 10;
        System.out.println(p1.test(25) ? "10보다 큼" : "10보다 작음");

        // 문자열 길이가 6자리 보다 큰지
        Predicate<String> p2 = (str) -> str.length() > 6;
        System.out.println(p2.test("abcdefg") ? "6자리 초과" : "6자리 이하");

        // 문자열에 대문자 A의 포함 여부
        p2 = (str) -> str.contains("A");
        System.out.println(p2.test("abcAefg") ? "A가 포함됨" : "A가 포함되지 않음");

        BiFunction<Integer, Integer, Integer> function = (x, y) -> x + y;
        System.out.println(function.apply(5, 8));

        BiFunction<String, String, String> function1 = (x, y) -> x.concat(y);
        System.out.println(function1.apply("java8", "람다"));
    }
}
