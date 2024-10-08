package com.tenco.bank.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tenco.bank.dto.DepositDTO;
import com.tenco.bank.dto.SaveDTO;
import com.tenco.bank.dto.TransferDTO;
import com.tenco.bank.dto.WithdrawalDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.model.Account;
import com.tenco.bank.repository.model.HistoryAccount;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;

@Controller // IoC대상 (싱글톤으로 관리)
@RequestMapping("/account") // 대문열기
public class AccountController {

	// 계좌 생성 화면 요청 - DI 처리
	private final HttpSession session;
	private final AccountService accountService; // 멤버변수 선언 시 final 사용하면 성능적으로 더 낫다.

	// @Autowired
	public AccountController(HttpSession session, AccountService accountService) {
		this.session = session;
		this.accountService = accountService;
	}

	
	
	/**
	 * 계좌 생성 페이지 요청 주소설계 - http://localhost:8080/account/save
	 * 
	 * @return save.jsp
	 */
	@GetMapping("/save")
	public String savePage() {

		
		return "account/save";
	}

	/**
	 * 계좌 생성 기능 요청 주소설계 - http://localhost:8080/account/save
	 * 
	 * @return : 추후 계좌 목록 페이지로 이동 처리
	 */
	@PostMapping("/save")
	public String saveProc(SaveDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {

		// 3. 유효성 검사
		if (dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}

		if (dto.getBalance() == null || dto.getBalance() <= 0) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}

		// 4. 서비스 호출
		accountService.createAccount(dto, principal.getId());

		return "redirect:/account/list";
	}

	/**
	 * 계좌 목록 페이지 요청 주소설계 - http://localhost:8080/account/list , ..../
	 * 
	 * @return
	 */
	@GetMapping({ "/list", "/" }) // url 매핑을 두 개 설정 가능하다.
	public String listPage(Model model, @SessionAttribute(Define.PRINCIPAL) User principal) { // TODO - 검색 기능 + 페이징 처리 추가 가능
		// 2. 유효성 검사 - 추출할 데이터 없으므로 아직 필요 x

		// 3. 서비스 호출
		List<Account> accountList = accountService.readAccountListByUserId(principal.getId());
		if (accountList.isEmpty()) { // 리스트는 있으나, 값이 비어있는 경우
			model.addAttribute("accountList", null); // 값은 null
		} else {
			model.addAttribute("accountList", accountList);
		}
		// JSP에 데이터를 넣어주는 기술 - set/getAttribute
		return "account/list";
	}

	/**
	 * 출금 페이지 요청
	 * 
	 * @return withdrawal.jsp
	 */
	@GetMapping("/withdrawal")
	public String withdrawalPage() {
		
		return "account/withdrawal";
	}

	/**
	 * 출금 기능 요청
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/withdrawal")
	public String withdrawalProc(WithdrawalDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {

		// 유효성 검사 (직접 자바 코드를 통해 개발했었지만) -> 스프링부트에서 제공하는 유효성 검사 라이브러리 @Valid 존재
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.W_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getWAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}

		if (dto.getWAccountPassword() == null || dto.getWAccountPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccountWithdraw(dto, principal.getId());

		return "redirect:/account/list";
	}

	/**
	 * 입금 페이지 요청
	 * 
	 * @return deposit.jsp
	 */
	@GetMapping("/deposit")
	public String depositPage() {
		
		return "account/deposit";
	}

	/**
	 * 입금 기능 요청
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/deposit")
	public String depositProc(DepositDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {
		// 유효성 검사
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.W_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getDAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccountDeposit(dto, principal.getId());

		return "redirect:/account/list";
	}

	/**
	 * 이체 페이지 요청
	 * 
	 * @return transfer.jsp
	 */
	@GetMapping("/transfer")
	public String transferPage() {
		
		return "account/transfer";
	}

	/**
	 * 이체 기능 처리 요청
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/transfer")
	public String tranferProc(TransferDTO dto, @SessionAttribute(Define.PRINCIPAL) User principal) {

		// 유효성 검사
		if (dto.getAmount() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_BALANCE, HttpStatus.BAD_REQUEST);
		}

		if (dto.getAmount().longValue() <= 0) {
			throw new DataDeliveryException(Define.W_BALANCE_VALUE, HttpStatus.BAD_REQUEST);
		}
		
		if (dto.getWAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException(Define.ENTER_YOUR_PASSWORD, HttpStatus.BAD_REQUEST);
		}
		
		if (dto.getDAccountNumber() == null) {
			throw new DataDeliveryException(Define.ENTER_YOUR_ACCOUNT_NUMBER, HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountTransfer(dto, principal.getId());

		return "redirect:/account/list";
	}

	/**
	 * 계좌 상세 보기 페이지
	 * 주소설계 - http://localhost:8080/account/detail/1?type=all, deposit, withdraw
	 * @RequestParam - 쿼리 스트링으로 들어오는 주소를 받아오는 방법
	 * @return
	 */
	@GetMapping("/detail/{accountId}")
	public String detail(@PathVariable(name = "accountId") Integer accountId, 
			@RequestParam(required = false, name = "type") String type,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			Model model) {
		
		
		
		// 유효성 검사
		List<String> validTypes = Arrays.asList("all", "deposit", "withdrawal");
		
		if(!validTypes.contains(type)) {
			throw new DataDeliveryException("유효하지 않은 접근 입니다.", HttpStatus.BAD_REQUEST);
		}
		
		// 페이지 개수를 계산하기 위해 총 페이지 수를 계산해주어야 한다.
		int totalRecords = accountService.countHistoryByAccountIdAndType(type, accountId);
		int totalPages = (int)Math.ceil((double)totalRecords / size);
		
		Account account = accountService.readAccountById(accountId);
		List<HistoryAccount> historyList = accountService.readHistoryByAccountId(type, accountId, page, size);
		
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("type", type);
		model.addAttribute("size", size);
		
		
		return "account/detail";
	}
	
	
	
}