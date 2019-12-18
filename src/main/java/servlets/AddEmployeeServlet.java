package servlets;

import entity.users.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_employee")
public class AddEmployeeServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(AddEmployeeServlet.class);
    private EmployeeService employeeService = new EmployeeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("emp name: " + request.getParameter("name"));

        Employee employee = new Employee();
        employee.setName(request.getParameter("name"));
        employee.setSurname(request.getParameter("surname"));
        employee.setEmail(request.getParameter("email"));
        employee.setPassword(request.getParameter("password"));
        logger.info("position of employee: " + request.getParameter("position"));
        employee.setPosition(request.getParameter("position"));
        employeeService.create(employee);
        response.sendRedirect("employees_managing");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add_employee.jsp").forward(request,response);
    }
}
