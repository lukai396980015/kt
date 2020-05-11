package org.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author lukai
 * @TODO TODO自定义描述
 * @create 2020-05-11 9:30
 */
public class QRCode
{
    private static final String QR_CODE_IMAGE_PATH = "D:\\log\\MyQRCode.png";
    private static final String CUSTOMIZE_PIC_IMAGE_PATH = "D:\\log\\CUSTOM.png";
    private static final String RESULT_PATH = "D:\\log\\MyQRCode_result.png";

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
            String formatStr = new String(str.getBytes(),"UTF-8");
            generateQRCodeImage(formatStr, 350, 350, QR_CODE_IMAGE_PATH);
            String br = FileUtil.getContents(CUSTOMIZE_PIC_IMAGE_PATH,null);
            //ImageInputStream imageis = ImageIO.createImageInputStream(new ByteArrayInputStream(br.getBytes()));
            BufferedImage customize_pic_image_buff = ImageIO.read(new File(CUSTOMIZE_PIC_IMAGE_PATH));
            BufferedImage qr_code_image_buff = ImageIO.read(new File(QR_CODE_IMAGE_PATH));
            Graphics2D graphics = qr_code_image_buff.createGraphics();
            int widthQRCode = qr_code_image_buff.getWidth();
            int heightQRCode = qr_code_image_buff.getHeight();
            int widthLogo = customize_pic_image_buff.getWidth();
            int heightLogo = customize_pic_image_buff.getHeight();
            BufferedImage result = new BufferedImage(widthQRCode,heightQRCode,BufferedImage.TYPE_INT_ARGB);
            Graphics2D resultGraphics = result.createGraphics();
;            //中间图片不能大于二维码本身
            if(widthQRCode<widthLogo)
            {
                return;
            }
            if(heightQRCode<heightLogo)
            {
                return;
            }
            int x = (widthQRCode - widthLogo) / 2;
            int y = (heightQRCode - heightLogo) / 2;
            resultGraphics.drawImage(qr_code_image_buff,0,0,widthQRCode,heightQRCode,null);
            resultGraphics.drawImage(customize_pic_image_buff,x,y,widthLogo,heightLogo,null);

            resultGraphics.drawRoundRect(x, y, widthLogo, heightLogo, 10, 10);
            //graphics.setStroke(new BasicStroke(3f));
            //graphics.setColor(new Color(255, 255, 255,0));
            resultGraphics.drawRect(x, y, widthLogo, heightLogo);

            resultGraphics.dispose();

            ImageIO.write(result, "PNG", new File(RESULT_PATH));

        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

    }
    
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException,
        IOException
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,0);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,hints);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
    
}
