/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mas.common.file.FileException;

/**
 * Input/Output stream auxiliary utility. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class StreamUtil 
{
	public static final int BUFFER_SIZE = 4096;
	
	/**
	 * The byte array is written to the output stream (in bytes way to write).
	 * 
	 * @param in Byte array.
	 * @param out Output stream.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static void copy(byte[] in, OutputStream out) throws FileException
	{		
		if ( null != in && 0 < in.length ) {
			try {
				out.write(in);
			} catch (IOException ex) {
				throw new FileException(FileMessage.msgByIOException(ex), ex);
			} catch (Exception ex) {
				throw new FileException(ex);
			}
		}
	}
	
	/**
	 * The input stream is written to the output stream (in bytes way to write). </br>
	 * The default buffer value to the 4096B.
	 * 
	 * @param in Input stream.
	 * @param out Output stream.
	 * 
	 * @return Total number of bytes written.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static int copy(InputStream in, OutputStream out) throws FileException
	{
		return copy(in, out, BUFFER_SIZE);
	}
	
	/**
	 * The input stream is written to the output stream (in bytes way to write). 
	 * 
	 * @param in Input stream.
	 * @param out Output stream.
	 * @param bufferSize Each write buffer value.
	 * 
	 * @return Total number of bytes written.
	 * 
	 * @throws FileException Maybe throws exception.
	 */
	public static int copy(InputStream in, OutputStream out, int bufferSize) throws FileException 
	{
		byte[] buffer = new byte[Math.max(bufferSize, 1)];
		int byteRead = -1;
		
		try 
		{
			int byteCount = 0;
			while(-1 != ( byteRead = in.read(buffer) ) ) {
				out.write(buffer, 0, byteRead);
				byteCount += byteRead;
			}
			out.flush();
			
			return byteCount;
		} 
		catch (IOException ex) {
			throw new FileException(FileMessage.msgByIOException(ex), ex);
		} catch (Exception ex) {
			throw new FileException(ex);
		}
	}
	
	private StreamUtil() { super(); }
}