package command;

import dao.BoardDao;
import dto.BoardDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BContentCommand implements BCommand {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String bnum = request.getParameter("bnum");
		
		//조회수 올려주는 메소드 호출
		boardDao.updateBhit(bnum);
		
		BoardDto boardDto = boardDao.contentView(bnum);
		
		if(boardDto == null) {
			request.setAttribute("msg", "존재하지 않는 글입니다.");
		}
		request.setAttribute("boardDto", boardDto);

	}

}
