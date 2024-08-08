package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.repository.model.Account;

@Mapper //AccountRepository 인터페이스와 account.xml 파일을 매칭 시킨다.
public interface AccountRepository {
	
	public int insert(Account account);
	public int updateById(Account account);
	public int deleteById(Integer id, String name);
	
	// ※ 계좌 조회 기능
	// 한 사람의 유저는 여러 개의 계좌번호를 가질 수 있다. : 리스트 
	// interface 파라미터명과 xml에 사용할 변수명을 다르게 사용해야 한다면 @Pram 애노테이션을 사용할 수 있다.
	// 그리고 2개 이상의 파라미터를 사용할 경우, 반드시 @Pram 애노테이션을 사용하자!
	public List<Account> findByUserId(@Param("userId") Integer principalId); // 유저 아이디로 조회 시 몇 개의 계좌가 있는지 조회
	// account id 값으로 계좌 정보 조회하는 기능 필요
	public Account findByNumber(@Param("number") String id);

	// 코드 추가 예정
}
