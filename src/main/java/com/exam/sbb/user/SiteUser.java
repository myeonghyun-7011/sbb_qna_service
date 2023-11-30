package com.exam.sbb.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SiteUser { // class 이름을 user라 해도 상관x , 그런데 스프링 시큐리티는 user라는 클래스가 이미 정의되어있음.

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(unique = true)
  private String email;
}