package servlets;

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

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(MenuServlet.class);
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer dishId = Integer.parseInt(request.getParameter("dishId"));
        if (dishId != null) {
            if (request.getParameter("delete") != null) {
                orderService.deleteDishById(dishId);
                doGet(request, response);
                logger.info("dish with id: " + dishId + " is deleted");
                return;
            }
            Double newPrice = Double.parseDouble(request.getParameter("newPrice"));
            String newDescription = request.getParameter("newDescription");
            if (newPrice != null && !newDescription.equals("")) {
                logger.info("info about dish id: " + dishId + "will be updated");
                orderService.updateDishById(dishId, newPrice, newDescription);
                doGet(request, response);
                return;
            }
            logger.info("either new price or descr is empty ");
        }
        logger.error("can't get dish id");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("menu.jsp page redirection");
        request.setAttribute("dishes", orderService.getAllDishes());
        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
}
