<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить группу</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        nav { background: #333; color: white; padding: 15px 20px;
            display: flex; justify-content: space-between; }
        nav a { color: white; text-decoration: none; }
        .container { max-width: 700px; margin: 30px auto; background: white;
            padding: 30px; border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        label { display: block; margin: 10px 0 4px; font-weight: bold; }
        input[type=text], select {
            width: 100%; padding: 9px; border: 1px solid #ddd;
            border-radius: 4px; box-sizing: border-box; }
        .row { display: flex; gap: 10px; margin-bottom: 6px; }
        .row input { flex: 1; }
        button, .btn-back {
            padding: 9px 18px; border: none; border-radius: 4px;
            cursor: pointer; font-size: 14px; margin-right: 8px; }
        .btn-submit { background: #4CAF50; color: white; }
        .btn-add { background: #2196F3; color: white; }
        .btn-back { background: #9E9E9E; color: white;
            text-decoration: none; display: inline-block; }
        .error { background: #f8d7da; color: #721c24;
            padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        #studentsBlock { border: 1px solid #eee; padding: 12px;
            border-radius: 4px; margin-top: 8px; }
    </style>
</head>
<body>
<nav>
    <b>Добавление группы</b>
    <a href="FacultyServlet">← Назад</a>
</nav>
<div class="container">
    <h2>Добавить группу в факультет</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <form action="AddGroupServlet" method="POST">

        <label>Факультет:</label>
        <select name="facultyId" required>
            <option value="">-- Выберите --</option>
            <c:forEach items="${faculties}" var="f">
                <option value="${f.id}"
                        <c:if test="${selectedFaculty != null && selectedFaculty.id == f.id}">
                            selected
                        </c:if>>
                        ${f.name}
                </option>
            </c:forEach>
        </select>

        <label>Название группы:</label>
        <input type="text" name="groupName" placeholder="Например: ИТ-301" required/>

        <label>Студенты:</label>
        <div id="studentsBlock">
            <div class="row">
                <input type="text" name="studentName" placeholder="ФИО"/>
                <input type="text" name="studentEmail" placeholder="Email"/>
            </div>
            <div class="row">
                <input type="text" name="studentName" placeholder="ФИО"/>
                <input type="text" name="studentEmail" placeholder="Email"/>
            </div>
            <div class="row">
                <input type="text" name="studentName" placeholder="ФИО"/>
                <input type="text" name="studentEmail" placeholder="Email"/>
            </div>
        </div>

        <br/>
        <button type="button" class="btn-add" onclick="addRow()">
            + Добавить студента
        </button>

        <br/><br/>
        <button type="submit" class="btn-submit">Создать группу</button>
        <a href="FacultyServlet" class="btn-back">Отмена</a>
    </form>
</div>

<script>
    function addRow() {
        var block = document.getElementById('studentsBlock');
        var div = document.createElement('div');
        div.className = 'row';
        div.innerHTML =
            '<input type="text" name="studentName" placeholder="ФИО"/>' +
            '<input type="text" name="studentEmail" placeholder="Email"/>' +
            '<button type="button" onclick="this.parentElement.remove()" ' +
            'style="padding:9px 12px;background:#f44336;color:white;' +
            'border:none;border-radius:4px;cursor:pointer;">X</button>';
        block.appendChild(div);
    }
</script>
</body>
</html>