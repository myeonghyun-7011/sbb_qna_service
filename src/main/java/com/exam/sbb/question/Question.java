package com.exam.sbb.question;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
