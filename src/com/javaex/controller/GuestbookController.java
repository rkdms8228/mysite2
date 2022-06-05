package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

/**
 * Servlet implementation class GuestbookController
 */
@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {
	
	//필드
	private static final long serialVersionUID = 1L;
       
	//생성자(디폴트 생성자 사용)

	//메소드-gs
	
	//메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("GuestbookController");
		
		//포스트 방식일 때 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//코드 작성
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) { //리스트일 때
			
			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestList = guestbookDao.getList();
			System.out.println(guestList);
			
			//request에 데이터 추가
			request.setAttribute("guestList", guestList);
			
			//데이터 + html --> jsp에게 시킴(forward) [본인 것을 쓸 때 fowqrd]
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		}else if("add".equals(action)) { //등록일 때
			
			//파라미터에서 값 꺼내기(name, hp, company)
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			String regDate = request.getParameter("rdate");
			
			
			//GuestbookVo 만들기
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content, regDate);
			
			//GuestbookDao guestInsert()로 등록하기
			GuestbookDao guestbookDao = new GuestbookDao();
			int count = guestbookDao.guestInsert(guestbookVo);
			System.out.println(count);
			
			//리다이렉트 list [본인 것이 아닐 때 list]
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}