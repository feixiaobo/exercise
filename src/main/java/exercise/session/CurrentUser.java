package exercise.session;

import exercise.model.ExeUserDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
    protected static Logger logger = LogManager.getLogger(CurrentUser.class);
    /**
     * @return 当前登录的用户信息
     */
    public static ExeUserDetail getAdmin() {
        try {
            return (ExeUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            logger.error("get current user exception:", e);
            return new ExeUserDetail();
        }
    }
}
