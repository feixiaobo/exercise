package exercise.interceptor;

import com.alibaba.fastjson.JSON;
import exercise.holder.ResponseHolder;
import exercise.enums.ErrorMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by feixiaobo on 2016/11/24.
 */
@Component
public class ResponseInterceptor implements HandlerInterceptor {

    @Autowired
    private ResponseHolder responseHolder;

    private Logger logger = LogManager.getLogger(ResponseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest var1, HttpServletResponse var2, Object var3) throws Exception{
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest var1, HttpServletResponse var2, Object var3, ModelAndView var4) throws Exception{
        //do noting
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception{
        if(responseHolder == null){
            logger.error("system error", exception);
            responseHolder.setErrorMsg(ErrorMsg.SYSTEM_ERROR);
        }
        String json = JSON.toJSONString(responseHolder.getModel());
        this.returnJson(response,json);
        responseHolder.clean();
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
