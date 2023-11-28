package com.exam.sbb.question;

import com.exam.sbb.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
