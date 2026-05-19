<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f0f0; }
        .box {
            width: 350px; margin: 100px auto; background: white;
            padding: 30px; border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 { text-align: center; }
        input[type=text], input[type=password] {
            width: 100%; padding: 10px; margin: 8px 0;
            border: 1px solid #ddd; border-radius: 4px;
            box-sizing: border-box;
        }
        input[type=submit] {
            width: 100%; padding: 10px; background: #4CAF50;
            color: white; border: none; border-radius: 4px; cursor: pointer;
        }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
<div class="box">
    <h2>Вход в систему</h2>
    <p class="error">${errorMessage}</p>
    <form action="LoginServlet" method="POST">
        <p>Логин: <input name="name" type="text" placeholder="admin"/></p>
        <p>Пароль: <input name="password" type="password" placeholder="admin"/></p>
        <input type="submit" value="Войти"/>
    </form>
</div>
</body>
</html>