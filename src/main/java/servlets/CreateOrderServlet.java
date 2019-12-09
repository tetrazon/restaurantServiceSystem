package servlets;

import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/create_order"})
public class CreateOrderServlet extends HttpServlet {
    private static OrderService orderService = new OrderService();

    private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("session" + req.getSession().getId() + "; req.getServletContext().getAttribute(req.getSession().getId()) = " + req.getServletContext().getAttribute(req.getSession().getId()) );
        if(req.getServletContext().getAttribute(req.getSession().getId()) == null ||
                req.getServletContext().getAttribute(req.getSession().getId())!= "client"){
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if(req.getServletContext().getAttribute(req.getSession().getId()).equals("client")){
            logger.info("new order");
            req.getRequestDispatcher("order.jsp").forward(req, resp);
        }


    }
}
