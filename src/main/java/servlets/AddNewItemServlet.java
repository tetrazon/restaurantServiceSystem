package servlets;

import entity.food.Dish;
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

@WebServlet("/add_menu_item")
public class AddNewItemServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(AddNewItemServlet.class);
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        logger.info("empl id:***" + session.getAttribute("emplId") + "***");
        logger.info("adding item, food category: " + request.getParameter("foodCategory"));
        Dish dishToAdd = new Dish();
        dishToAdd.setName(request.getParameter("dishName"));
        dishToAdd.setPrice(Double.parseDouble(request.getParameter("dishPrice")));
        dishToAdd.setDescription(request.getParameter("dishDescription"));
        dishToAdd.setFoodCategory(request.getParameter("foodCategory"));
        orderService.addDish(dishToAdd);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            logger.info("add_menu_item.jsp redirection");
            request.setAttribute("dishes", orderService.getAllDishes());
        request.getRequestDispatcher("add_menu_item.jsp").forward(request, response);
    }
}
