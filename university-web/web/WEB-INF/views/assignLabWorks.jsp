<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторные работы</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        nav { background: #333; color: white; padding: 15px 20px;
            display: flex; justify-content: space-between; }
        nav a { color: white; text-decoration: none; }
        .container { max-width: 800px; margin: 30px auto; background: white;
            padding: 30px; border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th { background: #FF9800; color: white; padding: 10px; }
        td { padding: 8px; border-bottom: 1px solid #eee; }
        .lab-item { border: 1px solid #ddd; padding: 10px 14px;
            border-radius: 4px; margin-bottom: 6px;
            display: flex; align-items: center; gap: 10px; }
        .lab-item label { cursor: pointer; flex: 1; }
        button, .btn-back {
            padding: 9px 18px; border: none; border-radius: 4px;
            cursor: pointer; font-size: 14px; margin-right: 8px; }
        .btn-submit { background: #FF9800; color: white; }
        .btn-sel { background: #9E9E9E; color: white; }
        .btn-back { background: #9E9E9E; color: white;
            text-decoration: none; display: inline-block; }
        .error { background: #f8d7da; color: #721c24;
            padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        .success { background: #d4edda; color: #155724;
            padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        .badge { background: #4CAF50; color: white; padding: 2px 8px;
            border-radius: 12px; font-size: 12px; }
    </style>
</head>
<body>
<nav>
    <b>Назначение лабораторных работ</b>
    <a href="FacultyServlet">← Назад</a>
</nav>
<div class="container">
    <h2>Группа: ${group.groupName}</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="success">${sessionScope.successMessage}</div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <h3>Студенты группы:</h3>
    <table>
        <tr>
            <th>#</th><th>Имя</th><th>Email</th><th>Назначено работ</th>
        </tr>
        <c:forEach items="${students}" var="st" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${st.name}</td>
                <td>${st.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty st.labWorks}">
                            <span class="badge">${st.labWorks.size()}</span>
                            <c:forEach items="${st.labWorks}" var="lw">
                                <br/><small>- ${lw.title}</small>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <span style="color:gray">Не назначено</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h3>Выберите лабораторные работы:</h3>
    <p style="color:gray;font-size:13px">
        Будут назначены каждому студенту группы.
    </p>

    <form action="AssignLabWorksServlet" method="POST">
        <input type="hidden" name="groupId" value="${group.id}"/>

        <c:forEach items="${availableLabWorks}" var="lw">
            <div class="lab-item">
                <input type="checkbox" id="lw${lw.id}"
                       name="labWorkId" value="${lw.id}"/>
                <label for="lw${lw.id}">
                    <b>${lw.title}</b><br/>
                    <small style="color:gray">${lw.description}</small>
                </label>
            </div>
        </c:forEach>

        <br/>
        <button type="button" class="btn-sel" onclick="selectAll()">Выбрать все</button>
        <button type="button" class="btn-sel" onclick="deselectAll()">Снять все</button>
        <br/><br/>
        <button type="submit" class="btn-submit">Назначить всем студентам</button>
        <a href="FacultyServlet" class="btn-back">Отмена</a>
    </form>
</div>
<script>
    function selectAll() {
        document.querySelectorAll('input[name="labWorkId"]')
            .forEach(function(c) { c.checked = true; });
    }
    function deselectAll() {
        document.querySelectorAll('input[name="labWorkId"]')
            .forEach(function(c) { c.checked = false; });
    }
</script>
</body>
</html>