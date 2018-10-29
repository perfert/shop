/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mas.common.verify.VerifyUtil;

/**
 * Security resource tree structure.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityResourceTree implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private SecurityResourceTree parent;
	private SecurityResource node;
	private List<SecurityResourceTree> children = new ArrayList<SecurityResourceTree>();
	private boolean menuCheck = Boolean.FALSE;
	private boolean checked = Boolean.FALSE;
	
	private boolean hasChildMenu = Boolean.FALSE;

	public SecurityResourceTree getParent()
	{
		return parent;
	}

	public SecurityResourceTree setParent( SecurityResourceTree parent )
	{
		this.parent = parent;
		return this;
	}

	public SecurityResource getNode()
	{
		return node;
	}

	public void setNode( SecurityResource node )
	{
		this.node = node;
	}

	public List<SecurityResourceTree> getChildren()
	{
		return children;
	}

	public void setChildren( List<SecurityResourceTree> children )
	{
		this.children = children;
	}

	public void addChildren( SecurityResourceTree children )
	{
		this.children.add( children );
	}

	public boolean getMenuCheck()
	{
		return menuCheck;
	}

	public void setMenuCheck( boolean menuCheck )
	{
		if( menuCheck )
		{
			if( ! this.menuCheck )
			{
				if( null != this.getParent() ) this.getParent().setMenuCheck( menuCheck );
			}
		} else {			
			if( VerifyUtil.isNotEmpty( this.getChildren() ) )
			{
				for( SecurityResourceTree child : this.getChildren() )
				{
					child.setMenuCheck( menuCheck );
				}
			}
		}
		this.menuCheck = menuCheck;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked( boolean checked )
	{
		this.checked = checked;
	}

	public boolean getHasChildMenu()
	{
		return hasChildMenu;
	}

	public void setIsMenu( boolean isMenu )
	{
		if( ! this.getHasChildMenu() )
		{
			this.hasChildMenu = isMenu;
		}
		if( isMenu && null != getParent() ) 	
		{
			getParent().setIsMenu( isMenu );
		}
	}
}