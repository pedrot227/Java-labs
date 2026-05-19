<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Факультеты</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background: #f5f5f5; }
        nav { background: #333; color: white; padding: 15px 20px;
            display: flex; justify-content: space-between; }
        nav a { color: white; text-decoration: none; }
        .container { max-width: 900px; margin: 30px auto; padding: 0 20px; }
        .card { background: white; padding: 20px; margin-bottom: 20px;
            border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th { background: #4CAF50; color: white; padding: 10px; }
        td { padding: 8px; border-bottom: 1px solid #eee; }
        .btn { padding: 7px 14px; border-radius: 4px; text-decoration: none;
            color: white; font-size: 13px; }
        .btn-green { background: #4CAF50; }
        .btn-orange { background: #FF9800; }
        .success { background: #d4edda; color: #155724;
            padding: 10px; border-radius: 4px; margin-bottom: 15px; }
    </style>
</head>
<body>
<nav>
    <b>Университет</b>
    <span>Пользователь: ${sessionScope.name} |
          <a href="LoginServlet">Выйти</a></span>
</nav>
<div class="container">
    <h2>Факультеты</h2>

    <c:if test="${not empty sessionScope.successMessage}">
        <div class="success">${sessionScope.successMessage}</div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:forEach items="${faculties}" var="faculty">
        <div class="card">
            <h3>${faculty.name}</h3>
            <a href="AddGroupServlet?facultyId=${faculty.id}" class="btn btn-green">
                + Добавить группу
            </a>
            <c:if test="${not empty faculty.groups}">
                <table>
                    <tr>
                        <th>Группа</th>
                        <th>Студентов</th>
                        <th>Действия</th>
                    </tr>
                    <c:forEach items="${faculty.groups}" var="group">
                        <tr>
                            <td>${group.groupName}</td>
                            <td>${group.students.size()}</td>
                            <td>
                                <a href="AssignLabWorksServlet?groupId=${group.id}"
                                   class="btn btn-orange">
                                    Назначить лаб. работы
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty faculty.groups}">
                <p style="color:gray">Групп нет</p>
            </c:if>
        </div>
    </c:forEach>
</div>
</body>
</html>