package exercise.controller;

import exercise.Holder.ResponseHolder;
import exercise.enums.ErrorMsg;
import exercise.model.ExeUserDetail;
import exercise.session.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController public class LoginLogoutController {

    @Autowired
    private ResponseHolder responseHolder;
    /**
     * 当访问请求未登录时,由Spring Security跳转至此交易,通知客户端其尚未登录
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET) public void loginNotify() {
        responseHolder.setErrorMsg(ErrorMsg.NO_LOGIN);
    }

    /**
     * 登录成功后,向请求方返回成功数据
     */
    @RequestMapping(path = "/loginSuccess") public void loginSuccess() {
        ExeUserDetail details = CurrentUser.getAdmin();
        Object data = null;
        if (details != null) {
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("id", details.getId());
            infoMap.put("status", details.getStatus());
            infoMap.put("username", details.getUsername());
            infoMap.put("realName", details.getRealName());
            infoMap.put("mobile", details.getMobile());
            infoMap.put("authorities", details.getAuthorities());
            data = infoMap;
        }
        responseHolder.setData(data);
    }

    /**
     * 登录校验失败后,向请求方返回失败数据
     */
    @RequestMapping(path = "/loginFail") public void loginFail(HttpServletRequest request) {
        AuthenticationException exception =
            (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        ErrorMsg code = ErrorMsg.LOGIN_FAIL;
        if(exception != null) {
            if(exception instanceof UsernameNotFoundException) {
                code = ErrorMsg.USER_NOT_FOUND;
            } else if (exception instanceof DisabledException) {
                code = ErrorMsg.USER_DISABLED;
            }
        }
        responseHolder.setErrorMsg(code);
    }

    /**
     * 登出通知
     */
    @RequestMapping(path = "/logoutSuccess") public void logoutSuccess() {
        responseHolder.setErrorMsg(ErrorMsg.LOGOUT_SUCCESS);
    }
}
