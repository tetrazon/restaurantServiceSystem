package servlets;

import entity.people.Client;
import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet({"/register"})
public class RegisterServlet extends HttpServlet {
    private static ClientService clientService;

    static {
        clientService = new ClientService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)   {

        clientService.add(req.getParameter("email"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("surname"));

    }
}
