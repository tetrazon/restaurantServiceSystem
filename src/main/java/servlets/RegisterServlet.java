package servlets;

import entity.people.Client;
import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/register"})
public class RegisterServlet extends HttpServlet {

    private static ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("reg.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//add validation!
        Client client = new Client(req.getParameter("email"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("surname"), System.currentTimeMillis());
        clientService.add(client);
        resp.sendRedirect(req.getContextPath()+"/clients");

    }
}
