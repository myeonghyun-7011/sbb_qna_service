package com.exam.sbb.answer;

import com.exam.sbb.DataNotFoundException;
import com.exam.sbb.question.Question;
import com.exam.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;

  public Answer create(Question question, String content, SiteUser author) {
    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    answer.setAuthor(author); // 질문에 대한 답변 작성자 저장.
    question.addAnswer(answer);

    answerRepository.save(answer);

    return answer;
  }

  public Answer getAnswer(Integer id) {
    return answerRepository.findById(id).orElseThrow(() -> new DataNotFoundException("answer not found"));
  } // 답변을 찾아와라 -> 없으면 에러메세지 출력


  // 답변 수정
  public void modify(Answer answer, String content) {
    answer.setContent(content);
    answer.setModifyDate(LocalDateTime.now());
    answerRepository.save(answer);
  }

  // 답변 삭제
  public void delete(Answer answer) {
    answerRepository.delete(answer);
  }

  // 추천
  public void vote(Answer answer, SiteUser siteUser) {
    answer.getVoter().add(siteUser);
    answerRepository.save(answer);
  }
}
