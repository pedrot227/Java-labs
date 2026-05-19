package by.university.servlet;

import by.university.model.Group;
import by.university.model.Student;
import by.university.service.FacultyService;
import by.university.service.LabWorkService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AssignLabWorksServlet", urlPatterns = "/AssignLabWorksServlet")
public class AssignLabWorksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("name") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String groupIdParam = request.getParameter("groupId");
        if (groupIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/FacultyServlet");
            return;
        }

        int groupId = Integer.parseInt(groupIdParam);
        Group group = FacultyService.getGroupById(groupId);
        List<Student> students = FacultyService.getStudentsByGroupId(groupId);

        request.setAttribute("group", group);
        request.setAttribute("students", students);
        request.setAttribute("availableLabWorks",
                LabWorkService.getAvailableLabWorks());
        request.getRequestDispatcher("/WEB-INF/views/assignLabWorks.jsp")
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

        String groupIdParam  = request.getParameter("groupId");
        String[] selectedIds = request.getParameterValues("labWorkId");

        if (selectedIds == null || selectedIds.length == 0) {
            int groupId = Integer.parseInt(groupIdParam);
            request.setAttribute("errorMessage",
                    "Выберите хотя бы одну лабораторную работу!");
            request.setAttribute("group", FacultyService.getGroupById(groupId));
            request.setAttribute("students",
                    FacultyService.getStudentsByGroupId(groupId));
            request.setAttribute("availableLabWorks",
                    LabWorkService.getAvailableLabWorks());
            request.getRequestDispatcher("/WEB-INF/views/assignLabWorks.jsp")
                    .forward(request, response);
            return;
        }

        try {
            int groupId = Integer.parseInt(groupIdParam);

            int[] labWorkIds = new int[selectedIds.length];
            for (int i = 0; i < selectedIds.length; i++) {
                labWorkIds[i] = Integer.parseInt(selectedIds[i]);
            }

            LabWorkService.assignLabWorksToGroup(groupId, labWorkIds);

            Group group = FacultyService.getGroupById(groupId);
            request.getSession().setAttribute("successMessage",
                    "Лабораторные работы назначены всем студентам группы '"
                            + group.getGroupName() + "'!");

            response.sendRedirect(request.getContextPath()
                    + "/AssignLabWorksServlet?groupId=" + groupId);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/assignLabWorks.jsp")
                    .forward(request, response);
        }
    }
}