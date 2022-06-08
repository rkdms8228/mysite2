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
			String regDate = request.getParameter("regDate");
			
			
			//GuestbookVo 만들기
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content, regDate);
			
			//GuestbookDao guestInsert()로 등록하기
			GuestbookDao guestbookDao = new GuestbookDao();
			int count = guestbookDao.guestInsert(guestbookVo);
			System.out.println(count);
			
			//리다이렉트 list [본인 것이 아닐 때 list]
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
			
		}else if("deleteForm".equals(action)) { //삭제폼일 때
			
			//파라미터 가져오기
			int deleteNo = Integer.parseInt(request.getParameter("delete_no"));
			
			GuestbookVo gv = new GuestbookVo();
			gv.setNo(deleteNo);
			
			request.setAttribute("gv", gv);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}else if("delete".equals(action)) { //삭제일 때
			
			//파라미터 값 가져오기
			int deleteNo = Integer.parseInt(request.getParameter("delete_no"));
			String deletePw = request.getParameter("delete_password");

			GuestbookDao guestDao = new GuestbookDao();
			GuestbookVo guest = guestDao.getDeleteGuest(deleteNo);
			
			if(guest.getPassword().equals(deletePw)) {
				
				//입력한 비밀번호가 같으면 삭제
				guestDao.guestDelete(deleteNo, deletePw);
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
				
			} else { //틀리면 그냥 메인으로 돌아가기
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
				System.out.println("비밀번호 오류");
			}
			
		}else {
			System.out.println("action 파라미터 없음");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
