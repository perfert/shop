/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate.calculate;

/**
 * 左下角的坐标算法. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public final class LeftDownCalculate extends Calculate
{
	@Override
	public int coordinateX(int max, int mark, int offset) 
	{
		return offset;
	}

	@Override
	public int coordinateY(int max, int mark, int offset) 
	{
		return difference(max, mark, offset);
	}
	
	public LeftDownCalculate() { super(); }
}