<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>새 글 작성</title>
  <link rel="stylesheet" href="css/writeStyle.css">
</head>
<body>
  <div class="wrap">
    <header class="board-head">
      <h1 class="title">자유게시판</h1>
      <p class="subtitle">새 글 작성</p>
    </header>

    <form class="write-form" action="writeOk.do" method="post">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required>
      </div>

      <div class="form-group">
        <label for="author">작성자</label>
        <input type="text" id="author" name="author" value="${sessionScope.sessionId}" readonly="readonly">
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>
      </div>

      <div class="form-footer">
        <a href="boardList.do" class="btn">취소</a>
        <button type="submit" class="btn primary">등록하기</button>
      </div>
    </form>
  </div>
</body>
</html>