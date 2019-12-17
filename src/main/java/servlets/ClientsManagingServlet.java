package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/clients_managing")
public class ClientsManagingServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ClientsManagingServlet.class);
    private ClientService clientService = new ClientService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer clientId = Integer.parseInt(request.getParameter("clientId"));
        String clientNewDeposit = request.getParameter("new_deposit");
        logger.info("client id: " + clientId + "; deposit: " + clientNewDeposit);
        if(clientId != null && clientNewDeposit != null){
            double doubleDeposit = Double.parseDouble(clientNewDeposit);
            clientService.updateDeposit(clientId, doubleDeposit);
            logger.info("deposit updated for client: " + clientId + "; new deposit is:" + doubleDeposit);
        }
        if(request.getParameter("delete")!= null){
            clientService.deleteClient(clientId);
            logger.info("deleted client. id: " + clientId);
        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("clients", clientService.getAll());
        request.getRequestDispatcher("clients_managing.jsp").forward(request,response);
    }

}
