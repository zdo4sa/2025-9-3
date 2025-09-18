<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="cssUrl" value="../jsp/style.css" />
<!DOCTYPE html> 
<html lang="ja"> 
<head> 
    <meta charset="UTF-8"> 
    <title>ログインページ</title> 
</head> 
<body> 
    <h1>ログインしてください</h1> 
    <form action="/inquiry-system/UserAccountServlet" method="post"> 
        ユーザー名: <input type="text" name="userName"><br> 
        パスワード: <input type="password" name="userPass"><br> 
        <input type="hidden" name="action" value="login"> 
        <input type="submit" value="ログイン"> 
    </form> 
    <hr> 
    <a href="/inquiry-system/UserAccountsServlet?action=logout">ログアウト</a> 
</body> 
</html> 