package stream;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx2 {
    public static void main(String[] args) {

        File[] files = { new File("file1.txt"),
                new File("file2.txt"), new File("file3.txt"),
                new File("file4.txt") };

        // 파일 이름 출력
        for (File file : files) {
            System.out.println(file.getName() + "\t");
        }

        // 파일명 추출 후 리스트에 담기
        List<String> fList = new ArrayList<>();
        for (File file : files) {
            fList.add(file.getName());
        }
        System.out.println(fList);

        Stream<File> stream = Stream.of(new File("file1.txt"),
                new File("file2.txt"), new File("file3.txt"),
                new File("file4.txt"));

        // Stream<String> names = stream.map(f -> f.getName());
        // names.forEach(name -> System.out.println(name));

        // IllegalStateException: stream has already been operated upon or closed
        List<String> list = stream.map(f -> f.getName()).collect(Collectors.toList());
        System.out.println(list);

        // stream.map(f -> f.getName())
        // .collect(Collectors.toList())
        // .forEach(name -> System.out.println(name));

        List<String> list2 = Arrays.asList("abc", "def", "melon", "text", "apple");
        // 대문자 변경 후 새로운 리스트로 생성, 출력
        List<String> upperList = new ArrayList<>();
        list2.forEach(s -> upperList.add(s.toUpperCase()));
        System.out.println(upperList);

        // 스트림
        list2.stream().map(s -> s.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);
    }
}
