package exercise.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by feixiaobo on 2016/11/14.
 */
@Data
public class Admin implements Serializable {
    public static Integer APP_DEFAULT = Integer.valueOf(0);
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private Integer status;
    private String email;
    private Date createTime;
    private Date updateTime;
}
