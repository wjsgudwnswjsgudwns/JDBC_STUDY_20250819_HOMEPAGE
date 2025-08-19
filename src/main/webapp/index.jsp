<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>뮤직 커뮤니티</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>
  <header class="site-header">
    <div class="container">
      <h1 class="logo">🎵 MusicHub</h1>
      <nav class="nav">
        <a href="index.jsp" class="active">홈</a>
        <a href="boardList.do">자유게시판</a>
        <a href="#">앨범 리뷰</a>
        <a href="#">공지사항</a>
      </nav>
    </div>
  </header>

  <!-- 메인 배너 -->
  <section class="hero">
    <div class="hero-content">
      <h2>최신 음악 소식과 함께하는 공간</h2>
      <p>아티스트 소식, 앨범 리뷰, 공연 후기까지 한 곳에서 즐기세요 🎶</p>
      <a href="boardList.do" class="btn primary">커뮤니티 바로가기</a>
    </div>
  </section>

  <!-- 추천 아티스트 -->
  <section class="artists container">
    <h3 class="section-title">🔥 이 달의 추천 아티스트</h3>
    <div class="grid">
      <div class="card">
        <img src="https://picsum.photos/300/200?random=1" alt="Artist">
        <h4>BTS</h4>
        <p>새 싱글 발표! 전 세계 팬들의 기대를 모으고 있습니다.</p>
      </div>
      <div class="card">
        <img src="https://picsum.photos/300/200?random=2" alt="Artist">
        <h4>NewJeans</h4>
        <p>중독성 강한 신곡으로 차트를 점령!</p>
      </div>
      <div class="card">
        <img src="https://picsum.photos/300/200?random=3" alt="Artist">
        <h4>Coldplay</h4>
        <p>성황리에 끝난 내한 공연의 여운이 가득합니다.</p>
      </div>
    </div>
  </section>

  <!-- 최신 글 (게시판 일부 미리보기) -->
  <section class="latest container">
    <h3 class="section-title">📝 자유게시판 최신 글</h3>
    <ul class="post-list">
      <li><a href="#">🎤 새 싱글 발표 - BTS</a> <span class="date">2025-08-19</span></li>
      <li><a href="#">🎶 Coldplay 내한 공연 후기</a> <span class="date">2025-08-18</span></li>
      <li><a href="#">🎧 플레이리스트 공유합니다</a> <span class="date">2025-08-17</span></li>
    </ul>
    <a href="boardList.do" class="btn">더 보기 →</a>
  </section>

  <footer class="site-footer">
    <div class="container">
      <p>© 2025 MusicHub. All rights reserved.</p>
    </div>
  </footer>
</body>
</html>
