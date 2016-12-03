package exercise.service;

import exercise.model.Admin;
import exercise.model.Authority;
import exercise.model.Role;

import java.util.List;

/**
 * Created by feixiaobo on 2016/11/14.
 */
public interface UserInfoService {

    Admin getUserDetailByUsername(String username);

    List<Role> getRoleByUsername(String username);

    List<Authority> getAuthorities(String username);
}
