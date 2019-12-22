package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/add_employee.jsp", "/add_menu_item.jsp", "/clients_managing.jsp",
        "/employees_managing.jsp", "/manage_options.jsp", "/menu.jsp", "/add_employee",
        "/add_menu_item", "/clients_managing", "/employees_managing", "/menu"})
public class ManagerFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ManagerFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
            Integer emplId = (Integer) session.getAttribute("emplId");
            String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
            logger.info("url: " + url);
            if (emplId != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.info("not authorized");
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.sendRedirect("/manager_login");
            }

        }
    }

}
