package servlets;

import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/clients"})
public class ClientsServlet extends HttpServlet {
    private static ClientService clientService;

    static {
        clientService = new ClientService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clients", clientService.getAll());
        req.getRequestDispatcher("clients.jsp").forward(req, resp);
        String sessionId = req.getSession().getId();
        System.out.println(sessionId);
    }

}
