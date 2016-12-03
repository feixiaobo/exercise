package exercise.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 自定义filter
 * Created by feixiaobo on 2016/11/30.
 */
@Component
public class DoFilter implements Filter {

    @Override
    public void init(FilterConfig var1) throws ServletException{

    }

    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException{

    }

    @Override
    public void destroy(){

    }
}
