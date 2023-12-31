package com.exam.sbb;

import com.exam.sbb.answer.AnswerRepository;
import com.exam.sbb.question.QuestionRepository;
import com.exam.sbb.user.UserRepository;
import com.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @BeforeEach
  void beforeEach() {
    clearData();
    createSampleData();
  }

  public static void createSampleData(UserService userService) {
    userService.create("admin", "admin@test.com", "1234");
    userService.create("user1", "user1@test.com", "1234");
  }

  private void createSampleData() {
    createSampleData(userService);
  }

  public static void clearData(UserRepository userRepository,
                               AnswerRepository answerRepository,
                               QuestionRepository questionRepository) {
    answerRepository.deleteAll();
    answerRepository.truncateTable();

    questionRepository.deleteAll();
    questionRepository.truncate();

    userRepository.deleteAll(); // DELETE FROM site_user;
    userRepository.truncateTable();
  }

  private void clearData() {
    clearData(userRepository, answerRepository, questionRepository);
  }

  @Test
  @DisplayName("회원가입이 가능하다.")
  public void t1() {
    userService.create("user2", "user2@eamil.com", "1234");
  }

}