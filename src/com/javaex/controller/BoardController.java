package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	
	//필드
	private static final long serialVersionUID = 1L;
       
	//생성자(디폴트 생성자 사용)

	//메소드-gs
	
	//메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("BoardController");
		
		//포스트 방식일 때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//코드 작성
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) { //리스트일 때
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getList();
			System.out.println(boardList);
			
			//request에 데이터 추가
			request.setAttribute("boardList", boardList);
			
			//데이터 + html --> jsp에게 시킴(forward) [본인 것을 쓸 때 fowqrd]
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		}else if("read".equals(action)) { //읽기일 때
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//no로 board 찾기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			
			//조회수
			boardVo.setHit(boardVo.getHit()+1);
			boardDao.boardHit(boardVo);
			
			//request에 데이터 추가
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("modifyForm".equals(action)) { //수정일 때
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 찾기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoard(no);
			
			//request에 데이터 수정
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("modify".equals(action)) { //수정일 때
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//줄 바꿈 인식
			content = content.replace("\r\n","<br>");
			
			BoardVo boardVo = new BoardVo();
			boardVo.setNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			BoardDao boardDao = new BoardDao();
			boardDao.update(boardVo);
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}else if("writeForm".equals(action)) { //글쓰기일 때
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("write".equals(action)) { //글쓰기일 때
			
			//세션에서 no 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//줄 바꿈 인식
			content = content.replace("\r\n","<br>");
			
			//DB에 추가
			BoardDao boardDao = new BoardDao();
			boardDao.write(new BoardVo(title, content, no));
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}else if("delete".equals(action)) { //글삭제일 때
			
			//파라미터 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//board 삭제
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);
			
			//list로 리다이렉트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
