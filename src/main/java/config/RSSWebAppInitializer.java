package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
public class RSSWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    @Override
    //Configuration class’s returned get-
    //RootConfigClasses() will be used to configure the application context created by
    //ContextLoaderListener.
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    //Dispatcher-Servlet load its application context with beans defined in the WebConfig configura-
    //tion class (using Java configuration)
    //The @Configuration
    //classes returned from getServletConfigClasses() will define beans for Dispatcher-
    //Servlet’s application context
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    /*{
        return new Class[0];
    }*/
    }
}
