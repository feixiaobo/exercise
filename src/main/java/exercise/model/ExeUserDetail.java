package exercise.model;

import lombok.Data;

import java.util.List;

/**
 * Created by feixiaobo on 2016/11/14.
 */
@Data
public class ExeUserDetail {

    private Integer id;

    private String username;

    private String password;

    private String mobile;

    private String realName;

    private Integer status;

    private String email;

    private List<Role> roleList;

    private List<Authority> authorities;

    public void initUserDetail(Admin admin){
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.mobile = admin.getMobile();
        this.status = admin.getStatus();
        this.email = admin.getEmail();
    }
}
