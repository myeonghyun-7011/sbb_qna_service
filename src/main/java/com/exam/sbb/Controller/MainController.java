package com.exam.sbb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //스프링부트로 사용하기 위해 필수 작성
public class MainController {
  @RequestMapping("/sbb") // 이렇게 접속이 되면
  public void index() { //이렇게 실행함.
    System.out.println("첫 시작");
  }
}
