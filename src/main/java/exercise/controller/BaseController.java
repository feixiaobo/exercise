package exercise.controller;

import com.alibaba.fastjson.JSON;
import exercise.holder.ResponseHolder;
import exercise.enums.ErrorMsg;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 通用处理controller，所有controller继承此类
 * Created by feixiaobo on 2016/11/22.
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class BaseController {
    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired protected ApplicationContext applicationContext;

    @Autowired protected ResponseHolder responseHolder;

    @ExceptionHandler(value = {RuntimeException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void exception(Exception exception, WebRequest request, HttpServletResponse response) {
        try {
            logger.info("Catch an exception,exception:{}", exception);
            responseHolder.setErrorMsg(ErrorMsg.SYSTEM_ERROR);
            this.returnJson(response, JSON.toJSONString(responseHolder.getModel()));
        }catch (Exception e){
            //
        }
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();

        }
    }
}
