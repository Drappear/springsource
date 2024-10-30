package stream;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lambda.Student;

public class StreamEx3 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("바둑", "바나나", "포도", "딸기", "바질", "강아지");

        // '바' 로 시작하는 문자 출력
        for (String str : list) {
            if (str.startsWith("바")) {
                System.out.println(str);
            }
        }

        // 스트림
        // filter(Predicate<? super String> predicate)
        list.stream().filter(s -> s.startsWith("바")).forEach(System.out::println);

        List<Student> list2 = Arrays.asList(new Student("홍길동", 90, 96),
                new Student("김수정", 80, 85),
                new Student("송혜교", 70, 95),
                new Student("김희원", 95, 82));

        // 성이 김으로 시작하는 학생의 이름 출력
        for (Student student : list2) {
            if (student.getName().startsWith("김")) {
                System.out.println(student.getName());
            }
        }

        list2.stream().filter(s -> s.getName().startsWith("김")).forEach(System.out::println);

        // 성이 김으로 시작하는 학생들만 새로운 리스트에 담고 출력
        list2.stream()
                .map(s -> s.getName())
                .filter(s -> s.startsWith("김"))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        list2.stream()
                .filter(s -> s.getName().startsWith("김")) // 김으로 시작하는 student
                .peek(s -> System.out.println(s)) // 중간결과 출력
                .map(s -> s.getName()) // 이름만 모으기
                .collect(Collectors.toList()) // 리스트로 수집
                .forEach(System.out::println); // 출력
        // 짝수 출력
        IntStream.rangeClosed(1, 10).filter(i -> i % 2 == 0).forEach(System.out::println);

        // distinct() : 중복제거
        list = Arrays.asList("바둑", "바나나", "포도", "딸기", "바질", "강아지", "바둑");
        list.stream().distinct().forEach(System.out::println);

        // 파일 확장자를 추출하고 중복 제거한 후 출력
        File[] files = { new File("file1.txt"),
                new File("file2.txt"),
                new File("Ex1"),
                new File("Ex2.bak"),
                new File("test.java") };

        // 스트림 변환 => 중간연산(이름 모으기, 확장자 추출, 중복제거) => 최종연산 => 출력
        Arrays.stream(files)
                .map(f -> f.getName())
                .filter(s -> s.contains(".")) // 확장자가 있는 파일명
                .map(s -> s.substring(s.lastIndexOf(".") + 1)) // 확장자 수집
                .distinct()
                .forEach(System.out::println);

        list.stream().skip(2).limit(3).forEach(System.out::println);
    }

}
