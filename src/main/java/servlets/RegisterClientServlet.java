package servlets;

import entity.people.Client;
import сontroller.ApplicationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/register"})
public class RegisterClientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        ApplicationController applicationController = new ApplicationController();
        Client client = new Client(req.getParameter("email"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("surname"), System.currentTimeMillis());
        System.out.println(applicationController.registerClient(client));
        /*System.out.println(req.getParameter("email") + ", "  + req.getParameter("password")+ ", "  +
                req.getParameter("name")+ ", "  + req.getParameter("surname"));*/
    }
}
