package exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by feixiaobo on 2016/11/20.
 */
public class ThreadsTest {

    public static String listToStr(List<String> list){
        StringBuffer str = new StringBuffer();
        Random random = new Random(10);
        Map<Integer,List<String>> group = list.parallelStream().collect(Collectors.groupingBy(e -> random.nextInt(10)));
        group.values().parallelStream().forEach(e -> e.forEach(str::append));
        System.out.println("method1: "+str.toString());
        return str.toString();
    }

    public static String list2Str(List<String> list, final int nThreads) throws Exception {
        if (list == null || list.isEmpty()) {
            return null;
        }

        StringBuffer ret = new StringBuffer();
        int size = list.size();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<String>> futures = new ArrayList<Future<String>>(nThreads);

        for (int i = 0; i < nThreads; i++) {
            final List<String> subList = list.subList(size / nThreads * i, size / nThreads * (i + 1));
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    StringBuffer sb = new StringBuffer();
                    for (String str : subList) {
                        sb.append(str);
                    }
                    return sb.toString();
                }
            };
            futures.add(executorService.submit(task));
        }

        for (Future<String> future : futures) {
            ret.append(future.get());
        }
        executorService.shutdown();
        System.out.println("method2: "+ret.toString());
        return ret.toString();
    }


    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        for(int i=0;i<1000;i++){
            list.add(String.valueOf(i));
        }
        list2Str(list,10);
        listToStr(list);
    }
}
