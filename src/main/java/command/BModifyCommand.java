package command;

import dao.BoardDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BModifyCommand implements BCommand {
	
	
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String bnum = request.getParameter("bnum");
		String btitle = request.getParameter("title");
		String bcontent = request.getParameter("content");

		
		boardDao.boardUpdate(bnum, btitle, bcontent);
		//request.setAttribute("boardDto", boardDao);
	}
}
