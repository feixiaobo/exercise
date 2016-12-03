package exercise.controller;

import exercise.Holder.ResponseHolder;
import exercise.enums.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by feixiaobo on 2016/11/22.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ResponseHolder responseHolder;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        responseHolder.setData(list);
        System.out.println("=====test===="+Thread.currentThread().getId());
    }
}
