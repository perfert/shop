/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate.calculate;

/**
 * 抽象水印计算基类. </br></br>
 * 
 * 描述: 计算图片的水印 X/Y 坐标起始位置的抽象类.
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public abstract class Calculate 
{	
	/**
	 * X 坐标起始值.
	 * 
	 * @param max X 坐标最大值.
	 * @param mark 水印宽.
	 * @param offset X 偏移值 (PX), 正数以结果坐标点为起点往 X 坐标最大值偏移; 负数以 X 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return int
	 */
	public abstract int coordinateX(int max, int mark, int offset);
	
	/**
	 * Y 坐标起始值.
	 * 
	 * @param max Y 坐标最大值.
	 * @param mark 水印高.
	 * @param offset Y 偏移量值 (PX), 正数以结果坐标点为起点往 Y 坐标最大值偏移; 负数以 Y 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return int
	 */
	public abstract int coordinateY(int max, int mark, int offset);
	
	/**
	 * 左右/上下 居中坐标.</br>
	 * ( max - mark ) / 2 + offset.
	 * 
	 * @param max X/Y 坐标最大值.
	 * @param mark 水印 长/高 值
	 * @param offset 偏移值 (PX), 正数以结果坐标点为起点往 X/Y 坐标最大值偏移; 负数以 X/Y 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return int
	 */
	protected final int middle(int max, int mark, int offset)
	{
		max = Math.max(0, max);
		mark = Math.max(0, mark);
		
		return
				max > mark ? ( max - mark ) / 2 + offset
						: offset;
	}
	
	/**
	 * 差值坐标.</br>
	 * max - mark + offset.
	 * 
	 * @param max X/Y 坐标最大值.
	 * @param mark 水印 长/高 值
	 * @param offset 偏移值 (PX), 正数以结果坐标点为起点往 X/Y 坐标最大值偏移; 负数以 X/Y 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return int
	 */
	protected int difference(int max, int mark, int offset)
	{
		max = Math.max(0, max);
		mark = Math.max(0, mark);
		
		return
				max > mark ? max - mark + offset
						: offset;
	}
	
	public Calculate() { super(); }
}