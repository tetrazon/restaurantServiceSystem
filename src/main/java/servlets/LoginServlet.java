package servlets;

import service.ClientService;
import utils.FieldsValidation;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private ClientService clientService = new ClientService();
    private Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet method");
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientEmail = req.getParameter("email");
        String passToCheck = req.getParameter("password");
        if (FieldsValidation.validateEmail(clientEmail)){
            String passFromDB = clientService.getPasswordByEmail(clientEmail);
            if(passFromDB != null && passFromDB.equals(passToCheck)){
                ServletContext servletContext = getServletContext();
                servletContext.setAttribute("client", req.getSession().getId());
                Integer clientId = clientService.getIdByEmail(clientEmail);
                HttpSession session = req.getSession();
                session.setAttribute("clientId", clientId);
                logger.info("client added in app context, client clientId is: " + clientId);
                resp.sendRedirect(req.getContextPath()+"/create_order");
            }else {
                logger.info("is not found");
                resp.sendRedirect(req.getContextPath()+"/login?loginError=y");
            }
        }else {
            logger.info("incorrect data");
            resp.sendRedirect(req.getContextPath()+"/login");
        }
    }
}
