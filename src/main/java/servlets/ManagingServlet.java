package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/managing")
public class ManagingServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(ManagingServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// change client deposit, add/delete employee
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer emplId = (Integer) session.getAttribute("emplId");
        logger.info("session" + request.getSession().getId() + "; req.getServletContext().getAttribute(\"emplId\") = " + emplId );
        if(emplId == null ){
            request.getRequestDispatcher("manager_login.jsp").forward(request, response);
        } else {
            logger.info("managing");
            request.getRequestDispatcher("manage_options.jsp").forward(request, response);
        }
    }
}
