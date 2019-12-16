package servlets;

import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/finish_order")
public class FinishOrderServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(FinishOrderServlet.class);
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer orderId = (Integer) session.getAttribute("orderId");
        if(orderId == null ){
            logger.info("unauthorised order");
            request.getRequestDispatcher("reg.jsp").forward(request, response);
            return;
        } else {
            logger.info("order is finished");
            List<DishesInOrder> dishesInOrderList = orderService.getDishesFromOrder(orderId);
            session.setAttribute("dishesInOrderList", dishesInOrderList);
            request.getRequestDispatcher("finish_order.jsp").forward(request, response);
            return;
        }
    }

}
