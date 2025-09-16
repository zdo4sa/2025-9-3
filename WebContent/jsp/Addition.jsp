<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head> 
    <meta charset="UTF-8"> 
    <title>管理者ログインページ</title> 
    <link rel="stylesheet" href="../style.css">
</head> 
<body> 
    <h1>開発者IDを入力してください</h1> 
    <form action="/jdynamic/UserAuthServlet" method="post"> 
        チームID: <input type="text" name="userName"><br> 
        パスワード: <input type="password" name="userPass"><br> 
        <input type="hidden" name="action" value="login"> 
        <input type="submit" value="ログイン"> 
    </form> 
    <hr> 
    <a href="/jdynamic/UserAuthServlet?action=logout">ログアウト</a> 
</body> 
</html> 