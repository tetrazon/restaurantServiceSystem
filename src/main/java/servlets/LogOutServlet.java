package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LogOutServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("invalidate") != null){
            logger.info("client id: " + request.getSession(true).getAttribute("clientId") +"; or managerId: " +
                    request.getSession(true).getAttribute("emplId") + " logout");
            request.getSession(true).invalidate();
            response.sendRedirect("index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).invalidate();
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
