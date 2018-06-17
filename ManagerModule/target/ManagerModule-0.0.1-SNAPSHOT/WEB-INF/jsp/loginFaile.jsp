<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/loginController/login" method="post">
	<table>
		<tr>
			<td>username:</td>
			<td><input type="text" id = "username" name="username"> </td>
		</tr>
		<tr>
			<td>username:</td>
			<td><input type="password" id = "password" name="password"></td>
		</tr>
		<tr>
			<td align="center" rowspan="2"><input type="submit" value="提交"></td>
		</tr>
	</table>
</form>
<a href="./Demo/queryDemo">页面跳转，数据库查询</a>
</body>
</html>