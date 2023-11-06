package com.exam.sbb.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller //스프링부트로 사용하기 위해 필수 작성
public class MainController {
  public int increaseNo = -1;

  @RequestMapping("/sbb")
  // 이렇게 접속이 되면
  // 아래 함수의 리턴값을 그대로 브라우저에 표시
  // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을  바디에 담는다.
  @ResponseBody // html body 출력
  public String index() {
    // index 메서드 이렇게 실행함.
    // 문자열 이기 때문에 String 사용.

    System.out.println("첫 시작");
    return "안녕하세요. asdfsaf"; // 화면에 출력
  }

  @GetMapping("/page1")
  @ResponseBody
  public String showGet() {
    return """
        <form method="POST" action="/page2"/>
           <input type="number" name="age" placeholder="나이 입력" />
           <input type="submit" value="page2로 POST 방식으로 이동" />
         </form>
         """;
  }

  @PostMapping("/page2")
  @ResponseBody
  public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. POST 방식으로 오신것을 환영합니다.</h1>
        """.formatted(age);
  }

  @GetMapping("/page2")
  @ResponseBody
  public String showPost(@RequestParam(defaultValue = "0") int age) {
    return """
         <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. GET 방식으로 오신것을 환영합니다.</h1>
          
        """.formatted(age);
  }

  @GetMapping("/plus")
  @ResponseBody
  public int showPlus(int a, int b) {
    return a + b;
  }

  @GetMapping("/plus2") //jsp
  @ResponseBody
  public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int a = Integer.parseInt(req.getParameter("a"));
    int b = Integer.parseInt(req.getParameter("b"));

    resp.getWriter().append(a + b + "");

  }

  @GetMapping("/minus")
  @ResponseBody
  public int showMinus(int a, int b) {
    return a - b;
  }

  @GetMapping("/increase")
  @ResponseBody
  public int showIncrease() {
    increaseNo++;
    return increaseNo;
  }

  @GetMapping("/gugudan")
  @ResponseBody
  public String showGugudan(int dan, int limit) {
    String rs = "";
    for (int i = 1; i <= limit; i++) {
      rs += "%d * %d = %d <br>\n".formatted(dan, i, dan * i);
    }
    return rs;
  }

  @GetMapping("/gugudan1")
  @ResponseBody
  public String showGugudan1(Integer dan, Integer limit) {//int = null을 허용x / integer= null 허용
    if (dan == null) {
      dan = 9;
    }

    if (limit == null) {
      limit = 9;
    }

    final Integer finalDan = dan;
    return IntStream.rangeClosed(1, limit)
        .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
        .collect(Collectors.joining("<br>\n")); //br을 기준으로 합쳐줌.
  }

  @GetMapping("/mbti/{name}") // ?를 안쓰고 @PathVariable name 값이 중괄호name에 들어옴.
  @ResponseBody
  public String mbti(@PathVariable String name) {
    return switch (name) {
      case "홍길순" -> {
        char j = 'J';
        yield "INF" + j;
      }
      case "임꺽정" -> "ESFJ";
      case "홍길동", "김명현" -> "INFP";
      default -> "모름";
    };
  }
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
        .get(); //반복문

    return article;
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
  @GetMapping("/modifyarticle/{id}")
  @ResponseBody
  public String modifyarticle(@PathVariable int id, String title, String body) {

    Article article = articles // id가 1번인 게시물이 앞에서 3번째
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .get(); //반복문

    if(article == null){
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
    article.setTitle(title);
    article.setBody(body);


    return "%d번 게시물이 수정되었습니다.".formatted(article.getId());
  }


}
