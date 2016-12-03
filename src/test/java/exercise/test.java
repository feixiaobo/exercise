package exercise;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by feixiaobo on 2016/10/21.
 */
public class test {
//    implements Runnable {
//    @Override public void run() {
//        for (int i = 0; i < 10; i++) {
//            try {
//                System.out.println("执行，休眠");
//                Thread.sleep(1000);
//                System.out.println("休眠结束");
//            } catch (Exception e) {
//                //
//            }
//        }
//    }



//    public static void main(String[] args) {
//        try {
//            List<String> list = new ArrayList<>();
//            for (int i = 0; i < 100; i++) {
//                list.add(i + ",");
//            }
//
//            System.out.println(new test().list2Str(list, 5));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

        return ret.toString();
    }

    public static List<String> readTxtFile(String filePath){
        File file=new File(filePath);//创建文件对象
        List<String> list = new ArrayList<>();
        String encoding="UTF-8";//设置读取文件的编码格式
        if(file.isFile()&&file.exists()){//判断文件是否存在
            try {
                FileInputStream fisr=new FileInputStream(file);
                //FileInputStream创建文件输入流,FileInputStream类是以字节读取文件的
                InputStreamReader isr=new InputStreamReader(fisr,encoding);//封装文件输入流，并设置编码方式
                //InputStreamReader是字节流转向字符流的桥梁，读取文本文件当然可以用字节流，但是使用字符流会更加的方便
                /*如果处理的是文本文件的话，下面两条语句几乎相同
                 * InputStreamReader in=new InputStreamReader(new FileInputStream(file));
                 * FileReader fin=new FileReader(file);
                 */
                BufferedReader br=new BufferedReader(isr);
                //BufferedInputStream将InputStreamReader中的数据存入缓冲区,它不改变FileInputSteam中数据的类型
                //BufferedInputStream是将多个输入的数据放入一个缓冲区中以便一次性操作
                String txt=null;
                while((txt=br.readLine())!=null){//按行读取文件，每次读取一行
                    System.out.println(txt);
                    list.add(txt);
                }
                fisr.close();
                isr.close();
                br.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }


    public static void main(String[] args){
        String filePath = "/Users/feixiaobo/Desktop/常用账号地址.txt";
        String newFile = "/Users/feixiaobo/Desktop/test.txt";
        List<String> list = readTxtFile(filePath);
        FileOutputStream outputStream = null;
        try {
            String str = list2Str(list, 10);
            File file = new File(newFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(str.getBytes());
        }catch (Exception e){
            //
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (IOException e){
                //
            }
        }
    }
}
