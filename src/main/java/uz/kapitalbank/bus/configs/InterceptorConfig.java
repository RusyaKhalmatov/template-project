package uz.kapitalbank.bus.configs;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uz.kapitalbank.bus.interceptors.HttpInterceptor;
/**
 * @author Rustam Khalmatov
 */

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    private final HttpInterceptor interceptor;

    public InterceptorConfig(HttpInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
