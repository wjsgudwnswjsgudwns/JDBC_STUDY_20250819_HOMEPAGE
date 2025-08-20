package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.BoardDao;
import dto.BoardDto;


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
		
		BoardDao bDao = new BoardDao();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		if(comm.equals("/boardList.do")) {	// 글 목록
			bDtos = bDao.boardList();
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		} else if(comm.equals("/write.do")) {	// 글 작성
			viewPage = "write.jsp";
		} else if(comm.equals("/modify.do")) {	// 글 수정
			viewPage = "modify.jsp";
		} else if(comm.equals("/delete.do")) {	// 글 삭제 후 글 목록
			viewPage = "boardList.do";
		} else if(comm.equals("/contentView.do")) {	// 선택 글 보기
						
						
			String bnum = request.getParameter("bnum");
			
			BoardDao boardDao = new BoardDao();
			BoardDto boardDto = boardDao.contentView(bnum);
			
			if(boardDto == null) {
				request.setAttribute("msg", "1");
			} else {
				request.setAttribute("boardDto", boardDto);
			}
			
			viewPage = "contentView.jsp";
		} else if(comm.equals("/writeOk.do")) {	// 선택 글 보기
			request.setCharacterEncoding("UTF-8");
			
			String btitle = request.getParameter("title");
			String memberid = request.getParameter("author");
			String bcontent = request.getParameter("content");
			
			bDao.boardWrite(btitle, memberid, bcontent); // DB에 새글 입력
			
			viewPage = "boardList.do";
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// viewPage에게 웹 서블릿 내에서 만든 request 객체를 전달 후 viewPage로 이동
		
	}
}
