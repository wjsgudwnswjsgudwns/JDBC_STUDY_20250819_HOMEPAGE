<%@page import="dto.BoardDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="stylesheet" href="css/boardStyle.css" />
</head>

	<body>
		<div class="wrap">
			<header class="board-head">
				<div>
					<h1 class="title">자유 게시판</h1>
					<p class="subtitle">자유 게시판</p>
				</div>
				<div class="actions">
					<input class="input" type="search" placeholder="검색어를 입력하세요" aria-label="게시글 검색">
					<button class="btn">검색</button>
				</div>
				<div class="actions">
					<button class="btn primary"><a href="write.do">+ 새 글</a></button>
				</div>
			</header>
			<section class="board" aria-labelledby="board-title" role="region">
			
			<table>
				<thead>
					<tr>
						<th scope="col" class="col-no" style="text-align:center">번호</th>
						<th scope="col" style="text-align:center">제목</th>
						<th scope="col" style="text-align:center">작성자</th>
						<th scope="col" style="text-align:center">작성일</th>
						<th scope="col" style="text-align:center">조회</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bDtos}" var="bDto">
					<tr>
						<td style="text-align:center">${bDto.bnum}</td>
						<td style="text-align:center">
						
							<c:choose>
								<c:when test="${fn:length(bDto.btitle) > 25}">
									<a href="contentView.do?bnum=${bDto.bnum}">${fn:substring(bDto.btitle, 0 ,25)}...</a>
								</c:when>
						
								<c:otherwise>
									<a href="contentView.do?bnum=${bDto.bnum}">${bDto.btitle}</a>
								</c:otherwise>
							</c:choose>
						
						</td>
						<td style="text-align:center">${bDto.memberid}</td>
						<td style="text-align:center">${fn:substring(bDto.bdate,0,10)}</td>
						<td style="text-align:center">${bDto.bhit}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		
		
		<!-- 데이터가 없을 때는 위 테이블을 숨기고 아래 empty 블록만 남기면 됩니다. -->
		<!--
		<div class="empty">
		<div class="ghost">◎</div>
		아직 등록된 글이 없습니다.
		</div>
		-->
		
		
		<nav class="pager" aria-label="페이지 네비게이션">
		<a href="#" class="page" aria-label="이전 페이지">‹</a>
		<a href="#" class="page" aria-current="page">1</a>
		<a href="#" class="page">2</a>
	</body>
</html>