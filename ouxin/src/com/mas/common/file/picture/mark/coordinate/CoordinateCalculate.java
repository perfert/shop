/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate;

import com.mas.common.file.picture.mark.Position;
import com.mas.common.file.picture.mark.coordinate.calculate.Calculate;

/**
 * 坐标计算器. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
abstract class CoordinateCalculate 
{	
	protected final CoordinateCalculate calculate;
	
	CoordinateCalculate(CoordinateCalculate calculate) 
	{
		this.calculate = calculate;
	}
	
	/**
	 * 获取坐标算法.
	 * 
	 * @param position 水印放置于图片的位置.
	 * 
	 * @return 坐标算法 or null
	 */
	abstract Calculate getCalculate(Position position);
	
	/**
	 * 管道.
	 * 
	 * @param position 水印放置于图片的位置.
	 * 
	 * @return 坐标算法 or null
	 */
	protected Calculate pipeline(Position position)
	{
		return
				null != this.calculate ? this.calculate.getCalculate(position)
						: null;
	}
}