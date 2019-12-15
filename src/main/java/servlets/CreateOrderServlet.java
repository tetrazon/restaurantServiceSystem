package servlets;

import entity.food.Dish;
import entity.food.DishesInOrder;
import entity.order.Order;
import entity.order.Table;
import entity.people.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
//import java.util.logging.Logger;

@WebServlet({"/create_order"})
public class CreateOrderServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CreateOrderServlet.class);
    private OrderService orderService = new OrderService();
    private ClientService clientService = new ClientService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int clientId = (int) req.getSession(false).getAttribute("clientId");
        String stringTableId = req.getParameter("tableId");
        if(stringTableId == null || stringTableId.equals("")) {
            //table not chosen
            resp.sendRedirect("/create_order?error=table");
            return;
        }
        Integer tableId = Integer.valueOf(stringTableId);
        int[] dishQuantities = orderService.stringToIntArray(req.getParameterValues("quantity"));
        double[] dishPrices = orderService.stringToDoubleArray(req.getParameterValues("dishPrice"));
        int[] dishesId = orderService.stringToIntArray(req.getParameterValues("dishId"));
        //check sum
        double invoice = orderService.calculateSumOfOrder(dishQuantities, dishPrices);
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
        Employee waiter = orderService.callEmployee("WAITER");
        Employee cook = orderService.callEmployee("COOK");
        if(cook != null && waiter != null){
            orderService.addEmployeesInOrder(waiter.getId(),cook.getId(), orderId);
            try {
                orderService.changeOrderStatus(orderId, "IS_PROCESSING");
                waiter.processOrder();
                cook.processOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientService.updateDeposit(clientId, newClientDeposit);
            orderService.changeLoadFactor(waiter.getId(), -1);
            orderService.changeLoadFactor(cook.getId(), -1);
            orderService.changeOrderStatus(orderId, "IS_FINISHED");
            orderService.setTableStatus(false, tableId);
            orderService.setTimeOfOrder(orderId);
            //get Order, set into context, redirect to finish page
            resp.sendRedirect("finish_order.jsp");
        } else if(cook == null && waiter!= null){
            // add waiter in order, form order, set order status cook_queue, and info need wait COOK, add listeners to catch COOK
        } else if(waiter == null && cook != null){
            // add cook in order, form order, set order status waiter, and info need wait waiter, add listeners to catch waiter
        } else {
            //set set order status ALL_queue
            //  and info need wait WAITER, COOK, add listeners to catch WAITER, COOK
        }
        List<Dish> dishes = new LinkedList<>();
        for (int i = 0; i < dishesId.length; i++) {
            Dish tempDish = orderService.getDish(Integer.valueOf(dishesId[i]));
            dishes.add(tempDish);
        }
        List<DishesInOrder> dishesInOrder = new LinkedList<>();
        for (int i = 0; i < dishesId.length; i++) {
            DishesInOrder tempDishesInOrder = new DishesInOrder();
            tempDishesInOrder.setQuantity(dishQuantities[i]);
            tempDishesInOrder.setDish(dishes.get(i));
            tempDishesInOrder.setOrderId(orderId);
            orderService.addDishesInOrder(tempDishesInOrder);
            dishesInOrder.add(tempDishesInOrder);
        }
        //create order
//        Table clientTable = orderService.getTableById(tableId);
//        Order order = new Order();
//        order.setId(orderId);
//        order.setClientId(clientId);
//        order.setOrderStatus("IS_FINISHED");
//        order.setInvoice(invoice);
//        order.setWaiterToService(waiter);
//        order.setCookToService(cook);
//        order.setTable(clientTable);
//        order.setTimestamp(System.currentTimeMillis());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Integer clientId = (Integer) session.getAttribute("clientId");
        logger.info("session" + req.getSession().getId() + "; req.getServletContext().getAttribute(\"clientId\") = " + clientId );
        if(clientId == null ){
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            logger.info("new order");
            List<Dish> dishes = orderService.getAllDishes();
            List<Table> tables = orderService.getAllTables();
            session.setAttribute("dishes", dishes);
            session.setAttribute("tables", tables);
            req.getRequestDispatcher("order.jsp").forward(req, resp);
        }


    }
}
