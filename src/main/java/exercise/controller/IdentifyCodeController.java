package exercise.controller;

import exercise.holder.ResponseHolder;
import exercise.enums.ErrorMsg;
import exercise.service.IdentifyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by feixiaobo on 2016/11/29.
 */
@RestController
@RequestMapping("/identifyCode")
public class IdentifyCodeController {

    private static final String INDENTIFY_CODE ="identify_code_register:%s";
    private Logger logger = LogManager.getLogger(IdentifyCodeController.class);
    @Autowired
    private IdentifyService identifyService;

    @Autowired
    private ResponseHolder responseHolder;

    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public void generateCode(String mobile, HttpServletRequest req, HttpServletResponse resp){
        try {
            String code = identifyService.getCode(String.format(INDENTIFY_CODE, mobile), req, resp);
            logger.info("get verify code ! mobile : {}, code : {}",mobile,code);
        }catch (Exception e){
            logger.error("get verify code error! mobile : {}",mobile);
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public void verifyCode(String mobile, String code){
        boolean result = identifyService.verifyCode(String.format(INDENTIFY_CODE, mobile),code);
        if(result){
            logger.info("验证通过 ! mobile : {}, code : {}",mobile,code);
        }else {
            logger.info("验证不通过 ! mobile : {}, code : {}",mobile,code);
            responseHolder.setErrorMsg(ErrorMsg.VERIFY_CODE_ERROR);
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format(INDENTIFY_CODE,"1352137263"));
    }
}
