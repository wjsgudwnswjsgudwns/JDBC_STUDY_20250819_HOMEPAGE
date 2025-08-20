<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인</title>
  <link rel="stylesheet" href="css/loginStyle.css">
</head>
<body>
  <div class="login-wrap">
    <div class="login-card">
      <h1 class="login-title">로그인</h1>
      <form class="login-form" action="loginOk.do" method="POST">
        <div class="form-group">
          <label for="userid">아이디</label>
          <input type="text" id="userid" name="userid" placeholder="아이디를 입력하세요" required>
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
        </div>

        <button type="submit" class="btn primary">로그인</button>
      </form>

      <div class="login-footer">
        <a href="#">회원가입</a>
        <a href="#">비밀번호 찾기</a>
      </div>
      
      <div class="login-footer">
        <c:if test="${param.msg == 1}">
        	<p style="color: red">잘못된 아이디 또는 비밀번호입니다.</p>
        </c:if>
      </div>
      
      <div class="login-footer">
        <c:if test="${param.msg == 2}">
        	<p style="color: red">로그인 후 작성 가능합니다.</p>
        </c:if>
      </div>
      
    </div>
  </div>
</body>
</html>
