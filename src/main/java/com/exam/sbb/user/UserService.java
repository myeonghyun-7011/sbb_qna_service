package com.exam.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // 생성자 생성
@Service
public class UserService {

  private final UserRepository userRepository;

  // 스프링이 책이짐지고 passwordEncoder 타입의 객체를 만들어야 하는 상황
  private final PasswordEncoder passwordEncoder;

  public SiteUser create(String username, String email, String password)
      throws SignupUsernameDuplicatedException, SignupEmailnameDuplicatedException {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));

    try {
      this.userRepository.save(user);
    }catch (DataIntegrityViolationException e) {
      if(userRepository.existsByUsername(username)) {
        // username 때문
        throw new SignupUsernameDuplicatedException("이미 사용중인  username 입니다.");
      }
      else {
        // email 때문
        throw new SignupEmailnameDuplicatedException("이미 사용중인 e-mail 입니다.");
      }
    }
    return user;
  }
}