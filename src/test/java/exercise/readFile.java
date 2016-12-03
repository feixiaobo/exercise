package exercise;

import java.io.*;

/**
 * Created by feixiaobo on 2016/11/20.
 */
public class readFile {

    public static void readTxtFile(String filePath){
        File file=new File(filePath);//创建文件对象
        String encoding="GBK";//设置读取文件的编码格式
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
    }
    public static void main(String[] args) {
        String filePath="F:\\test\\testR.txt";//在F盘创建test文件夹，在文件夹下创建testR.txt文件
        readTxtFile(filePath);
    }
}
