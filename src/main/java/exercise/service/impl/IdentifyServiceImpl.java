package exercise.service.impl;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import exercise.service.IdentifyService;
import exercise.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by feixiaobo on 2016/11/29.
 */
@Service
public class IdentifyServiceImpl extends KaptchaServlet implements IdentifyService {

    /**
     * 缓存失效时间（单位：秒）
     */
    private final int EXPIRE = 30 * 60; //验证码半小时失效
    private Logger logger = LogManager.getLogger(IdentifyServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Override
    public String getCode(String key, HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        super.init(initServletConfig(req));
        super.doGet(req, resp);
        String code = (String) req.getSession().getAttribute("KAPTCHA_SESSION_KEY");
        redisService.setObject(key,code, EXPIRE);
        logger.info("save code to redis ! key : {}, code : {}",key,code);
        return code;
    }

    @Override
    public boolean verifyCode(String key, String code){
        String redisCode = redisService.getObject(key);
        redisService.remove(key);
        if(StringUtils.isBlank(redisCode)){
            return false;
        }
        logger.info("get right code from redis ! key : {}, code : {}",key,redisCode);
        return redisCode.equals(code)? true : false;
    }

    private ServletConfig initServletConfig(HttpServletRequest req){
        ServletConfig servletConfig = new ServletConfig() {
            @Override public String getServletName() {
               // return req.getSession().getServletContext().getServletContextName();
                return null;
            }

            @Override public ServletContext getServletContext() {
                return req.getSession().getServletContext();
            }

            @Override public String getInitParameter(String s) {
                //return req.getSession().getServletContext().getInitParameter(s);
                return null;
            }

            @Override public Enumeration<String> getInitParameterNames() {
                return req.getSession().getServletContext().getInitParameterNames();
            }
        };
        return servletConfig;
    }

}
