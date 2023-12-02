package com.exam.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
  @Size(min = 3, max = 25, message = "사용자ID는 3자 이상, 25자 이하로 입력해주세요.")
  @NotEmpty(message = "사용자ID는 필수항목입니다.")
  private String username;

  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  private String password1;

  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
  private String password2;

  @NotEmpty(message = "이메일은 필수항목입니다.")
  @Email
  private String email;

  @NotEmpty(message = "핸드폰 번호는 필수항목입니다.")
  private int phoneNo;

  @NotEmpty(message = "주소 필수항목입니다.")
  @Email
  private String address;

}

