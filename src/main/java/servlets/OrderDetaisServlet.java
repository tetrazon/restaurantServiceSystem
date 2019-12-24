package servlets;

import entity.food.DishesInOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/order_details")
public class OrderDetaisServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(OrderDetaisServlet.class);
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        logger.info("order id is: " + orderId);
        if(orderId == null ){
            logger.info("unauthorised order");
            request.getRequestDispatcher("reg.jsp").forward(request, response);
            return;
        } else {
            logger.info("order details:");
            List<DishesInOrder> dishesInOrderList = orderService.getDishesFromOrder(orderId);
            double invoice = orderService.calculateSumOfOrder(dishesInOrderList);
            session.setAttribute("dishesInOrderList", dishesInOrderList);
            session.setAttribute("invoice", invoice);
            request.getRequestDispatcher("finish_order.jsp").forward(request, response);
            return;
        }
    }
}
