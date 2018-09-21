<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인</h1>
	
	<form method="post" action="/HelloSpring1/member/login">
		<div>
			<input type="email" name="email" placeholder="이메일을 입력하세요." />
		</div>
		<div>
			<input type="password" name="password" placeholder="비밀번호를 입력하세요." />
		</div>
		<div>
			<input type="submit" value="로그인" />
		</div>
	</form>
</body>
</html>