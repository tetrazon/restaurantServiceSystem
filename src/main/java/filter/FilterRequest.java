package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/clients", "/create_order", "/managing"})
public class FilterRequest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();
            if(url.contains("index.jsp") ){
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
