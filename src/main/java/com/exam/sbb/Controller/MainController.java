package com.exam.sbb.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller //스프링부트로 사용하기 위해 필수 작성
public class MainController {

  @GetMapping("/saveSessionAge/{name}/{value}")
  @ResponseBody
  public String showSaveSessionAge(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);

    return "세션변수 %s의 값이 %s로 설정되었습니다.".formatted(name, value);
  }

  @GetMapping("/getSessionAge/{name}")
  @ResponseBody
  public String getSessionAge(@PathVariable String name, HttpSession session) {
    // req에 쿠기가 들어있음. 쿠키 : 키,값으로 구성.
    // req => 쿠기 => JSESSIONID => 세션을 얻을 수있다.

    String value = (String) session.getAttribute(name);

    return "세션변수 %s의 값은 %s입니다.".formatted(name, value);
  }

  /*private List<Article> articles = new ArrayList<>();*/
  private List<Article> articles = new ArrayList<>( // test data 생성.
      Arrays.asList(
          new Article("제목","내용"),
          new Article("제목","내용")
      )
  );
// 값이 고정된 값이라 불변성임.
  @GetMapping("/addArticle")
  @ResponseBody
  public String addArticle(String title, String body) {

    Article article = new Article(title, body);
    articles.add(article); //articles 에 article을 담아줌.

    return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
  }
  @GetMapping("/article/{id}")
  @ResponseBody
  public Article getArticle(@PathVariable int id) {

    Article article = articles // id가 1번인 게시물이 앞에서 3번째
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null); //반복문

    return article;
  }

  @GetMapping("/modifyarticle/{id}")
  @ResponseBody
  public String modifyarticle(@PathVariable int id, String title, String body) {

    Article article = articles // id가 1번인 게시물이 앞에서 3번째
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null); //반복문

    if(article == null){
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
    article.setTitle(title);
    article.setBody(body);


    return "%d번 게시물이 수정되었습니다.".formatted(article.getId());
  }
  @GetMapping("/deleteArtice/{id}")
  @ResponseBody
  public String deleteArtice(@PathVariable int id) {

    Article article = articles // id가 1번인 게시물이 앞에서 3번째
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null); //반복문

    if(article == null){
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
    articles.remove(article);

    return "%d번 게시물이 삭제되었습니다.".formatted(article.getId());
  }

  @GetMapping("/addPersonOnlyWay")
  @ResponseBody
  public Person addPersonOnlyWay(int id, int age, String name) {
    Person p = new Person(id,age,name);
    return p;
  }

  // 액션 메서드
  @GetMapping("/addPerson/{id}")
  @ResponseBody
  public Person addPerson(Person p) {
    return p;
  }

// 실행안됨
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  private class Person {
    private int id;
    private int age;
    private String name;

    public Person(int age, String name) {
      this.age = age;
      this.name = name;
    }
  }


  @AllArgsConstructor //lombok사용  /이용하여 alt + ins 컨스트럭트 생성이랑 같음./ alt+7 생성자 확인가능
  @Getter
  @Setter
  private class Article {
    private static int lastId = 0;
    private int id;
    private String title;
    private String body;

    public Article(String title, String body) { // 생성자 추가.
      this(++lastId, title, body); //this 는 Article 객체를 가리킴.
    }
  }

}
