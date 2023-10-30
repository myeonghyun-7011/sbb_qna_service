package com.exam.sbb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    return "안녕하세요."; // 화면에 출력
  }
}
