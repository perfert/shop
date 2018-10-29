/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.mas.common.exception.RuntimeEx;
import com.mas.common.file.FileException;
import com.mas.common.file.picture.mark.Coordinate;
import com.mas.common.file.picture.mark.Position;
import com.mas.common.file.util.FileMessage;
import com.mas.common.file.util.FileUtil;
import com.mas.common.file.util.StreamUtil;
import com.mas.common.verify.VerifyUtil;

/**
 * 图片缩放. </br>
 * 使用方型区域颜色平均算法. </br></br>
 *
 * Description: </br>
 * 	当按等比缩放图片时.</br>
 * 	在不超过源文件的宽和高的值的情况下, 按缩放目标的宽或高的最大尺寸量化比的宽和高值的作为最终缩放值.
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class PictureScale
{	
	/**
	 * 按等比缩放图片.</br>
	 * 在不超过源文件的宽和高的值的情况下, 按缩放目标的宽或高的最大尺寸量化比的宽和高的值作为最终缩放值.
	 * 
	 * @param src 源图片.
	 * @param dest 缩放后的目录图片.
	 * @param resizeWidth 缩放目标的宽度 (单位:PX).
	 * @param resizeHeight 缩放目标的高度 (单位:PX).
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void scaleFix(File src, File dest, int resizeWidth, int resizeHeight) throws FileException 
	{
		InputStream fileIn = null;
		InputStream in = null;
		
		try {
			scaleFix(in = new BufferedInputStream(fileIn = new FileInputStream(src)), dest, resizeWidth, resizeHeight);
		} catch (FileNotFoundException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			FileUtil.closingStream(fileIn, in);
		}
	}
	
	/**
	 * 按等比缩放图片.</br>
	 * 在不超过源文件的宽和高的值的情况下, 按缩放目标的宽或高的最大尺寸量化比的宽和高的值作为最终缩放值.
	 * 
	 * @param srcStream 源图片输入流.
	 * @param dest 缩放后的目录图片.
	 * @param resizeWidth 缩放目标的宽度 (单位:PX).
	 * @param resizeHeight 缩放目标的高度 (单位:PX).
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void scaleFix(InputStream srcStream, File dest, int resizeWidth, int resizeHeight) throws FileException 
	{
		BufferedImage srcImgBuff = null;
		OutputStream fileOut = null;
		OutputStream out = null;
		
		try {
			srcImgBuff = ImageIO.read(srcStream);
			
			if ( srcImgBuff.getWidth() <= resizeWidth && srcImgBuff.getHeight() <= resizeHeight ) {				// 原图尺寸比目标尺寸小, 直接 copy.
				StreamUtil.copy(srcStream, out = new BufferedOutputStream(fileOut = new FileOutputStream(dest)));
			} else {
				scaleFix(srcImgBuff, dest, resizeWidth, resizeHeight);
			}			
		} catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) { 
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			srcImgBuff = null;
			FileUtil.closingStream( fileOut, out);
		}
	}
	
	/**
	 * 剪裁图片, 并按等比缩放图片.</br>
	 * 在不超过源文件的宽和高的值的情况下, 按缩放目标的宽或高的最大尺寸量化比的宽和高的值作为最终缩放值.
	 * 
	 * @param src 源图片.
	 * @param dest 缩放后的目录图片.
	 * @param resizeWidth 缩放目标的宽度 (单位:PX).
	 * @param resizeHeight 缩放目标的高度 (单位:PX).
	 * @param cutXStart 剪裁 X 坐标起点值.
	 * @param cutYStart 剪裁 Y 坐标起点值.
	 * @param cutWidth 剪裁宽度值.
	 * @param cutHeight 剪裁高度值.
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void scaleAfterCutFix(File src, File dest, int resizeWidth, int resizeHeight, int cutXStart, int cutYStart, int cutWidth, int cutHeight) throws FileException 
	{
		InputStream fileIn = null;
		InputStream in = null;
		
		try {
			scaleAfterCutFix(in = new BufferedInputStream(fileIn = new FileInputStream(src)) , dest, resizeWidth, resizeHeight, cutXStart, cutYStart, cutWidth, cutHeight);
		} catch (FileNotFoundException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			FileUtil.closingStream(fileIn, in);
		}
	}

	/**
	 * 剪裁图片, 并按等比缩放图片.</br>
	 * 在不超过源文件的宽和高的值的情况下, 按缩放目标的宽或高的最大尺寸量化比的宽和高的值作为最终缩放值.
	 * 
	 * @param srcStream 源图片输入流.
	 * @param dest 缩放后的目录图片.
	 * @param resizeWidth 缩放目标的宽度 (单位:PX).
	 * @param resizeHeight 缩放目标的高度 (单位:PX).
	 * @param cutXStart 剪裁 X 坐标起点值.
	 * @param cutYStart 剪裁 Y 坐标起点值.
	 * @param cutWidth 剪裁宽度值.
	 * @param cutHeight 剪裁高度值.
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void scaleAfterCutFix(InputStream srcStream, File dest, int resizeWidth, int resizeHeight, int cutXStart, int cutYStart, int cutWidth, int cutHeight) throws FileException 
	{
		BufferedImage srcImgBuff = null;
		
		try {
			srcImgBuff = ImageIO.read(srcStream).getSubimage(cutXStart, cutYStart, cutWidth, cutHeight);
			
			if ( srcImgBuff.getWidth() <= resizeWidth && srcImgBuff.getHeight() <= resizeHeight ) {				// 原图剪裁后的尺寸比目标尺寸小, 直接 copy.
				writeFile(srcImgBuff, dest);
			} else {
				scaleFix(srcImgBuff, dest, resizeWidth, resizeHeight);
			}
		} catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) {
			throw ex;	
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			srcImgBuff = null;
		}
	}

	/**
	 * 添加水印图片.</br>
	 * 当水印图片的宽或高大于或等于源图片的宽或高时, 则不添加水印.
	 * 
	 * @param src 源图片.
	 * @param dest 目录图片.
	 * @param mark 水印图片.
	 * @param pos 水印图片添加到目标图片的位置.
	 * @param offsetX X 偏移值 (PX), 正数以结果坐标点为起点往 X 坐标最大值偏移; 负数以 X 坐标最大值为起点往 0 点方向偏移.
	 * @param offsetY Y 偏移量值 (PX), 正数以结果坐标点为起点往 Y 坐标最大值偏移; 负数以 Y 坐标最大值为起点往 0 点方向偏移.
	 * @param alpha 水印图片的透明度, (取值范围: 0-100, 越小越透明).
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void imageMark(File src, File dest, File mark, Position pos, int offsetX, int offsetY, int alpha) throws FileException 
	{
		BufferedImage srcImgBuff = null;
		BufferedImage markBuff = null;
		
		try {
			srcImgBuff = ImageIO.read(src);
			int width = srcImgBuff.getWidth();
			int height = srcImgBuff.getHeight();
			
			markBuff = ImageIO.read(mark);
			int markWidth = markBuff.getWidth();
			int markHeight = markBuff.getHeight();
			
			if ( width <= markWidth || height <= markHeight ) {
				if ( ! src.equals(dest) ) {
					FileUtil.copy(src, dest);
				}
			} else {
				Coordinate coordinate = new Coordinate(pos);
				imageMark(srcImgBuff, markBuff, coordinate.xCoordinate(width, markWidth, offsetX), coordinate.yCoordinate(height, markHeight, offsetY), alpha);
			}
		} 
		catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			srcImgBuff = null;
			markBuff = null;
		}
	}

	/**
	 * 添加水印文字.
	 * 
	 *  @param src 源图片.
	 * @param dest 目录图片.
	 * @param text 水印文字.
	 * @param color 水印文字的颜色.
	 * @param textSize 水印大小.
	 * @param pos 水印图片添加到目标图片的位置.
	 *  @param offsetX X 偏移值 (PX), 正数以结果坐标点为起点往 X 坐标最大值偏移; 负数以 X 坐标最大值为起点往 0 点方向偏移.
	 * @param offsetY Y 偏移量值 (PX), 正数以结果坐标点为起点往 Y 坐标最大值偏移; 负数以 Y 坐标最大值为起点往 0 点方向偏移.
	 * @param alpha 水印图片的透明度 (取值范围: 0-1).
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	public static void imageMark(File src, File dest, String text, Color color, int textSize, Position pos, int offsetX, int offsetY, int alpha) throws FileException 
	{
		if ( VerifyUtil.isBlank(text) ) {
			throw new RuntimeEx(FileMessage.msgByNoMarkText());
		}
		
		BufferedImage srcImgBuff = null;
		try {
			srcImgBuff = ImageIO.read(src);
			
			Coordinate coordinate = new Coordinate(pos);
			
			imageMark(
					srcImgBuff
					, text
					, ( null != color ? color : PictureUtil.parseToColor("000000") )			// 颜色 (默认:黑色)
					, new Font(null, Font.PLAIN, 0 >= textSize ? 24 : textSize)					// 字体大小 (默认:24PX)
					, coordinate.xCoordinate(srcImgBuff.getWidth(), text.length() * textSize / 2, offsetX)
					, coordinate.yCoordinate(srcImgBuff.getHeight(), text.length() * textSize / 2, offsetY)
					, alpha
					);
			
			writeFile(srcImgBuff, dest);
		} catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			srcImgBuff = null;
		}
	}
	
	/**
	 * 缩放图片.</br>
	 * 
	 * @param srcImgBuff 源图片Buffer.
	 * @param dest 缩放后的目录图片.
	 * @param resizeWidth 缩放目标的宽度 (单位:PX).
	 * @param resizeHeight 缩放目标的高度 (单位:PX).
	 * 
	 * @throws FileException 可能抛出的异常.
	 */
	private static void scaleFix(BufferedImage srcImgBuff, File dest, int resizeWidth, int resizeHeight) throws FileException
	{
		assertScalWidthNeedGreaterZero(resizeWidth);
		assertScalHeightNeedGreaterZero(resizeHeight);
		
		int width = srcImgBuff.getWidth();
		int height = srcImgBuff.getHeight();
		int realWidth, realHeight;
		
		resizeWidth = Math.min(width, resizeWidth);
		resizeHeight = Math.min(height, resizeHeight);
		
		// 最大量化缩放比尺寸.
		if ( (float) width / resizeWidth > (float) height / resizeHeight ) {
			realWidth = resizeWidth;
			realHeight = Math.round( (float) resizeWidth * height / width );
		} else {
			realWidth = Math.round( (float) resizeHeight * width / height );
			realHeight = resizeHeight;
		}
		
		BufferedImage imgBuff = scaleHandle(srcImgBuff, realWidth, realHeight);
		try {
			writeFile(imgBuff, dest);
		} catch (FileException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new FileException(ex);
		} finally {
			imgBuff = null;
		}
	}
		
	/**
	 * 图片缩放处理.
	 * 
	 * @param srcImgBuff 源图片Buffer.
	 * @param resizeWidth 缩放后的宽.
	 * @param resizeHeight 缩放后的高.
	 * 
	 * @return 缩放后的图片Buffer
	 */
	private static BufferedImage scaleHandle(BufferedImage srcImgBuff, int resizeWidth, int resizeHeight) 
	{
		assertScalWidthNeedGreaterZero(resizeWidth);
		assertScalHeightNeedGreaterZero(resizeHeight);
		
		// 源图片的宽高值.
		final int width = srcImgBuff.getWidth();
		final int height = srcImgBuff.getHeight();
		
		int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0, width);
		BufferedImage outBuff = new BufferedImage(resizeWidth, resizeHeight, BufferedImage.TYPE_INT_RGB);
		
		// 宽缩小的倍数.
		float wScale = (float) width / resizeWidth;
		int wScaleInt = (int) (wScale + 0.5);
		
		// 高缩小的倍数.
		float hScale = (float) height / resizeHeight;
		int hScaleInt = (int) (hScale + 0.5);
		
		int area = wScaleInt * hScaleInt;
		int x0, x1, y0, y1;
		int color;
		long red, green, blue;
		int x, y, i, j;
		
		for ( y = 0; y < resizeHeight; y++ ) 
		{
			// 得到原图高的Y坐标
			y0 = (int) (y * hScale);
			y1 = y0 + hScaleInt;
			for ( x = 0; x < resizeWidth; x++ ) 
			{
				x0 = (int) (x * wScale);
				x1 = x0 + wScaleInt;
				red = green = blue = 0;
				for ( i = x0; i < x1; i++ ) 
				{
					for ( j = y0; j < y1; j++ ) {
						color = colorArray[width * j + i];
						red += getRedValue(color);
						green += getGreenValue(color);
						blue += getBlueValue(color);
					}
				}
				outBuff.setRGB(x, y, comRGB((int) (red / area), (int) (green / area), (int) (blue / area)));
			}
		}
		return outBuff;
	}
	
	/**
	 * 打印水印图片.
	 * 
	 * @param imgBuff 打印目标图片Buffer.
	 * @param markBuff 水印图片Buffer.
	 * @param coordinateX 水印在目标图片的 X 坐标起点.
	 * @param coordinateY 水印在目标图片的 Y 坐标起点.
	 * @param alpha 水印图片的透明度 (取值范围: 0-100, 越小越透明).
	 */
	private static void imageMark(BufferedImage imgBuff, BufferedImage markBuff, int coordinateX, int coordinateY, int alpha)
	{
		Graphics2D g2d = imgBuff.createGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0 <= alpha && 100 >= alpha ? (float) alpha / 100 : 1F);
		g2d.setComposite(alphaComposite);		
		g2d.drawImage(markBuff, coordinateX, coordinateY, null);
		g2d.dispose();
	}
	
	/**
	 * 打印水印文字.
	 * 
	 * @param imgBuff 打印目标图片Buffer.
	 * @param text 水印文字.
	 * @param color 水印文字颜色.
	 * @param font 水印文字样式对象.
	 * @param coordinateX 水印在目标图片的 X 坐标起点.
	 * @param coordinateY 水印在目标图片的 Y 坐标起点.
	 * @param alpha 水印图片的透明度 (取值范围: 0-100, 越小越透明).
	 */
	private static void imageMark(BufferedImage imgBuff, String text, Color color, Font font, int coordinateX, int coordinateY, int alpha)
	{
		Graphics2D g2d = imgBuff.createGraphics();
		g2d.setColor(color);
		g2d.setFont(font);		//new Font(null, Font.PLAIN, textSize));		
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0 <= alpha && 100 >= alpha ? (float) alpha / 100 : 1F);
		g2d.setComposite(alphaComposite);	
		g2d.drawString(text, coordinateX, coordinateY);
		g2d.dispose();
	}
	
	/**
	 * 输出图片Buffer到目标图片.
	 * 
	 * @param imgBuf 图片Buffer.
	 * @param dest 目标图片.
	 * 
	 * @return true or false.
	 */
	private static boolean writeFile(BufferedImage imgBuff, File dest) throws FileException
	{
		FileUtil.mkdirs(dest.getAbsolutePath());		
		try {
			return ImageIO.write(imgBuff, "jpeg", dest);
		} catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		}
	}
			
	private static int getRedValue(int rgbValue) 
	{
		return (rgbValue & 0x00ff0000) >> 16;
	}

	private static int getGreenValue(int rgbValue) 
	{
		return (rgbValue & 0x0000ff00) >> 8;
	}

	private static int getBlueValue(int rgbValue) 
	{
		return rgbValue & 0x000000ff;
	}

	private static int comRGB(int redValue, int greenValue, int blueValue) 
	{
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}
	
	private static void assertScalWidthNeedGreaterZero(int width)
	{
		if ( 0 >= width ) {
			throw new RuntimeEx(FileMessage.msgByScalWidthNeedGreaterZero(width));
		}
	}
	
	private static void assertScalHeightNeedGreaterZero(int height)
	{
		if ( 0 >= height ) {
			throw new RuntimeEx(FileMessage.msgByScalHeightNeedGreaterZero(height));
		}
	}
		
	private PictureScale() { super(); }
}