/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate;

import com.mas.common.file.picture.mark.Position;
import com.mas.common.file.picture.mark.coordinate.calculate.Calculate;
import com.mas.common.file.picture.mark.coordinate.calculate.RightDownCalculate;

/**
 * 右下角的坐标算法. </br></br>
 *
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
class RightDownCoordinate extends CoordinateCalculate
{
	@Override
	Calculate getCalculate(Position position) 
	{
		return
				Position.RIGHTDOWN == position ? SingletonCalculate.CALCULATE
						: pipeline(position);
	}
	
	RightDownCoordinate(CoordinateCalculate calculate) 
	{
		super(calculate);
	}
	
	private static final class SingletonCalculate { private static final Calculate CALCULATE = new RightDownCalculate(); }
}