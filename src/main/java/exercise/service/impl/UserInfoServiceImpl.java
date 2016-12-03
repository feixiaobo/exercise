package exercise.service.impl;

import exercise.model.Admin;
import exercise.model.Authority;
import exercise.model.Role;
import exercise.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by feixiaobo on 2016/11/14.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public Admin getUserDetailByUsername(String username){
        return null;
    }

    @Override
    public List<Role> getRoleByUsername(String username){
        return null;
    }

    @Override
    public List<Authority> getAuthorities(String username){
        return null;
    }
}
