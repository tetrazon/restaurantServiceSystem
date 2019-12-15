package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/manager_login")
public class ManagerServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getServletContext().getAttribute(req.getSession().getId()) == null ||
                req.getServletContext().getAttribute(req.getSession().getId())!= "manager"){
            req.getRequestDispatcher("manager_login.jsp").forward(req, resp);
        } else if(req.getServletContext().getAttribute(req.getSession().getId()).equals("manager")){
            logger.info("manager");
            req.getRequestDispatcher("/managing").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("manager_login.jsp").forward(req, resp);
    }
}
