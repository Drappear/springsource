package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project1.dto.CalcDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
public class SampleController {

    // @RequestMapping(path = "/basic", method = RequestMethod.GET)
    // public void basic() {
    // log.info("basic controller active");
    // }

    // void : templates 폴더 아래 경로로 인식
    // /basic => basic.html
    // /sample/ex2 => /sample/ex2.html

    // String : redirect 하는 경우이거나 template 파일명을 임의대로 지정하는 경우

    // 입력값 가져오기
    // 1) HttpServletRequest 사용 가능(입력값을 가져오는 용도로는 잘 사용하지 않음)
    // 2) 매개변수 선언(변수명과 이름을 맞추는게 편함)
    // 3) DTO 사용(POST 메소드가 끝난 후 보여지는 페이지에서 DTO사용 가능)
    // ㄴ> CalcDto => calcDto / LoginDto => loginDto

    // 컨트롤러가 가지고 있는 값을 화면과 공유하기
    // redirect로 움직이지 않은 경우
    // 1) ~~DTO : 기본(클래스명과 동일, 맨 첫글자만 소문자)
    // 2) 변수에 들어있는 값 : model.addAttribute("이름", 변수명/객체명)
    // 3) method(@ModelAttribute int bno) : bno 공유
    // -- method(@ModelAttribute("uDto") UserDto uDto) : 별칭 이용
    //
    // redirect로 움직이는 경우
    // 1) RedirectAttributes rttr 이용
    // - rttr.addAttribute("이름", 값) : 경로에 ? 다음에 따라가는 값 => ${param.이름}
    // - rttr.addFlashAttribute("이름", 값) : 세션을 이용하기에 보이지 않음 => ${이름}

    // Model 과 RedirectAttributes 차이 : 움직이는 방식 / 객체를 담을 수 있는지

    @GetMapping("/basic")
    public String basic(RedirectAttributes rttr) {
        log.info("basic 컨트롤러 동작");

        // session을 사용하는것과 동일하나 일시적 보관
        rttr.addFlashAttribute("addr", "seoul");

        return "redirect:/ex1";
    }

    @GetMapping("/basic2")
    public String basic2(RedirectAttributes rttr) {
        log.info("basic2 컨트롤러 동작");

        rttr.addAttribute("age", 15); // redirect 시 주소의 파라메터로 보냄
        rttr.addAttribute("name", "hong");

        // sendRedirect() => redirect:경로
        return "redirect:/ex1";
    }

    @GetMapping("/ex1")
    public void getEx1() {
        log.info("ex1 컨트롤러 동작");
    }

    @GetMapping("/sample/ex2")
    public void getEx2() {
        log.info("ex2 컨트롤러 동작");
    }

    @GetMapping("/ex3")
    public String getEx3() {
        log.info("ex3 컨트롤러 동작");
        return "test";
    }

    @GetMapping("/ex4")
    public String getEx4() {
        log.info("ex4 컨트롤러 동작");
        return "/sample/ex2";
    }

    @GetMapping("/sample/calc1")
    public void getCalc1() {

    }

    // @PostMapping("/sample/calc1")
    // public void postCalc1(int num1, int num2) {
    // log.info("calc 입력값");
    // log.info("{} + {} = {}", num1, num2, (num1 + num2));
    // }

    @PostMapping("/sample/calc1")
    public void postCalc1(CalcDto calcDto, Model model) {
        log.info("calc 입력값");
        log.info("{} + {} = {}", calcDto.getNum1(), calcDto.getNum2(), (calcDto.getNum1() + calcDto.getNum2()));

        int result = calcDto.getNum1() + calcDto.getNum2();
        // result값 화면 출력
        model.addAttribute("result", result);
    }

    @GetMapping("/sample/calc2")
    public void getCalc2() {

    }

    // @PostMapping("/sample/calc2")
    // public void postCalc2(int num1, int num2, String op) {
    // int result = 0;
    // switch (op) {
    // case "+":
    // result = num1 + num2;
    // break;
    // case "-":
    // result = num1 - num2;
    // break;
    // case "*":
    // result = num1 * num2;
    // break;
    // case "/":
    // result = num1 / num2;
    // break;
    // case "%":
    // result = num1 % num2;
    // break;
    // default:
    // break;
    // }
    // log.info("calc 입력값");
    // log.info("{} {} {} = {}", num1, op, num2, result);
    // }

    @PostMapping("/sample/calc2")
    public void postCalc2(CalcDto calcDto, Model model) {
        int result = 0;
        switch (calcDto.getOp()) {
            case "+":
                result = calcDto.getNum1() + calcDto.getNum2();
                break;
            case "-":
                result = calcDto.getNum1() - calcDto.getNum2();
                break;
            case "*":
                result = calcDto.getNum1() * calcDto.getNum2();
                break;
            case "/":
                result = calcDto.getNum1() / calcDto.getNum2();
                break;
            case "%":
                result = calcDto.getNum1() % calcDto.getNum2();
                break;
            default:
                break;
        }
        log.info("calc 입력값");
        log.info("{} {} {} = {}", calcDto.getNum1(), calcDto.getOp(),
                calcDto.getNum2(), result);
        model.addAttribute("result", result);
    }

}
