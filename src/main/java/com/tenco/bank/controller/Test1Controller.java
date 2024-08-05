package com.tenco.bank.controller;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.model.User;

// Controller 로 리턴 타입을 String으로 지정 시, 뷰리졸버가 동작 -> JSP 파일 찾아 렌더링 처리
// RestController -> 데이터를 반환 처리한다.

// Controller : JSP 파일 / RestController : 데이터 반환

@RestController // @Controller + @ResponseBody 로 응답 처리 = 데이터 반환
public class Test1Controller {

	// http://localhost:8080/test1
	@GetMapping("/test1")
	public User test1() {
		
		// Gson -> JSON 형식으로 변환 -> String 응답 처리
		
		try {
			int result = 10 / 0;
		} catch (Exception e) {
			throw new UnAuthorizedException("인증 되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		return User.builder().username("길동").password("asd123").build();
	}
	
}
