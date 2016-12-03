package exercise.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class FreemakerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreemakerUtil.class);

    /**
     * 获取指定目录下的模板文件
     * @param name       模板文件的名称
     * @param path 模板文件的目录
     */
    public Template getTemplate(String name, String path) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23); //通过FreeMarker的Configuration对象可以读取ftl文件
        cfg.setClassForTemplateLoading(this.getClass(), path); //设置模板文件的目录
        cfg.setDefaultEncoding("UTF-8");       //Set the default charset of the template files
        Template temp = cfg.getTemplate(name); //在模板文件目录中寻找名为"name"的模板文件
        return temp; //此时FreeMarker就会到类路径下的"path"文件夹中寻找名为"name"的模板文件
    }

    /**
     * 根据模板文件输出内容到指定的文件中
     * @param name       模板文件的名称
     * @param path 模板文件的目录
     * @param rootMap    模板的数据模型
     * @param outputStream   输出流
     */
    public void replaceParams(String name, String path, Map<String, Object> rootMap, OutputStream outputStream) throws
        TemplateException, IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        outputStream.close();
        this.getTemplate(name, path).process(rootMap, out); //将模板文件内容以UTF-8编码输出到相应的流中
        if (null != out) {
            out.close();
        }
    }

    /**
     * 根据模板文件输出内容到控制台
     * @param name       模板文件的名称
     * @param pathPrefix 模板文件的目录
     * @param rootMap    模板的数据模型
     */
    public void print(String name, String pathPrefix, Map<String,Object> rootMap) throws
        TemplateException, IOException{
        this.getTemplate(name, pathPrefix).process(rootMap, new PrintWriter(System.out));
    }


    public static void main(String[] args){
        class B {
            float a;
            public float getA(){
                return a;
            }
        }
        B b = new B();
        System.out.println(b.getA());
    }

    //    public static void main(String[] args){
    //        try{
    //            //URL url = new URL("http://o2o-dev.wecash.net/image/57b5811dfb04d052bdc19dc0");
    //            URL url = new URL("http://123.56.9.46/image/55e66f3ce4b0b6e13964332a");
    //            URL url1 =
    //                HttpClientUtil.DataInputStream dataInputStream = new DataInputStream(url.openStream());
    //            String imageName = 111 + ".jpg";
    //            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
    //            IOUtils.copy(dataInputStream,fileOutputStream);
    //        }catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //
    //    }
}
