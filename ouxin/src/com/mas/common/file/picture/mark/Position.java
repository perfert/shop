/*
 *Copyright (c) 2012, 2014, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file.picture.mark;

/**
 * 水印放置位置. </br></br>
 *
 * 描述: 水印放置在图片上的相对位置.
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public enum Position 
{
	LEFTUP("左上角"),				// 左上角
	LEFTDOWN("左下角"),		// 左下角.
	LEFT("左边上下居中"),				// 左边上下居中.
	RIGHTUP("右上角"),				// 右上角.
	RIGHTDOWN("右下角"),		// 右下角.
	RIGHT("右边上下居中"),			// 右边上下居中.
	UP("上边左右居中"),							// 上边左右居中.
	DOWN("下边左右居中"),					// 下边左右居中.
	MIDDLE("上下左右居中")				// 上下左右居中.
	;
	private String label;
	private Position() { this.label = name(); }
	private Position(String label) { this.label = label; }
	public String label() { return this.label; }
}