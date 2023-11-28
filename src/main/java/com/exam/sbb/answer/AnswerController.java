package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

  private final QuestionService questionService;
  private final  AnswerService answerService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model, @PathVariable int id, @Valid AnswerForm answerForm, BindingResult bindingResult, String content) {
    Question question = questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question_detail";
    }

    // TODO: 답변을 저장한다.

    // 답변 등록 시작
    answerService.create(question, answerForm.getContent());
    // 답변 등록 끝
    return String.format("redirect:/question/detail/%s", id);
  }
}