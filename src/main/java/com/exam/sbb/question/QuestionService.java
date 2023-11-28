package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {
  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public List<Question> getList() {
    return questionRepository.findAll();
  }

  public Question getQuestion(int id) {
    return  questionRepository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("no %d question not found".formatted(id)));

  }

  public void create(String subject, String content) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q);
  }

  public Page<Question> getList(int page) {
    Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 게시물 10까지 가능
    return this.questionRepository.findAll(pageable);
  }

}
