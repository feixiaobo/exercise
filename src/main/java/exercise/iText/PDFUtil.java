package exercise.iText;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class PDFUtil {
    private static final Logger logger = LoggerFactory.getLogger(PDFUtil.class);

    //@Value("${fonts.path}") private String fontPath;
    private static String fontPath = "/Users/feixiaobo/Downloads/simsun.ttf";

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
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            document.close();
            logger.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
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

//    public void exportPdf() {
//        Document document=null;
//        try {
//            BaseFont bfChinese = BaseFont.createFont(TestPDF.class.getClassLoader().getResource("template/fonts/wqywmh.ttc").toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 设置中文字体
//            //BaseFont bfChinese = BaseFont.createFont("Chinese", "UTF-8", BaseFont.NOT_EMBEDDED);// 设置中文字体
//            Font headFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小
//
//            //ITextRenderer renderer = new ITextRenderer();
//
//            // 处理中文字体
//            //renderer.getFontResolver().addFont(PDFUtils.class.getClassLoader().getResource("template/fonts/wqywmh.ttc").toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//            //第一步：创建一个document对象。
//            document = new Document();
//            //第二步：创建一个PdfWriter实例，将文件输出流指向一个文件。
//            PdfWriter.getInstance(document, new FileOutputStream("/Users/feixiaobo/Desktop/123.pdf"));
//            //第三步：打开文档。
//            document.open();
//            Paragraph title = new Paragraph("你好，Pdf！", headFont);
//            //第四步：在文档中增加一个段落。
//            document.add(title);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            if(document!=null){
//                //第五步：关闭文档。
//                document.close();
//            }
//        }
//
//    }
}
