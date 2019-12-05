package servlets;

import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private static ClientService clientService;

    static {
        clientService = new ClientService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientEmail = req.getParameter("email");
        String passToCheck = req.getParameter("password");
        String passFromDB = clientService.getPasswordByEmail(clientEmail);
        if(passFromDB != null && passFromDB.equals(passToCheck)){
            clientService.addSessionId(clientEmail, req.getSession().getId());
            System.out.println("session added");
            resp.sendRedirect(req.getContextPath()+"/create_order");
        } else {
            System.out.println("is not found");
            resp.sendRedirect(req.getContextPath()+"/login");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //delete sessionId
    }
}
