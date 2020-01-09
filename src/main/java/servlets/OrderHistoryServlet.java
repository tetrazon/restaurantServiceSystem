package servlets;

import entity.order.Order;
import entity.users.Client;
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

@WebServlet("/order_history")
public class OrderHistoryServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(OrderHistoryServlet.class);
    OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("order_details servlet");
        HttpSession session = request.getSession(true);
        Integer clientId = (Integer) session.getAttribute("clientId");
        Client client = new Client(clientId);
        String destination = "order_history.jsp";
        if(clientId == null){
            destination = "/reg.jsp";
        }
        List<Order>  orders = orderService.getAllOrders(client);
        for(Order o: orders){
            logger.info("order id: " + o.getId() + "; check " + o.getInvoice());
        }
        session.setAttribute("orders", orders);
        request.getRequestDispatcher(destination).forward(request, response);
    }
}
