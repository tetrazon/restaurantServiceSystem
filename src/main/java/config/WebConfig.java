package config;

import dao.dataSourse.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({"controller", "service","config", "dao"})
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public DataSource(){
//
//    }

    /*@Bean
    //Configure a JSP view resolver
    //(Internal-ResourceViewResolver)
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }*/
//    @Bean
//    public InternalResourceViewResolver jspViewResolver() {
//        InternalResourceViewResolver resolver= new InternalResourceViewResolver();
//        resolver.setPrefix("/views/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

//    @Override
//    public void configureViewResolvers(final ViewResolverRegistry registry) {
//        registry.jsp("/views/", ".jsp");
//    }

    @Override
    //Configure static content handling
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //    @Bean
//    public ViewResolver cnViewResolver(ContentNegotiationManager cnm) {
//        ContentNegotiatingViewResolver cnvr =
//                new ContentNegotiatingViewResolver();
//        cnvr.setContentNegotiationManager(cnm);
//        return cnvr;
//    }
//
//    @Bean
//    //Look up views as beans
//    public ViewResolver beanNameViewResolver() {
//        return new BeanNameViewResolver();
//    }
//
//    @Bean
//    //getClients JSON view
//    public View getClients() {
//        return new MappingJackson2JsonView();
//    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);// TEXT_HTML
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {
        viewResolverRegistry.jsp("/views/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

}