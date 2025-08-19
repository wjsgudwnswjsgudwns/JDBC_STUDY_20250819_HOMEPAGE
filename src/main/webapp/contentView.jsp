<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>게시글 상세보기</title>
  <link rel="stylesheet" href="css/contentViewStyle.css">
</head>
<body>
  <div class="wrap">
    <header class="board-head">
      <h1 class="title">게시판</h1>
      <p class="subtitle">글 상세보기</p>
    </header>

    <article class="post">
      <header class="post-header">
        <h2 class="post-title">${boardDto.btitle}</h2>
        <div class="post-meta">
          <span class="author">작성자: ${boardDto.memberid}</span>
          <span class="date">${boardDto.bdate}</span>
          <span class="views">조회수: ${boardDto.bhit}</span>
        </div>
      </header>

      <div class="post-content">
       ${boardDto.bcontent}
      </div>

      <footer class="post-footer">
        <a href="boardList.do" class="btn">목록으로</a>
        <a href="#" class="btn primary">수정</a>
        <a href="#" class="btn danger">삭제</a>
      </footer>
    </article>
  </div>
</body>
</html>
