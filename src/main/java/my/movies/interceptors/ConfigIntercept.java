package my.movies.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ConfigIntercept extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptorHandler()).addPathPatterns("/admin");
        registry.addInterceptor(new AdminInterceptorHandler()).addPathPatterns("/admin/**");
    }
}
