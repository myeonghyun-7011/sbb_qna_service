package com.exam.sbb.question;

import com.exam.sbb.answer.AnswerForm;
import com.exam.sbb.user.SiteUser;
import com.exam.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor // 생성자 주입
// 컨트롤러는 Repository가 있는지 몰라야 한다.
// 서비스는 브라우저라는 것이 이 세상에 존재하는지 몰라야 한다.
// 리포지터리는 서비스가 이 세상이 있는지 몰라야 한다.
// 서비스는 컨트롤러를 몰라야 한다.
// DB는 리포지터리를 몰라야 한다.
// SPRING DATA JPA는 MySQL을 몰라야 한다.
 // SPRING DATA JPA(리포지터리) -> JPA -> 하이버네이트 -> JDBC -> MySQL Driver -> MySQL
public class QuestionController {
  // @Autowired 필드 주입
  private final QuestionService questionService;

  private final UserService userService;

  @GetMapping("/list")
  // 이 자리에 @ResponseBody가 없으면 resources/templates/question_list.html 파일을 뷰로 삼는다.
  public String list(String kw, Model model, @RequestParam(defaultValue="0") int page) {

    Page<Question> paging = questionService.getList(kw, page);
    // 미리 실행된 question_list.html에서
    // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
    model.addAttribute("paging", paging);
    model.addAttribute("kw", kw);

    return "question_list";
  }

  @GetMapping(value = "/detail/{id}")
  public String detail(Model model, @PathVariable int id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);
    return "question_detail";
  }

  @PreAuthorize("isAuthenticated()") // 실행 하기 전에 권한체크를 해라.
  @GetMapping("/create") // question_form
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create") // question_form 처리
  //
  public String questionCreate(Principal principal, Model model, @Valid QuestionForm questionForm, BindingResult bindingResult) {
    SiteUser siteUser = userService.getUser(principal.getName());


    if(bindingResult.hasErrors()) {
      return "question_form";
    }

    questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    Question question = this.questionService.getQuestion(id);

    if(!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    questionForm.setSubject(question.getSubject());
    questionForm.setContent(question.getContent());
    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer id) {
    if (bindingResult.hasErrors()) {
      return "question_form";
    }

    Question question = questionService.getQuestion(id);

    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
    return String.format("redirect:/question/detail/%s", id);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);

    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }

    questionService.delete(question);
    return "redirect:/";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String questionVote(Principal principal, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);
    SiteUser siteUser = userService.getUser(principal.getName());

    questionService.vote(question, siteUser);
    return String.format("redirect:/question/detail/%s", id);
  }
}
