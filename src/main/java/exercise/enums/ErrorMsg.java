package exercise.enums;

/**
 * Created by feixiaobo on 2016/11/22.
 */
public enum ErrorMsg {

    TEST(1,"test"),
    SUCCESS(200,"交易完成"),
    LOGIN_FAIL(101,"登录失败"),
    LOGOUT_SUCCESS(104,"登出成功"),
    USER_NOT_FOUND(102,"未注册用户"),
    USER_DISABLED(103,"无效用户"),
    NO_LOGIN(105,"未登录"),
    VERIFY_CODE_ERROR(106,"验证码错误"),
    SYSTEM_ERROR(100,"系统错误");

    private int code;
    private String message;

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

    ErrorMsg(int code, String message){
        this.code = code;
        this.message = message;
    }
}
