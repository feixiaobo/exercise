package exercise.iText;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.xmlbeans.impl.common.IOUtil;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class GetPDF {
    private static final String fontPath = "/Users/feixiaobo/Desktop/simsun.ttf";

    public static void main(String[] args) throws IOException {
        String html = "/Users/feixiaobo/Desktop/ruiyinBusiness.html";
        String pdf = "/Users/feixiaobo/Desktop/ruiyinBusiness.pdf";
        File pdfFile = new File(pdf);
        InputStream inputStream = htmlToPDF(new FileInputStream(new File(html)));
        try{
            if(!pdfFile.exists()){
                pdfFile.createNewFile();
            }
        }catch (IOException e ){
        }
        IOUtil.copyCompletely(inputStream,new FileOutputStream(pdf));
    }

    public static InputStream htmlToPDF(InputStream htmlInputStream) {
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
        } catch (IOException e) {
            document.close();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return inputStream;
    }
}
