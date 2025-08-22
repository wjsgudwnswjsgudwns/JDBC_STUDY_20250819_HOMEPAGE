package command;

import java.io.UnsupportedEncodingException;

import dao.BoardDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BWriteCommand implements BCommand {

	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String btitle = request.getParameter("title");
		String memberid = request.getParameter("author");
		String bcontent = request.getParameter("content");
		
		boardDao.boardWrite(btitle, memberid, bcontent); // DB에 새글 입력
	}
	
}
