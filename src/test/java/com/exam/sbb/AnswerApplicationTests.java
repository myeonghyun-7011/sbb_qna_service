package com.exam.sbb;

import com.exam.sbb.answer.Answer;
import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AnswerApplicationTests {

  @Autowired // 답변에대한 질문 객체가 있어야만 실행가능함.
  private QuestionRepository questionRepository;
  @Autowired
  private AnswerRepository answerRepository;
  private int lastSampleDataId;

  @BeforeEach
    // 1순위로 실행됨.
  void beforeEach() {
    clearData(); //data를 전부 날려버릴 함수.
    createSampleData(); // data생성.

  }

  private void clearData() {
    questionRepository.disableForeignKeyChecks(); //foreign key를 비활성화 시킴
    answerRepository.truncate(); //테이블 삭제 데이터전부
    questionRepository.enableForeignKeyChecks(); //foreign key를 활성화 시킴
  }

  private void createSampleData() {

  }

  @Test
  void 저장() {
    Question q = questionRepository.findById(2).get();

    Answer a = new Answer();
    a.setContent("네 자동으로 생성됩니다.");
    a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
    a.setCreateDate(LocalDateTime.now());
    answerRepository.save(a);
  }


}
