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

import command.BCommand;
import command.BContentCommand;
import command.BDeleteCommand;
import command.BModifyCommand;
import command.BWriteCommand;
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
		BCommand bCommand = null;
		
		if(comm.equals("/boardList.do")) {	
		    String searchType = request.getParameter("searchType");
		    String searchKeyword = request.getParameter("searchKeyword");
		    int page = 1; 

		    if(request.getParameter("page") != null) { 
		        page = Integer.parseInt(request.getParameter("page"));
		    }

		    int totalBoardCount = 0;
		    List<BoardDto> bDtos1 = new ArrayList<>();

		    if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {
		        // ✅ 검색된 게시글 목록 & 개수
		        bDtos1 = boardDao.searchBoardList(searchKeyword, searchType, page);
		        totalBoardCount = boardDao.countSearchBoard(searchType, searchKeyword);
		        request.setAttribute("searchKeyword", searchKeyword);
		        request.setAttribute("searchType", searchType);
		    } else {
		        // ✅ 전체 게시글 목록 & 개수
		        bDtos1 = boardDao.boardList(page);
		        totalBoardCount = boardDao.countBoard();
		    }

		    // ✅ 검색/전체 여부에 맞춰서 페이지 계산
		    int totalPage = (int) Math.ceil((double)totalBoardCount / BoardDao.PAGE_SIZE);
		    int startPage = (((page - 1) / BoardDao.PAGE_SIZE) * BoardDao.PAGE_SIZE) + 1;
		    int endPage = startPage + BoardDao.PAGE_SIZE - 1;
		    if(endPage > totalPage) endPage = totalPage;

		    request.setAttribute("bDtos", bDtos1);
		    request.setAttribute("currentPage", page);
		    request.setAttribute("totalPage", totalPage);
		    request.setAttribute("startPage", startPage);
		    request.setAttribute("endPage", endPage);

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
			bCommand = new BModifyCommand();
			bCommand.excute(request, response);
			
			viewPage = "contentView.do";
		} else if(comm.equals("/delete.do")) {	// 글 삭제 후 글 목록
			bCommand = new BDeleteCommand();
			bCommand.excute(request, response);
			
			viewPage = "boardList.do";
		} else if(comm.equals("/contentView.do")) {	// 선택 글 보기			
			bCommand = new BContentCommand();
			bCommand.excute(request, response);
			
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
			bCommand = new BWriteCommand();
			bCommand.excute(request, response);
			
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
