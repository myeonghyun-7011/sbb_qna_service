package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import com.exam.sbb.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Page<Question> getList(String kw, int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    sorts.add(Sort.Order.desc("id"));

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 게시물 10까지 가능

    if(kw == null || kw.trim().length() == 0) {
      return questionRepository.findAll(pageable);
    }

    return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_Author_usernameContains(kw, kw, kw, kw, kw, pageable);
  }

  public Question getQuestion(int id) throws DataNotFoundException {
    Optional<Question> question = this.questionRepository.findById(id); // Optional  예외 방지
    if (question.isPresent()) { // 예외 방지중.
      Question question1 = question.get();
      question1.setView(question1.getView() + 1); // 질문을 찾았으면 그 질문에 +1 증가
      this.questionRepository.save(question1); // 저장.
      return question1;
    } else {
      throw new DataNotFoundException("question not found");
    }
  }

  public void create(String subject, String content, SiteUser author) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());
    q.setAuthor(author);

    this.questionRepository.save(q);
  }

  public void modify(Question question, String subject, String content) {
    question.setSubject(subject);
    question.setContent(content);
    question.setModifyDate(LocalDateTime.now());

    this.questionRepository.save(question);
  }

  // 질문 삭제
  public void delete(Question question) {
    this.questionRepository.delete(question);
  }

  // 질문 투표
  public void vote(Question question, SiteUser siteUser) {
    question.getVoter().add(siteUser);
    questionRepository.save(question);
  }
}
