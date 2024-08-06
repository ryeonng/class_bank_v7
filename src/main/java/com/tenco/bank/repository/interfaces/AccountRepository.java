package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.repository.model.Account;

@Mapper
public interface AccountRepository {
	
	public int insert(Account account);
	public int updateById(Account account);
	public int deleteById(Integer id);
	
	// ※ 계좌 조회 기능
	// 한 사람의 유저는 여러 개의 계좌번호를 가질 수 있다. : 리스트 
	public List<Account> findByUserId(@Param("userId") Integer principalId); // 유저 아이디로 조회 시 몇 개의 계좌가 있는지 조회
	// account id 값으로 계좌 정보 조회하는 기능 필요
	public Account findByNumber(@Param("number") String id);
}
