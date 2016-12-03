package exercise.config;

import exercise.interceptor.ResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Created by feixiaobo on 2016/11/24.
 */
@Configuration
public class WebAppConfig extends org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter {

    @Autowired
    private ResponseInterceptor responseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseInterceptor).addPathPatterns("/**").excludePathPatterns("/identifyCode/generate");
    }

}
