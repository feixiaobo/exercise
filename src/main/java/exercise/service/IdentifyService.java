package exercise.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by feixiaobo on 2016/11/29.
 */
public interface IdentifyService {

    /**
     * 获取验证码
     * @return
     */
    String getCode(String key, HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException;

    /**
     * 验证验证码
     * @return
     */
    boolean verifyCode(String key, String code);

}
