package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.BoardDao;
import dao.MemberDao;
import dto.BoardDto;
import dto.BoardMemberDto;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
       
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI(); // jsp_mvcboard_20250819/boardList.do
		String conPath = request.getContextPath(); // jsp_mvcboard_20250819
		String comm = uri.substring(conPath.length()); // 최종 요청 값
		
		String viewPage = null;
		MemberDao memberDao = new MemberDao();
		BoardDao boardDao = new BoardDao();
		BoardMemberDto boardMemberDto = new BoardMemberDto();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		HttpSession session = null;
		
		
		if(comm.equals("/boardList.do")) {	// 글 목록
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			int page = 1; 
			int totalBoardCount = boardDao.countBoard(); // 총 글의 갯수
			
			if(request.getParameter("page") == null) { // 링크 타고 게시판 들어온 경우
	    		page = 1;
	    	} else { // 유저가 보고 싶은 페이지 번호를 클릭한 경우
	    		page = Integer.parseInt(request.getParameter("page")); // 유저가 클릭한 페이지 번호
	    	}
			List<BoardDto> boardDtos = boardDao.boardList(page);
			
			int totalPage = (int) Math.ceil((double)totalBoardCount/BoardDao.PAGE_SIZE); //총 글의 갯수로 표현 될 페이지의 수
	    	int startPage = (((page-1)/BoardDao.PAGE_SIZE)*BoardDao.PAGE_SIZE) + 1;
	    	int endPage = startPage + BoardDao.PAGE_SIZE -1;
	    	
	    	if(endPage > totalPage ) { // 계산한 endPage(20)가 totalPage(16)보다 크면 totalPage로 대체  
	    		endPage = totalPage;
	    	}
	    	
	    	request.setAttribute("boardDtos", boardDtos); // 유저가 선택한 페이지에 해당하는 글
	    	request.setAttribute("currentPage", page); // 유저가 현재 선택한 페이지
	    	request.setAttribute("totalPage", totalPage); // 총 글의 갯수로 표현 될 페이지의 수
	    	request.setAttribute("startPage", startPage); // 시작 페이지
	    	request.setAttribute("endPage", endPage); // 마지막 페이지
			
			if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {
				bDtos = boardDao.searchBoardList(searchKeyword, searchType, page);
				
				request.setAttribute("searchKeyword", searchKeyword);
				request.setAttribute("searchType", searchType);
			} else { // boardList.do로 넘어온 경우 -> 모든 게시판 리스트
				bDtos = boardDao.boardList(page);
			}
			
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		} else if(comm.equals("/modify.do")) {	// 글 수정
			session = request.getSession();
			String sid = (String)session.getAttribute("sessionId");
			
			String bnum = request.getParameter("bnum");
			
			BoardDto boardDto = boardDao.contentView(bnum);
			
			if(boardDto.getMemberid().equals(sid)) {
				request.setAttribute("boardDto", boardDto);
				
				viewPage = "modify.jsp";
			} else {
				response.sendRedirect("login.do?msg=2");
				return;
			}
		} else if(comm.equals("/modifyOk.do")) { // 글 수정 후 리스트로 이동 
			request.setCharacterEncoding("UTF-8");
			
			String bnum = request.getParameter("bnum");
			String btitle = request.getParameter("title");
			String bcontent = request.getParameter("content");
	
			
			boardDao.boardUpdate(bnum, btitle, bcontent);
			//request.setAttribute("boardDto", boardDao);
			
			viewPage = "contentView.do";
		} else if(comm.equals("/delete.do")) {	// 글 삭제 후 글 목록
			String bnum = request.getParameter("bnum");
			
			boardDao.boardDelete(bnum);
			
			viewPage = "boardList.do";
		} else if(comm.equals("/contentView.do")) {	// 선택 글 보기			
			String bnum = request.getParameter("bnum");
			
			//조회수 올려주는 메소드 호출
			boardDao.updateBhit(bnum);
			
			BoardDto boardDto = boardDao.contentView(bnum);
			
			if(boardDto == null) {
				request.setAttribute("msg", "존재하지 않는 글입니다.");
			}
			request.setAttribute("boardDto", boardDto);
			
			
			viewPage = "contentView.jsp";
		} else if(comm.equals("/write.do")) { //글 쓰기 폼으로 이동
			session = request.getSession();
			String sid = (String)session.getAttribute("sessionId");
			
			if(sid != null) { // 로그인 중
				viewPage = "write.jsp";
			} else { // 로그아웃 상태
				response.sendRedirect("login.do?msg=2");
				return;
			}
			
		} else if(comm.equals("/writeOk.do")) {	// 선택 글 보기
			request.setCharacterEncoding("UTF-8");
			
			String btitle = request.getParameter("title");
			String memberid = request.getParameter("author");
			String bcontent = request.getParameter("content");
			
			boardDao.boardWrite(btitle, memberid, bcontent); // DB에 새글 입력
			
			response.sendRedirect("boardList.do"); // 포워딩을 하지 않고 강제로 boardList.do로 이동
			return;
		} else if(comm.equals("/login.do")) {
			viewPage = "login.jsp";
		} else if(comm.equals("/loginOk.do")) {
			request.setCharacterEncoding("UTF-8");
			String loginId = request.getParameter("userid");
			String loginPw = request.getParameter("password");
			
			int loginFlag = memberDao.loginCheck(loginId, loginPw);
			
			if (loginFlag == 1) {
				session = request.getSession(); // session을 java에서 사용법
				session.setAttribute("sessionId", loginId); // 로그인 성공
			} else {
				response.sendRedirect("login.do?msg=1");
				return;
			}
			viewPage = "boardList.do";
		} else {
			viewPage = "index.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// viewPage에게 웹 서블릿 내에서 만든 request 객체를 전달 후 viewPage로 이동
		
	}
}
