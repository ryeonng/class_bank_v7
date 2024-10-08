<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp -->
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<!-- start of content.jsp(xxx.jsp) -->
<div class="col-sm-8">
	<h2>계좌목록(인증)</h2>
	<h5>Bank App에 오신걸 환영합니다.</h5>

	<!-- 계좌가 없는 경우와 있는 경우 두 가지로 분리 -->
	<!-- 계좌가 있는 사용자일 경우, 반복문 활용 예정 (계좌가 여러 개 일 수 있으니) -->
	
	<c:choose>
		<c:when test="${accountList != null}"> <%-- 계좌가 존재하는 경우 --%>
			<%-- jstl 태그 안에서는 html 주석 사용 시 오류발생 --%>
			<table class="table">
				<thead>
					<tr>
						<th>계좌 번호</th>
						<th>잔액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="account" items="${accountList}"> <%-- accountList 만큼 반복을 돌려준다. --%>
						<tr>
							<td><a href="/account/detail/${account.id}?type=all">${account.number}</a></td>
							<td>${account.formatKoreanWon(account.balance)}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<div class="jumbotron display-4">
				<h5>아직 생성된 계좌가 없습니다.</h5>
			</div>
		</c:otherwise>
	</c:choose>
	
</div>
<!-- end of col-sm-8 -->
</div>
</div>
<!-- end of content.jsp(xxx.jsp) -->

<!-- footer.jsp -->
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
