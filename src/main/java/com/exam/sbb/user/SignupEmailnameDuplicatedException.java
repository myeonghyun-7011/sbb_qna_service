package com.exam.sbb.user;

public class SignupEmailnameDuplicatedException extends RuntimeException {
  public SignupEmailnameDuplicatedException(String message) {
    super(message);
  }
}
