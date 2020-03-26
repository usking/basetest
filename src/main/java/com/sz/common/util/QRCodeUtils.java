package com.sz.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtils {
	
	
	public static void doQRCode(OutputStream outputStream,String text,int width,int height,String logoPath) throws WriterException, IOException {
		int BLACK=0xFF000000;
		int WHITE=0xFFFFFFFF;
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);	
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		
		//插入logo
		if(!StringUtils.isBlank(logoPath)) {
			Image logo = ImageIO.read(new File(logoPath));
			int logoWidth = logo.getWidth(null)/6;
			int logoHeight = logo.getHeight(null)/6;
			Graphics2D graph = image.createGraphics();
			int x = (width-logoWidth) / 2;
			int y = (height-logoHeight) / 2;
			graph.drawImage(logo, x, y, logoWidth, logoHeight, null);
			graph.drawRoundRect(x, y, logoWidth, logoHeight, 10, 10);
			graph.drawRect(x, y, logoWidth, logoHeight);
			graph.dispose();
		}
		
		ImageIO.write(image, "png", outputStream);
	}

	public static void main(String[] args) {
		String text="http://www.baidu.com";
		String dest="E:/logs/qrcode/a.png";
		int width=250;
		int height=250;
		String logoPath="E:/logs/qrcode/logo.jpg";
		try {
			OutputStream out=new FileOutputStream(new File(dest));
			QRCodeUtils.doQRCode(out, text, width, height, null);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("main执行结束");
	}

}
