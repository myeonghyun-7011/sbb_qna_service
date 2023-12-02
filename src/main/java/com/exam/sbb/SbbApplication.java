package com.exam.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {

		SpringApplication.run(SbbApplication.class, args);
	}

	// 스프링 시스템에 객체를 등록한다.
	// @Configuration 라는 어노테이션을 가진 클래스에서만 사용 가능하다.

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
