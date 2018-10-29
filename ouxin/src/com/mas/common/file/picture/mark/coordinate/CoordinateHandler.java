/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate;

import com.mas.common.file.picture.mark.Position;
import com.mas.common.file.picture.mark.coordinate.calculate.Calculate;

/**
 * 水印开始位置坐标处理器. </br></br>
 *
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class CoordinateHandler 
{
	/**
	 * @return 坐标计算器.
	 */
	public static final Calculate handle(Position position)
	{
		return
				SingletonHolder.HANDLER.getCalculate(position);
	}
	
	private static final class SingletonHolder 
	{
		private static final CoordinateCalculate HANDLER = 
				new RightDownCoordinate(
					new LeftCoordinate(
							new LeftDownCoordinate(
									new LeftUpCoordinate(
											new MiddlerCoordinate(
													new RightCoordinate(
															new DownCoordinate(
																	new RightUpCoordinate(
																			new UpCoordinate(null)
																			)
																	)
															)
													)
											)
									)
							)
					);
	}
	
	private CoordinateHandler() { super(); }
}