package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;

import lombok.RequiredArgsConstructor;

// WebMvcConfigurer : 약속 - 설정파일
@Configuration // 하나의 클래스를 IOC 하고 싶을때 사용
@RequiredArgsConstructor
public class WebWvcConfig implements WebMvcConfigurer{

	@Autowired // DI
	private final AuthInterceptor authInterceptor;
	
	// @RequiredArgsConstructor : 생성자 대신 사용 가능
	
	// 우리가 만들어 놓은 AuthInterceptor를 등록해야 한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account//**")
				.addPathPatterns("/auth/**");
		
	}
}
