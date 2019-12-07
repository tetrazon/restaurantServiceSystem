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
        logger.info("session" + req.getSession().getId() + "; req.getAttribute(\"clientEmail\") = " + req.getServletContext().getAttribute("client") );
        if(req.getServletContext().getAttribute("clientEmail") == null){
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
        logger.info("new order");
        //orderService.getAll(1);
        //resp.sendRedirect("order.jsp");
        req.getRequestDispatcher("order.jsp").forward(req, resp);

    }
}
