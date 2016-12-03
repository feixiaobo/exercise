package exercise.iText;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feixiaobo on 2016/11/7.
 */
public class FindKeyWords {

    static String keyWord = "客户相关信息明细如下";
    static float[] result = null;
    static int i = 0;

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/feixiaobo/Desktop/test.pdf";
        List list = getKeyWords(filePath);
        System.out.println(list);
    }

    public static List getKeyWords(String filePath) throws IOException {
        List list = new ArrayList<>();
        PdfReader pdfReader = new PdfReader(filePath);
        int pageNum = pdfReader.getNumberOfPages();
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

        //遍历查找关键字
        for (i = 1; i <= pageNum; i++) {
            pdfReaderContentParser.processContent(i, new RenderListener() {
                @Override public void beginTextBlock() {

                }

                @Override public void renderText(TextRenderInfo textRenderInfo) {
                    String text = textRenderInfo.getText();
                    if (text != null && text.contains(keyWord)) {
                        Rectangle2D.Float boundingRecatnge = textRenderInfo.getBaseline().getBoundingRectange();
                        result = new float[3];
                        result[0] = boundingRecatnge.x;
                        result[1] = boundingRecatnge.y;
                        result[2] = i;
                        list.add(String.format("%s,%s,%s   ", result[0], result[1], result[2]));
                    }

                }

                @Override public void endTextBlock() {

                }

                @Override public void renderImage(ImageRenderInfo imageRenderInfo) {

                }
            });
        }
        return list;
    }

    public static List getImagePostion(String filePath) throws IOException {
        List list = new ArrayList<>();
        PdfReader pdfReader = new PdfReader(filePath);
        int pageNum = pdfReader.getNumberOfPages();
        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

        for (i = 1; i <= pageNum; i++) {
            pdfReaderContentParser.processContent(i, new RenderListener() {
                @Override public void beginTextBlock() {

                }

                @Override public void renderText(TextRenderInfo textRenderInfo) {

                }

                @Override public void endTextBlock() {

                }

                @Override public void renderImage(ImageRenderInfo imageRenderInfo) {

                }
            });
        }
        return null;
    }
}
