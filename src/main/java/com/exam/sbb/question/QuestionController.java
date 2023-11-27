package com.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입
// 컨트롤러는 Repository가 있는지 몰라야한다.
// 서비스는 브라우저라는 것이 이 세상에 존재하는지 몰라야 한다.
// 리포지터리는 서비스가 이 세상이 있는지 몰라야한다.
// 서비스는 컨트롤러르 몰라야한다.
// DB는 리포지터리를 몰라야한다.
// SPIRNG DATA JPA는 MySql을 몰라야한다.
// SPRING DATA JPA(리포지터리) -> JPA -> 하이버네이트 -> JDBC -> MySQL Driver -> MySQL

public class QuestionController {
  // @Autowired 필드 주입 위에 리콰이얼드 사용하면 final만사용하면됨.

  private final QuestionService questionService; // QuestionService 생성



  @GetMapping("/question/list")
  // 이 자리에 @ResponseBody가 없으면 resources/templates/question_list.html 파일을 뷰로 삼는다.
  public String list(Model model) { // model 외워야함.
    List<Question> questionList = questionService.getList();
    // 미리 실행된 question_list.html에서
    // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
    model.addAttribute("questionList", questionList);
    return "question_list";
  }

  @GetMapping(value = "/question/detail/{id}")
  public String detail(Model model, @PathVariable int id) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);

    return "question_detail";
  }
}
