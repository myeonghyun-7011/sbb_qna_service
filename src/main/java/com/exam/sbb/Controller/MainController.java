package com.exam.sbb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller //스프링부트로 사용하기 위해 필수 작성
public class MainController {
  public int increaseNo = -1;
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

  @GetMapping("/plus")
  @ResponseBody
  public int showPlus(int a, int b) {
    return a + b;
  }

  @GetMapping("/plus2") //jsp
  @ResponseBody
  public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int a = Integer.parseInt(req.getParameter("a"));
    int b = Integer.parseInt(req.getParameter("b"));

    resp.getWriter().append(a + b + "");

  }

  @GetMapping("/minus")
  @ResponseBody
  public int showMinus(int a, int b) {
    return a-b;
  }

  @GetMapping("/increase")
  @ResponseBody
  public int showIncrease() {
    increaseNo++;
    return increaseNo;
  }

  @GetMapping("/gugudan")
  @ResponseBody
  public String showGugudan(int dan, int limit) {
    String rs = "" ;
    for(int i = 1; i <= limit; i++) {
      rs += "%d * %d = %d <br>\n".formatted(dan, i, dan * i);
    }
    return rs;
  }
  @GetMapping("/gugudan1")
  @ResponseBody
  public String showGugudan1(Integer dan, Integer limit) {//int = null을 허용x / integer= null 허용
    if(dan == null) {
      dan = 9;
    }

    if(limit == null) {
      limit = 9;
    }

    final Integer finalDan = dan;
    return IntStream.rangeClosed(1, limit)
        .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan *i))
        .collect(Collectors.joining("<br>\n")); //br을 기준으로 합쳐줌.
  }

  @GetMapping("/mbti/{name}") // ?를 안쓰고 @PathVariable name 값이 중괄호name에 들어옴.
  @ResponseBody
  public String mbti(@PathVariable String name) {
    return switch (name) {
      case "홍길순" -> {
        char j = 'J';
        yield  "INF" + j;
      }
      case "임꺽정" -> "ESFJ";
      case "홍길동" , "김명현" -> "INFP";
      default -> "모름";
    };
  }

}
