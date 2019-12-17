package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({  "/manager_login"
        })//"/welcome_new.jsp", "/order.jsp", "/clients.jsp", "/clients","/managing"
public class FilterRequest implements Filter {
    private static Logger logger = LoggerFactory.getLogger(FilterRequest.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();
            logger.info("url: " + url);
            if(url.contains("index.jsp") || url.contains("manager_login")){
                filterChain.doFilter(servletRequest,servletResponse);
            } else{
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                resp.sendRedirect("/index.jsp");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
