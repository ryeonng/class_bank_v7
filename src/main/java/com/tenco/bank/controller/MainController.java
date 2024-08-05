package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
}
