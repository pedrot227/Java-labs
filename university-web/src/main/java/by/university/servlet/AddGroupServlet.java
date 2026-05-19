package by.university.servlet;

import by.university.model.Group;
import by.university.service.FacultyService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddGroupServlet", urlPatterns = "/AddGroupServlet")
public class AddGroupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("name") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String facultyIdParam = request.getParameter("facultyId");
        if (facultyIdParam != null && !facultyIdParam.isEmpty()) {
            int facultyId = Integer.parseInt(facultyIdParam);
            request.setAttribute("selectedFaculty",
                    FacultyService.getFacultyById(facultyId));
        }
        request.setAttribute("faculties", FacultyService.getAllFaculties());
        request.getRequestDispatcher("/WEB-INF/views/addGroup.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getSession().getAttribute("name") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String groupName    = request.getParameter("groupName");
        String facultyIdParam = request.getParameter("facultyId");
        String[] studentNames  = request.getParameterValues("studentName");
        String[] studentEmails = request.getParameterValues("studentEmail");

        if (groupName == null || groupName.trim().isEmpty()
                || facultyIdParam == null || facultyIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "Заполните все обязательные поля!");
            request.setAttribute("faculties", FacultyService.getAllFaculties());
            request.getRequestDispatcher("/WEB-INF/views/addGroup.jsp")
                    .forward(request, response);
            return;
        }

        try {
            int facultyId = Integer.parseInt(facultyIdParam);
            Group newGroup = FacultyService.addGroupToFaculty(
                    groupName.trim(), facultyId, studentNames, studentEmails);

            request.getSession().setAttribute("successMessage",
                    "Группа '" + newGroup.getGroupName() + "' добавлена! " +
                            "Студентов: " + newGroup.getStudents().size());

            response.sendRedirect(request.getContextPath() + "/FacultyServlet");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            request.setAttribute("faculties", FacultyService.getAllFaculties());
            request.getRequestDispatcher("/WEB-INF/views/addGroup.jsp")
                    .forward(request, response);
        }
    }
}