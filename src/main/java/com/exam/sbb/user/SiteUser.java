package com.exam.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SiteUser { // class 이름을 user라 해도 상관x, 그런데 스프링 시큐리티는 user라는 클래스가 이미 정의되어 있다.

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private int phoneNo;

  @Column(unique = true)
  private String address;

  public SiteUser(long id) {
    this.id = id;
  }
}
