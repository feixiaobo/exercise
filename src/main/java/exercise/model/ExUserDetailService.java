package exercise.model;

import exercise.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by feixiaobo on 2016/11/14.
 */
public class ExUserDetailService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException{
        return null;
    }

    private ExeUserDetail initDetail(Admin admin){
        ExeUserDetail detail = (ExeUserDetail) context.getBean("userDetail");
        detail.initUserDetail(admin);
        detail.setRoleList(userInfoService.getRoleByUsername(admin.getUsername()));
        detail.setAuthorities(userInfoService.getAuthorities(admin.getUsername()));
        return detail;
    }
}
