package exercise.iText;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    //@Value("${fonts.path}") private String fontPath;
    private  String fontPath = "/Users/feixiaobo/Downloads/simsun.ttf";

    public InputStream htmlToPDF(InputStream htmlInputStream) {
        ByteArrayOutputStream out = null;
        ByteArrayInputStream inputStream = null;
        Document document = new Document();
        XMLWorkerFontProvider provider = new XMLWorkerFontProvider();
        provider.register(fontPath);
        try {
            out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new BufferedInputStream(htmlInputStream), Charset.forName("utf-8"), provider);
            document.close();
            inputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (DocumentException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return inputStream;
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
        this.getTemplate(name, path).process(rootMap, out); //将模板文件内容以UTF-8编码输出到相应的流中
        if (null != out) {
            out.close();
        }
    }

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
}
