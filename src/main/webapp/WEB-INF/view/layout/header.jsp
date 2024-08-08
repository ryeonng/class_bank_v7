<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>myBank</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/common.css">
<link rel="icon" href="/ko.ico" type="image/x-icon">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
</style>
</head>
<body>
	<div class="m--flex-container">
		<div class="m--container">
			<div class="jumbotron text-center m--banner-img" style="margin-bottom: 0">
				<h1>My Bank</h1>
				<p>마이바이티스를 활용한 스프링 부트 앱 만들어보기</p>
			</div>


			<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
				<a class="navbar-brand" href="/index">홈</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="collapsibleNavbar">
					<ul class="navbar-nav">
					<c:choose>
						<c:when test="${principal != null}">
							<%-- principal이 null이 아니라면, 사용자는 로그인 상태로, 로그아웃 표시 --%>
						<li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
						</c:when>
						<c:otherwise> <%-- 사용자 로그인 상태가 아니라면, 로그인 회원가입 표시 되도록 --%>
						<li class="nav-item"><a class="nav-link" href="/user/sign-in">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/sign-up">회원가입</a></li>
						</c:otherwise>
					</c:choose>
					
						
					</ul>
				</div>
			</nav>

			<div class="container" style="margin-top: 30px">
				<div class="row">
					<div class="col-sm-4">
						<h2>About Me</h2>
						<h5>Photo of me:</h5>
						<div class="m--profile"></div>
						<p>코린이 개발을 위한 뱅크 앱</p>
						<h3>서비스 목록</h3>
						<p>계좌 목록, 생성, 출금, 입금, 이체 페이지를 활용할 수 있습니다.</p>
						<ul class="nav nav-pills flex-column">
							<li class="nav-item"><a class="nav-link" href="/account/list">내 계좌목록</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/save">신규계좌 생성</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/withdrawal">출금</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/deposit">입금</a></li>
							<li class="nav-item"><a class="nav-link" href="/account/transfer">이체</a></li>
						</ul>
						<hr class="d-sm-none">
					</div>
					<!-- end of header.jsp  -->