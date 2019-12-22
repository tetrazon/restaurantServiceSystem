package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;
import utils.FieldsValidation;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/manager_login")
public class ManagerLoginServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(ManagerLoginServlet.class);
    private EmployeeService employeeService = new EmployeeService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String managerEmail = req.getParameter("email");
        String passToCheck = req.getParameter("password");
        if (FieldsValidation.validateEmail(managerEmail)){
            String passFromDB = employeeService.getPasswordByEmail(managerEmail);
            if(passFromDB != null && passFromDB.equals(passToCheck)){
                ServletContext servletContext = getServletContext();
                servletContext.setAttribute("manager", req.getSession().getId());
                Integer emplId = employeeService.getIdByEmail(managerEmail);
                HttpSession session = req.getSession();
                session.setAttribute("emplId", emplId);
                logger.info("empl added in app context, empl id is: " + emplId);
                resp.sendRedirect("/manage_options.jsp");
            }else {
                logger.info("is not found");
                resp.sendRedirect(req.getContextPath()+"/manager_login.jsp");
            }
        }else {
            logger.info("incorrect data");
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("manager_login servlet");
        req.getRequestDispatcher("manager_login.jsp").forward(req, resp);
    }
}
