package exercise.enums;

/**
 * Created by feixiaobo on 2016/11/22.
 */
public enum ResponseCodeEnum {

    SUCCESS(100),
    LOGIN_FAIL(101),
    LOGOUT_SUCCESS(104),
    USER_NOT_FOUND(102),
    USER_DISABLED(103),
    NO_LOGIN(105),
    ERROR(300);

    private int code;

    ResponseCodeEnum(int code){
        this.code = code;
    }
    public Integer getCode(){
        return code;
    }
}
