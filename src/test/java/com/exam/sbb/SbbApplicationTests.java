package com.exam.sbb;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test // 외워야함.
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장 // save시점으로 id생성


		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		assertThat(q1.getId()).isGreaterThan(0); // id는 최소 0 보다는 크다.
		assertThat(q2.getId()).isGreaterThan(q1.getId()); // 첫번째 id보다크다

	}

	@Test
	void testJpa2() {
		// select * from question = > findAll 을해서 all에 담아줌.
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size()); // 질문이 2개 이기때문에 2

		Question q = all.get(0); // 1번게시물 가져오기
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

}
