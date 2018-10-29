/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.tree.domain.vo;

/**
 * 移动节点的终点相对于参照节点的位置.<br><br>
 * 
 * LEFT 参照节点的左邻.<br>
 * RIGHT 参照节点的右邻.<br>
 * LEFTDOWN 参照节点的第一层子节点的最左邻, 此时参照节点被设置成为移动的节点的父节点.<br>
 * RIGHTDOWN 参照节点的第一层子节点的最右邻, 此时参照节点被设置成为移动的节点的父节点.<br>
 * 
 * @version 1.0
 * 
 * @author MAS
 */
public enum TreeNodePosition
{
	/** 左邻 */
	LEFT,
	/** 右邻 */
	RIGHT,
	/** 第一层子节点 最左邻 */
	LEFTDOWN,
	/** 第一层子节点 最右邻 */
	RIGHTDOWN
}
