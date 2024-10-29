package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
interface Lambda6 {
    void run();
}

public class LambdaEx5 {

    static void execute(Lambda6 lambda) {
        lambda.run();
    }

    static Lambda6 getRun() {
        return () -> System.out.println("함수형 인터페이스");
    }

    private static List<Student> list = Arrays.asList(new Student("홍길동", 90, 96),
            new Student("성춘향", 80, 85));

    static void printScore(Function<Student, Integer> f) {
        for (Student student : list) {
            System.out.println(f.apply(student));
        }
    }

    // 이름 출력 printName
    static void printName(Function<Student, String> f) {
        for (Student student : list) {
            System.out.println(f.apply(student));
        }
    }

    public static void main(String[] args) {
        Lambda6 lambda = () -> System.out.println("Lambda6");
        lambda.run();

        // Lambda6 lambda6 = getRun();
        // execute(lambda6);
        execute(getRun());

        Function<Student, Integer> f = (s) -> s.getEng();
        // System.out.println(f.apply(list.get(0)));
        // System.out.println(f.apply(list.get(1)));

        // for (Student student : list) {
        // System.out.println(f.apply(student));
        // }

        // f = (s) -> s.getMath();
        // for (Student student : list) {
        // System.out.println(f.apply(student));
        // }

        System.out.println("영어점수");
        printScore((s) -> s.getEng());
        System.out.println("수학점수");
        printScore((s) -> s.getMath());
        System.out.println("이름");
        printName((s) -> s.getName());
    }
}
