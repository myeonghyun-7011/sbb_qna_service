package com.exam.sbb.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/signup")
  public String signup(UserCreateForm userCreateForm) {
    return "signup_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "signup_form";
    }

    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect",
          "2개의 패스워드가 일치하지 않습니다.");
      return "signup_form";
    }

    try {
      userService.create(userCreateForm.getUsername(),
          userCreateForm.getEmail(), userCreateForm.getPassword1());

    } catch (SignupUsernameDuplicatedException e) {
      bindingResult.reject("signupUsernameDuplicated", e.getMessage());
      return "signup_form";
    } catch (SignupEmailDuplicatedException e) {
      bindingResult.reject("signupEmailDuplicated", e.getMessage());
      return "signup_form";
    }

    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() {
    return "login_form";
  }

  @PostMapping("/login")
  public String loginCheck(String id, String pwd, HttpServletRequest request,
                           HttpServletResponse response, boolean rememberId) {
    if (!idCheck(id, pwd)) {
      return "redirect:/login";
    }

    HttpSession session = request.getSession();
    session.setAttribute("id", id);

    if (rememberId(rememberId)) {
      Cookie cookie = new Cookie("id", id);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("id", id);
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

  private boolean idCheck(String id, String pwd) {
    if (id.equals(id) && pwd.equals(pwd)) {
      return true;
    } else {
      return false;
    }
  }

  private boolean rememberId(boolean rememberId) {
    return rememberId;
  }

  private boolean isLogin(HttpServletRequest request) {
    HttpSession session = request.getSession();
    if (session.getAttribute("id") == null) {
      return false;
    } else {
      return true;
    }
  }
}