package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // IoC의 대상이 된다. (싱글톤 패턴으로 관리 됨)
public class GlobalControllerAdvice {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody // 데이터 반환
	public ResponseEntity<Object> handleResourceNotFoundException(Exception e) {
		System.out.println("GlobalControllerAdvice : 오류 확인 : ");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
	}
	
}
