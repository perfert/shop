/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark.coordinate;

import com.mas.common.file.picture.mark.Position;
import com.mas.common.file.picture.mark.coordinate.calculate.Calculate;
import com.mas.common.file.picture.mark.coordinate.calculate.LeftDownCalculate;

/**
 * 左下角的坐标算法. </br></br>
 *
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
class LeftDownCoordinate extends CoordinateCalculate
{
	@Override
	Calculate getCalculate(Position position) 
	{
		return 
				Position.LEFTDOWN == position ? SingletonCalculate.CALCULATE
						: pipeline(position);
	}

	LeftDownCoordinate(CoordinateCalculate calculate) 
	{
		super(calculate);
	}
	
	private static final class SingletonCalculate { private static final Calculate CALCULATE = new LeftDownCalculate(); }
}	