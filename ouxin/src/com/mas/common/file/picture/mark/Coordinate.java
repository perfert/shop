/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark;

import com.mas.common.file.picture.mark.coordinate.CoordinateHandler;
import com.mas.common.file.picture.mark.coordinate.calculate.Calculate;

/**
 * 水印开始位置坐标. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public class Coordinate 
{
	private final Position position;
	private Integer xCoordinate;
	private Integer yCoordinate;
	private Calculate calculate;
		
	/**
	 * X 开始坐标值.
	 * 
	 * @param pictureWidth 图片宽度.
	 * @param markImageWidth 水印宽度.
	 * @param offset X 偏移值 (PX), 正数以结果坐标点为起点往 X 坐标最大值偏移; 负数以 X 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return X 开始坐标值 or 0.
	 */
	public int xCoordinate(int pictureWidth, int markImageWidth, int offset)
	{
		if ( null == this.xCoordinate ) {
			this.xCoordinate = null != calculate() ? calculate().coordinateX(pictureWidth, markImageWidth, offset)
					: 0;
		}
		return this.xCoordinate;
	}
	
	/**
	 * Y 开始坐标值.
	 * 
	 * @param pictureWidth 图片宽度.
	 * @param markImageWidth 水印宽度.
	 * @param offset Y 偏移量值 (PX), 正数以结果坐标点为起点往 Y 坐标最大值偏移; 负数以 Y 坐标最大值为起点往 0 点方向偏移.
	 * 
	 * @return Y 开始坐标值 or 0.
	 */
	public int yCoordinate(int pictureHeight, int markImageHeight, int offset)
	{
		if ( null == this.yCoordinate ) {
				this.yCoordinate = null != calculate() ? calculate().coordinateY(pictureHeight, markImageHeight, offset)
						: 0;
		}
		return this.yCoordinate;
	}
	
	/**
	 * @return 坐标计算器.
	 */
	private Calculate calculate()
	{
		return
				null == this.calculate ? CoordinateHandler.handle(this.position)
						: this.calculate;
	}
	
	/**
	 * 水印开始位置坐标.
	 * 
	 * @param position 水印放置于图片上的位置.
	 */
	public Coordinate(Position position)
	{
		this.position = position;
	}
}