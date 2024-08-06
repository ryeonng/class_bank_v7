package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.handler.exception.UnAuthorizedException;

@Controller // Ioc의 대상(싱글톤 패턴으로 관리된다.) -- 제어의 역전
public class MainController {

	// REST API 기반으로 주소를 설계할 수 있다.
	
	// 주소설계
	// http:localhost:8080/main-page
	
	@GetMapping({"/main-page", "/index"})
	// @ResponseBody // 데이터를 반환시킨다.
	public String mainPage() {
		System.out.println("mainPage() 메서드 호출 확인");
		// [JSP 파일 찾기 (yml 설정)] - 뷰 리졸버 (JSP 파일을 찾아주는 요소)
		// prefix : /WEB-INF/view
		//			/main 
		// suffix : .jsp
		return "/main";
	}
	
	// TODO - 삭제예정
	// 주소 설계
	// http://localhost:8080/error-test1/true
	// http://localhost:8080/error-test1/false
	
	@GetMapping("/error-test1")
	public String errorPage() {
		if(true) {
			throw new RedirectException("잘못된 요청입니다.", HttpStatus.NOT_FOUND); // 404
		}
		return "main"; // 에러가 없다면, main 페이지로 이동
	}
	
	// http://localhost:8080/error-test2
	@GetMapping("/error-test2")
	public String errorData2() {
		if(true) {
			throw new DataDeliveryException("잘못된 데이터 입니다.", HttpStatus.BAD_REQUEST);
		}
		return "main"; 
	}
	
	// http://localhost:8080/error-test3
	@GetMapping("/error-test3")
	public String errorData3() {
		if(true) {
			throw new UnAuthorizedException("인증되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		}
		return "main"; 
	}
	
}
