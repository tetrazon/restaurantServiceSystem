package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/home"})
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        dispatch(req,resp, action);


    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String action)
            throws  javax.servlet.ServletException, java.io.IOException {
        switch (action){
            case "register":
                action = "/reg.jsp";
                break;
            case "about":
                action = "/about.jsp";
                break;
            default:
                action = "/index.jsp";
                System.out.println("default");
                break;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(action);
        dispatcher.forward(request, response);
    }

}
