package command;

import dao.BoardDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BDeleteCommand implements BCommand {
	
	
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String bnum = request.getParameter("bnum");
		
		boardDao.boardDelete(bnum);
	}
}
