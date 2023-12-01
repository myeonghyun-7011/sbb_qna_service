package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionService;
import com.exam.sbb.user.SiteUser;
import com.exam.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

  private final QuestionService questionService;
  private final AnswerService answerService;

  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create/{id}")
  public String createAnswer(Principal principal, Model model, @PathVariable int id, @Valid AnswerForm answerForm, BindingResult bindingResult) {

    Question question = questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question_detail";
    }

    SiteUser siteUser = userService.getUser(principal.getName());
    
    // 답변 등록 시작
    Answer answer = answerService.create(question, answerForm.getContent(), siteUser);
    // 답변 등록 끝
    
    return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
    Answer answer = answerService.getAnswer(id);

    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    answerForm.setContent(answer.getContent());
    return "answer_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                             @PathVariable("id") Integer id, Principal principal) {
    // 오류나면 되돌아 가는 것이 아닌 다시 이 view를 보여줘라.
    if (bindingResult.hasErrors()) {
      return "answer_form";
    }

    Answer answer = answerService.getAnswer(id);
    // 해당 id를 받아와서 처리, 없으면 null 처리 해주는 거고
    // 없으면 answerService getAnswer에서 에러 처리를 해줌
    // if null을 안해도 상관 없음.

    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    answerService.modify(answer, answerForm.getContent());
    return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
    Answer answer = this.answerService.getAnswer(id);

    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }

    answerService.delete(answer);
    return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String answerVote(Principal principal, @PathVariable("id") Integer id) {
    Answer answer = answerService.getAnswer(id);
    SiteUser siteUser = userService.getUser(principal.getName());

    answerService.vote(answer, siteUser);
    return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
  }
}