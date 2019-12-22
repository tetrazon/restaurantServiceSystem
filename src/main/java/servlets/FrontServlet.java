package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/home"})
public class FrontServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(LoginServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        dispatch(req,resp, action);


    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String action)
            throws  javax.servlet.ServletException, java.io.IOException {
        switch (action){
            case "register":
            case "login":
                action = "/reg.jsp";
                break;
            case "about":
                action = "/about.jsp";
                break;
            case "manager":
                action = "/manager_login";
                break;
            default:
                action = "/index.jsp";
                logger.info("default");
                break;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(action);
        dispatcher.forward(request, response);
    }

}
