package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/employees_managing")
public class EmployeesManagingServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ClientsManagingServlet.class);
    private EmployeeService employeeService = new EmployeeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer employeeId = Integer.parseInt(request.getParameter("employeeId"));
        if(employeeId != null && request.getParameter("delete").equals("y")){
            employeeService.deleteEmployeeById(employeeId);
            logger.info("deleted employee. id: " + employeeId);
        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer managerId = (Integer) session.getAttribute("emplId");
        request.setAttribute("employees", employeeService.getAllEmployees(managerId));
        request.getRequestDispatcher("employees_managing.jsp").forward(request, response);
    }
}
