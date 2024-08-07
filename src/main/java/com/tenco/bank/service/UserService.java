package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.repository.interfaces.UserRepository;
import com.tenco.bank.repository.model.User;

@Service // 제어의 역전 : IoC의 대상이 된다. (싱글톤으로 관리 됨)
public class UserService {
	
	private UserRepository userRepository;
	
	
	// DI - 의존 주입 (Dependency Injection)
	// ↓↓↓ @Autowired 어노테이션으로 대체 가능하다 ↓↓↓
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	
	/**
	 * 회원 등록 서비스 기능
	 * 트랜잭션 처리
	 * @param dto
	 */
	@Transactional // 트랜잭션 처리는 반드시 습관화!
	public void createUser(SignUpDTO dto) { // 회원가입 처리 (CRUD 기반 네이밍)
		int result = 0;
		try {
			result = userRepository.insert(dto.toUser());
			
		} catch (DataAccessException e) {
			throw new DataDeliveryException("중복된 이름을 사용할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
		}
		if(result != 1) {
			throw new DataDeliveryException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		// 로그인 서비스
		public User readUser(SignInDTO dto) {
		// 유효성 검사는 Controller에서 먼저 하자.
			User userEntity = null; // 지역변수 선언을 습관화 하자.
			try {
				userEntity = userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
			} catch (DataAccessException e) { // 쿼리 측 오류는 대부분 DataAccessException
				throw new DataDeliveryException("잘못된 처리입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (Exception e) {
				throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
			}
			
			if(userEntity == null) {
				throw new DataDeliveryException("ID 혹은 password가 틀렸습니다.", HttpStatus.BAD_REQUEST);
			}
			
			return userEntity;
	}
	
}
