package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


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
			
		}else if("read".equals(action)) { //리스트일 때
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("modifyForm".equals(action)) { //리스트일 때
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("writeForm".equals(action)) { //리스트일 때
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
