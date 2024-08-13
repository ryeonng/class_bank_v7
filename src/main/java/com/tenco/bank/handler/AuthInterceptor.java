package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component // IoC 대상 (싱글톤 패턴)
public class AuthInterceptor implements HandlerInterceptor {
	
	// preHandle 동작 흐름 (단, 스프링부트 설정파일, 설정 클래스에 등록이 되어야 동작함)
	// 컨트롤러가 들어오기 전에 이 메서드가 먼저 동작
	// return 값 -- true : 컨트롤러 안으로 들여 보낸다. --false : 컨트롤러 안으로 들어갈 수 없다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) { // principal == null , 컨트롤러 안으로 못 들어가도록
			throw new UnAuthorizedException("로그인을 먼저 해주세요.", HttpStatus.UNAUTHORIZED);
		}
		return true;
	}
	
	
	// postHandle
	// 뷰가 렌더링되기 바로 전에 콜백 되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	// 요청 처리가 완료된 후. 즉, 뷰가 완전히 렌더링 된 후에 호출 된다.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
