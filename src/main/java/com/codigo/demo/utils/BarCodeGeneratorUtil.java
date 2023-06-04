package com.codigo.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class BarCodeGeneratorUtil {

	public static byte[] getQRCodeImageByte(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}

	public static String getBase64QRImage(String text) {
		String imageBase64 = null;
		try {
			var image = getQRCodeImageByte(text, 250, 250);
			imageBase64 = Base64.getEncoder().encodeToString(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageBase64;

	}

	public static String readQRCodeByImageByte(byte[] base64)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new ByteArrayInputStream(base64)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		return qrCodeResult.getText();
	}

	public static String readQRCodeByBase64(String base64)
			throws FileNotFoundException, IOException, NotFoundException {
		byte[] imageByte = DatatypeConverter.parseBase64Binary(base64);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(ImageIO.read(new ByteArrayInputStream(imageByte)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		return qrCodeResult.getText();
	}

}
