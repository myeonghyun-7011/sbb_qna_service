package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

  private final QuestionService questionService;
  private final  AnswerService answerService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model, @PathVariable int id,  String content) {
    Question question = questionService.getQuestion(id);
    // TODO: 답변을 저장한다.

    // 답변 등록 시작
    answerService.create(question, content);
    // 답변 등록 끝
    return String.format("redirect:/question/detail/%s", id);
  }
}