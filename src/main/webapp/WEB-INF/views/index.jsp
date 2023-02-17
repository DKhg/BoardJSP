<%@ page  contentType="text/html; charset=UTF-8"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 이동 기능</title>
</head>
<body>

<input type="button" value="게시판 이동" onclick="location.href='<%=cp %>/list.action'"/>


</body>
</html>