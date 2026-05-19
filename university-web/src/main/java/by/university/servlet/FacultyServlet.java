package by.university.servlet;

import by.university.service.FacultyService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FacultyServlet", urlPatterns = "/FacultyServlet")
public class FacultyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("name") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        request.setAttribute("faculties", FacultyService.getAllFaculties());
        request.getRequestDispatcher("/WEB-INF/views/faculty.jsp")
                .forward(request, response);
    }
}