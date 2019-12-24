package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/create_order", "/order.jsp", "/finish_order", "/finish_order.jsp", "/order_details", "/order_history.jsp"})
public class ClientFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ClientFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        Integer clientId = (Integer) session.getAttribute("clientId");
        String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        logger.info("url: " + url);
        if (clientId != null) {
            logger.info("session" + session.getId() + "; req.getServletContext().getAttribute(\"clientId\") = " + clientId );
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.info("not authorized");
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect("/reg.jsp");
        }
    }
}
