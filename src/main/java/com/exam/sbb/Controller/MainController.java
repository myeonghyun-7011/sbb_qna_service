package com.exam.sbb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //스프링부트로 사용하기 위해 필수 작성
public class MainController {
  @RequestMapping("/sbb")
  // 이렇게 접속이 되면
  // 아래 함수의 리턴값을 그대로 브라우저에 표시
  // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을  바디에 담는다.
  @ResponseBody // html body 출력
  public String index() {
    // index 메서드 이렇게 실행함.
    // 문자열 이기 때문에 String 사용.

    System.out.println("첫 시작");
    return "안녕하세요. asdfsaf"; // 화면에 출력
  }

  @GetMapping("/page1")
  @ResponseBody
  public String showGet() {
    return """
        <form method="POST" action="/page2"/>
           <input type="number" name="age" placeholder="나이 입력" />
           <input type="submit" value="page2로 POST 방식으로 이동" />
         </form>
         """;
  }

  @PostMapping("/page2")
  @ResponseBody
  public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. POST 방식으로 오신것을 환영합니다.</h1>
        """.formatted(age);
  }

  @GetMapping("/page2")
  @ResponseBody
  public String showPost(@RequestParam(defaultValue = "0") int age) {
    return """
         <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. GET 방식으로 오신것을 환영합니다.</h1>
          
        """.formatted(age);
  }
}
