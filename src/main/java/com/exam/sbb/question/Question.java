package com.exam.sbb.question;

import com.exam.sbb.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity // 아래 Question 클새는 엔터티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에없다면, 자동으로 생성되어야한다.
public class Question {
  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
  private Integer id;

  @Column(length = 200) // varchar(200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  // onetomany는 fetch 타입은 lazy가 맞지만 eager(즉시로딩)로 바꿔줌.
  // mapby는  question 칼럼과 관련.
  // 작성해도되고 안해도됨. cascade 연쇄효과(그 질문행을 지우면 답변 데이터도 지워짐.)
  private List<Answer> answerList;
}
