package servlets;

import entity.food.Dish;
import entity.order.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/create_order"})
public class CreateOrderServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(CreateOrderServlet.class);
    private OrderService orderService = new OrderService();
    private ClientService clientService = new ClientService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        int clientId = (int) session.getAttribute("clientId");
        String stringTableId = req.getParameter("tableId");
        if(stringTableId == null || stringTableId.equals("")) {
            //table not chosen
            resp.sendRedirect("/create_order?error=table");
            return;
        }
        Integer tableId = Integer.valueOf(stringTableId);
        int[] dishQuantities = orderService.stringToIntArray(req.getParameterValues("quantity"));
        if (dishQuantities == null){
            resp.sendRedirect("/create_order");
            return;
        }
        double[] dishPrices = orderService.stringToDoubleArray(req.getParameterValues("dishPrice"));
        int[] dishesId = orderService.stringToIntArray(req.getParameterValues("dishId"));
        //check sum
        double invoice = orderService.calculateSumOfOrder(dishQuantities, dishPrices);
        session.setAttribute("invoice", invoice);
        double newClientDeposit = clientService.checkDeposit(clientId, invoice);
        if(newClientDeposit < 0 ){
            //not enough money,cancel order
            logger.info("not enough money: "+ -newClientDeposit);
            resp.sendRedirect("/create_order?error=money&need=" + -newClientDeposit);
            return;
        }
        //2 reserve table, init order;
        orderService.setTableStatus(true, tableId);
        if(!orderService.initOrder(clientId,tableId, invoice)){
            orderService.setTableStatus(false, tableId);
            resp.sendRedirect("/create_order?error=order");
            return;
        }
        int orderId = orderService.getOrderId(clientId, "NEW");
        String orderResult = orderService.processOrder(clientId, orderId, tableId, invoice, dishesId, dishQuantities);
        switch (orderResult){
            //    NEW, IS_PROCESSING, IS_FINISHED, COOK_QUEUE, WAITER_QUEUE, ALL_QUEUE;
            case "IS_FINISHED":
                clientService.updateDeposit(clientId, newClientDeposit);
                session.setAttribute("orderId", orderId);
                //, set into context, redirect to finish page
                resp.sendRedirect("/finish_order");
                break;
            case "COOK_QUEUE":
                logger.info("no free cook");
                //finish order init
                //info need wait COOK, add listeners to catch COOK
                break;
            case "WAITER_QUEUE":
                //finish order init
                logger.info("no free waiter");
                //info need wait waiter, add listeners to catch waiter
                break;
            case "ALL_QUEUE":
                //finish order init
                logger.info("no free employees");
                //info need wait ALL, add listeners to catch cook/waiter
                break;
            default:
                logger.error("error in order status");
                resp.sendRedirect("/index.jsp");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        logger.info("new order");
        List<Dish> dishes = orderService.getAllDishes();
        List<Table> tables = orderService.getAllTables();// change table status if time after finishing order is more than 10 min
        session.setAttribute("dishes", dishes);
        session.setAttribute("tables", tables);
        req.getRequestDispatcher("order.jsp").forward(req, resp);


    }
}
