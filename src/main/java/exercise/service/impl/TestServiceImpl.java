package exercise.service.impl;

import exercise.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by feixiaobo on 2016/10/26.
 */
@Service
public class TestServiceImpl implements TestService{

    @Override
    public void test(){
        System.out.println("==============");
    }
}
