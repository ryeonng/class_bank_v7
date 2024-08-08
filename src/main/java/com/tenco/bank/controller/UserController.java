package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;

@Controller // IoC의 대상 (싱글톤 패턴으로 관리)
@RequestMapping("/user") // 대문 처리
public class UserController {

	private UserService userService;
	private final HttpSession session; // final : 초기화 필요 > HttpSession session
	
	@Autowired // 노란색 경고는 사용할 필요 없음 - 가독성 위해서 선언해도 된다.
	public UserController(UserService service, HttpSession session) {
		this.userService = service;
		this.session = session;
	}
	
	/**
	 * 회원 가입 페이지 요청
	 * 주소설계 - http://localhost:8080/user/sign-up
	 * @return signUp.jsp
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		// prefix : WEB-INF/view/
		// suffix : .jsp
		return "user/signUp"; // user폴더의 signUp.jsp 파일을 불러온다.
	}

	/**
	 * 회원가입 로직 처리 요청 (기능 요청)
	 * 주소설계 - http://localhost:8080/user/sign-up
	 * @param dto
	 * @return
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpDTO dto) { // dto와 매핑
		System.out.println("test : " + dto.toString());
		// * controller에서 일반적인 코드 작업
		// 1. 인증 검사 (여기서는 인증검사 불필요 - 회원가입에는 로그인 유무가 필요없다.)
		
		// 2. 유효성 검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME, HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_FULLNAME, HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 객체로 전달
		userService.createUser(dto);
		
		// TODO - 추후 수정
		return "redirect:/user/sign-in";
	}
	
	/**
	 * 로그인 화면 요청
	 * 주소설계 - http://localhost:8080/user/sign-in
	 * @return
	 */
	@GetMapping("/sign-in")
	public String signInPage() {
		// 인증검사,유효성검사 <- 로그인 화면은 필요 없다.
		return "user/signIn"; 
	}
	
	/**
	 * 로그인 요청 처리
	 * 주소설계 - http://localhost:8080/user/sign-in
	 * @return
	 */
	@PostMapping("/sign-in")
	public String signProc(SignInDTO dto) {
		
		// 인증 검사 불필요
		// 유효성 검사 필요 !
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_USERNAME, HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		// 서비스 호출
		User principal = userService.readUser(dto);
		// 세션 메모리에 등록 처리
		session.setAttribute(Define.PRINCIPAL, principal); // User Object 값이 담김
		// 새로운 페이지로 이동 처리
		// TODO - 계좌 목록 페이지 이동 처리 예정
		return "redirect:/account/list";
	}
	
	// 코드흐름 : 유저컨트롤러 포스트로 보냄 > 유저 서비스로 > 레파지토리 > xml > 세션 등록 > 리다이렉트처리
	
	/**
	 * 로그아웃 처리
	 * @return
	 */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); // 호출 시 로그아웃 처리 됨
		return "redirect:/user/sign-in";
	}
	
}
