<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.javaex.vo.UserVo" %>
<%@ page import="java.util.List"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	System.out.println(authUser);
	
	List<GuestbookVo> guestList = (List<GuestbookVo>)request.getAttribute("guestList");

	Date date = new Date();
	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String regDate = simpleDate.format(date);
	
	System.out.println(guestList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>

			<%if(authUser == null) { //로그인 실패, 로그인 전%>
				 <ul>
					<li><a href="/mysite2/user?action=loginForm" class="btn_s">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm" class="btn_s">회원가입</a></li>
				</ul> 
			<%}else { //로그인 성공%>
				<ul>
					<li><%=authUser.getName()%> 님 안녕하세요 ﻿คʕ•ﻌ•ʔค</li>
					<li><a href="/mysite2/user?action=logout" class="btn_s">로그아웃</a></li>
					<li><a href="/mysite2/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
				</ul>
			<%}%>
			
		</div>
		<!-- //header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite2/guestbook?action=addList">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->
	
		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
				
				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<form action="/mysite2/guestbook" method="get">
						<input type="hidden" name="action" value="add">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></th>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label></th>
									<td><input id="input-pass"type="password" name="pass"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center">
										<input type="hidden" name="rdate" value="<%=regDate%>">
										<input type="hidden" name="action" value="add">
										<button type="submit" >등록</button>
									</td>
								</tr>
							</tbody>
							
						</table>
						<!-- //guestWrite -->
						<input type="hidden" name="action" value="add">
						
					</form>	
					
					<%for(int i =0; i<guestList.size(); i++) {%>
						<table class="guestRead">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 40%;">
								<col style="width: 10%;">
							</colgroup>
							<tr>
								<td>[ <%=guestList.get(i).getGuestbookNo()%>번 ]</td>
								<td> 이름: <%=guestList.get(i).getName()%> </td>
								<td>[ 등록날짜: <%=guestList.get(i).getRegDate()%> ]</td>
								<td><a href="/mysite2/guestbook?action=deleteForm&delete_no=<%=guestList.get(i).getGuestbookNo()%>">[삭제]</a></td>
							</tr>
							<tr>
								<td colspan="4">
								<%=guestList.get(i).getGuestbookNo()%>번째 방명록 내용<br>
								<%=guestList.get(i).getContent()%></td>
							</tr>
						</table>
						<!-- //guestRead -->
					<%}%>
					
				</div>
				<!-- //guestbook -->
			
			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->

		<div id="footer">
			Copyright ⓒ 2020 김가은. All right reserved
		</div>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>