<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	int deleteNo = Integer.parseInt(request.getParameter("delete_no"));
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

		<!-- header -->
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>
		<!-- //header -->

		<!-- nav -->
		<jsp:include page="/WEB-INF/views/includes/nav.jsp"></jsp:include>
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
			
				<div id="content-head">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="guestbook">
					<form action="/mysite2/guestbook" method="get">
						<input type="hidden" name="action" value="delete">
						<input type="hidden" name="delete_no" value="<%=deleteNo%>">
						<table id="guestDelete">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 25%;">
								<col style="width: 25%;">
							</colgroup>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="delete_password"></td>
								<td class="text-left"><button type="submit">삭제</button></td>
								<td><a href="/mysite2/main">[메인으로 돌아가기]</a></td>
							</tr>
						</table>
						<input type='hidden' name="" value="">
						<input type='hidden' name="" value="">
					</form>
					
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
