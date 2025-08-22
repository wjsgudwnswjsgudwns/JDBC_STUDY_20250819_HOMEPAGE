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
<link rel="stylesheet" href="css/boardStyle2.css" />
</head>

	<body>
		<div class="wrap">
			<header class="board-head">
				<div>
					<h1 class="title">자유 게시판</h1>
					<p class="subtitle">자유 게시판</p>
					
					<span class="subtitle">
						<c:if test="${not empty sessionScope.sessionId}">
							${sessionScope.sessionId}님 로그인중
						</c:if>
					</span>
				</div>
				
				<form action="boardList.do" method="get">
				<div class="actions">
					<select name="searchType">
						<option value="btitle">제목</option>
						<option value="bcontent">내용</option>
						<option value="b.memberid">작성자</option>
					</select>
					<input class="input" type="search" placeholder="검색어를 입력하세요" aria-label="게시글 검색" name="searchKeyword">
					<input class="btn" type="submit" value="검색">
				</div>
				</form>
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
						<th scope="col" style="text-align:center">이메일</th>
						<th scope="col" style="text-align:center">작성일</th>
						<th scope="col" style="text-align:center">조회</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bDtos}" var="bDto">
					<tr>
						<td style="text-align:center">${bDto.bno}</td> <!-- 번호 -->
						<td style="text-align:center"> <!-- 제목 -->
						
							<c:choose>
								<c:when test="${fn:length(bDto.btitle) > 25}">
									<a href="contentView.do?bnum=${bDto.bnum}">${fn:substring(bDto.btitle, 0 ,25)}...</a>
								</c:when>
						
								<c:otherwise>
									<a href="contentView.do?bnum=${bDto.bnum}">${bDto.btitle}</a>
								</c:otherwise>
							</c:choose>
						
						</td>
						<td style="text-align:center">${bDto.memberid}</td> <!-- 작성자 -->
						<td style="text-align:center">${bDto.memberDto.memberemail}</td> <!-- 이메일 -->
						<td style="text-align:center">${fn:substring(bDto.bdate,0,10)}</td> <!-- 날짜 -->
						<td style="text-align:center">${bDto.bhit}</td> <!-- 조회수 -->
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
		
		
		<!-- 1 페이지로 이동 -->
	<div class="pagination">
    <!-- 첫 페이지 -->
    <c:if test="${currentPage > 1}">
        <a href="boardList.do?page=1" class="prev">◀◀</a>
    </c:if>

    <!-- 이전 그룹 -->
    <c:if test="${startPage > 1}">
        <a href="boardList.do?page=${startPage - 1}" class="prev">◀</a>
    </c:if>

    <!-- 페이지 번호 반복 -->
    <c:forEach begin="${startPage}" end="${endPage}" var="i">
        <c:choose>
            <c:when test="${currentPage == i}"> <!-- 현재 보고 있는 페이지의 번호를 표시하기 위해 하이라이트 표시 -->
                <a href="boardList.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}" class="current">${i}</a>
            </c:when>
            <c:otherwise>
                <a href="boardList.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <!-- 다음 그룹 -->
    <c:if test="${endPage < totalPage}">
        <a href="boardList.do?page=${endPage + 1}" class="next">▶</a>
    </c:if>

    <!-- 마지막 페이지 -->
    <c:if test="${currentPage < totalPage}">
        <a href="boardList.do?page=${totalPage}" class="next">▶▶</a>
    </c:if>
	</div>

	</body>
</html>