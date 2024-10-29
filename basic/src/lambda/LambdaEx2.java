package lambda;

// interface 선언
// new를 직접적으로 할 수 없음
// 구현 클래스 필요
// 또는 익명 구현 클래스 사용

@FunctionalInterface // 일반 메소드가 2개 이상 들어오는걸 컴파일 단계에서 체크
interface MyfunctionalInterface1 {
    void method();
    // void print();
}

// class A implements MyfunctionalInterface1 {
// @Override
// public void method() {
// }
// }

public class LambdaEx2 {
    public static void main(String[] args) {
        // MyfunctionalInterface1 obj = new MyfunctionalInterface1() {
        // @Override
        // public void method() {
        // System.out.println("인터페이스 구현");
        // }
        // };
        // obj.method();

        // 익명 구현 객체를 식으로 작성 : 람다식
        MyfunctionalInterface1 obj = () -> System.out.println("인터페이스 구현");
        obj.method();

        obj = () -> {
            int i = 10;
            System.out.println(i * i);
        };
        obj.method();
    }

}
