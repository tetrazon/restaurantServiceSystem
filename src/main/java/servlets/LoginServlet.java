package servlets;

import service.ClientService;
import utils.FieldsValidation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private static ClientService clientService = new ClientService();
    private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

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
                clientService.addSessionId(clientEmail, req.getSession().getId());
                logger.info("session added");
                resp.sendRedirect(req.getContextPath()+"/create_order");
            }else {
                logger.info("is not found");
                resp.sendRedirect(req.getContextPath()+"/login");
            }
        }else {
            logger.info("incorrect data");
            resp.sendRedirect(req.getContextPath()+"/login");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //delete sessionId
    }
}
