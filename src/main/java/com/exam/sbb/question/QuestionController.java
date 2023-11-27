package com.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입
public class QuestionController {
  // @Autowired 필드 주입 위에 리콰이얼드 사용하면 final만사용하면됨.
  private final QuestionRepository questionRepository;



  @GetMapping("/question/list")
  // 이 자리에 @ResponseBody가 없으면 resources/templates/question_list.html 파일을 뷰로 삼는다.
  public String list(Model model) { // model 외워야함.
    List<Question> questionList = questionRepository.findAll();
    // 미리 실행된 question_list.html에서
    // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
    model.addAttribute("questionList", questionList);
    return "question_list";
  }
}
