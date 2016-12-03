package exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feixiaobo on 2016/11/20.
 */
public class threadTest {

    class NewThread implements Runnable {
        private List<String> list;

        public NewThread(List<String> list){
            this.list = list;
        }

        @Override
        public void run(){
            //process list
        }
    }

    public void useThread(List<String> list){
        NewThread newThread = new NewThread(list);
        for(int i = 0;i < 10 ;i++) {
            List<String> list1 = list.subList(i, i * 10);
            Thread thread = new Thread(newThread);
            thread.start();
        }
    }

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        threadTest tr = new threadTest();
        tr.useThread(list);
    }

}
