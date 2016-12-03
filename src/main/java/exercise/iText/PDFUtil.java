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

}
