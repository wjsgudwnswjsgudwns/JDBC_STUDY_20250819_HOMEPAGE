<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시글 수정</title>
  <link rel="stylesheet" href="css/modifyStyle.css">
</head>
<body>
  <div class="wrap">
    <header class="board-head">
      <h1 class="title">게시판</h1>
      <p class="subtitle">글 수정하기</p>
    </header>

    <form class="edit-form" action="modifyOk.do" method="post">
    	<input type="hidden" name="bnum" value="${boardDto.bnum}">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${boardDto.btitle}" required>
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" required>${boardDto.bcontent}</textarea>
      </div>

      <div class="form-footer">
        <a href="javascript:history.go(-1);" class="btn">취소</a>
        <button type="submit" class="btn primary">저장하기</button>
      </div>
    </form>
  </div>
</body>
</html>
