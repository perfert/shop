/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.mas.common.verify.VerifyUtil;

/**
 * Reflection auxiliary utility.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class ReflectionUtil
{
	/**
	 * Get the first one generic type of class.
	 * 
	 * @param clazz Source object class.
	 * 
	 * @return Generic type of class or Object type class.
	 */
	public static Class<?> getSuperClassGenericType( Class<?> clazz )
	{
		return getSuperClassGenericType( clazz, 0 );
	}

	/**
	 * Gets the type of the index generic class.
	 * 
	 * @param clazz Source object class.
	 * @param index the index.
	 * 
	 * @return Generic type of class or Object type class.
	 */
	public static Class<?> getSuperClassGenericType( Class<?> clazz, int index )
	{
		if( 0 > index )
		{
			index = 0;
		}
		Type genType = clazz.getGenericSuperclass();
		if( null == genType )
		{
			return Object.class;
		}
		if( Object.class == genType.getClass() )
		{
			return Object.class;
		}
		if( ! ( genType instanceof ParameterizedType ) )
		{
			return getSuperClassGenericType( clazz.getSuperclass(), index );
		}	
		Type[] types = ( ( ParameterizedType ) genType ).getActualTypeArguments();
		if( index >= types.length )
		{
			return Object.class;
		}
		if( ! ( types[ index ].getClass() instanceof Class ) )
		{
			return Object.class;
		}
		return ( Class<?> ) types[ index ];
	}

	/**
	 * Call getter method.
	 * 
	 * @param object The access object.
	 * @param fieldName The field name.
	 */
	public static Object invokeGetterMethod( Object object, String fieldName ) throws ReflectionException
	{
		return 
				invokeMethod( 
						object
						, getAccessibleMethod( object, "get" + StringUtils.capitalize( fieldName ) ) 
						);
	}

	/**
	 * Call setter method.
	 * 
	 * @param object The access object.
	 * @param fieldName The field name.
	 * @param value The value of setter.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static void invokeSetterMethod( Object object, String fieldName, Object value ) throws ReflectionException
	{
		invokeMethod( 
				object
				, getAccessibleMethod( 
						object
						, "set" + StringUtils.capitalize( fieldName ), VerifyUtil.isNotNull( value ) ? value.getClass() 
								: Object.class 
						)
				, value 
				);
	}

	/**
	 * Get the value of the object field, ignoring the access control.
	 * 
	 * @param object The access object.
	 * @param fieldName The field name.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static Object getFieldValue( Object object, String fieldName ) throws ReflectionException
	{
		try
		{
			return 
					getAccessibleField( object, fieldName )
						.get( object );
		}
		catch( IllegalArgumentException ex )
		{
			throw new ReflectionException( msgByIllegalArguments() );
		}
		catch( IllegalAccessException ex )
		{
			throw new ReflectionException( msgByNotDefaultInstance( object ) );
		}
		catch( ReflectionException ex )
		{
			throw ex;
		}
		catch( Exception ex )
		{
			throw new ReflectionException( ex );
		}
	}

	/**
	 * Set the value of the object field, ignoring the access control.
	 * 
	 * @param object The access object.
	 * @param fieldName The field name.
	 * @param value The value of setter.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static void setFieldValue( Object object, String fieldName, Object value ) throws ReflectionException
	{
		try
		{
			getAccessibleField( object, fieldName )
				.set( object, value );
		}
		catch( IllegalArgumentException ex )
		{
			throw new ReflectionException( msgByIllegalArguments() );
		}
		catch( IllegalAccessException ex )
		{
			throw new ReflectionException( msgByNotDefaultInstance( object ) );
		}
		catch( ReflectionException ex )
		{
			throw ex;
		}
		catch( Exception ex )
		{
			throw new ReflectionException( ex );
		}
	}

	/**
	 * Access object methods.
	 * 
	 * @param object The access object.
	 * @param method The method.
	 * @param parameterValues Parameters of the method.
	 * 
	 * @return The return value of method.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static Object invokeMethod( Object object, Method method, Object... parameterValues ) throws ReflectionException
	{
		try
		{
			return method.invoke( object, parameterValues );
		}
		catch( IllegalAccessException ex )
		{
			throw new ReflectionException( msgByNotDefaultInstance( object ), ex );
		}
		catch( IllegalArgumentException ex )
		{
			throw new ReflectionException( msgByIllegalArguments(), ex );
		}
		catch( InvocationTargetException ex )
		{
			throw new ReflectionException( MessageFormat.format( "Access {0}.{1} --- {2}", object, method.getName(), ex.getMessage() ) );
		}
		catch( Exception ex )
		{
			throw new ReflectionException( ex );
		}
	}

	/**
	 * Gets the object's methods.
	 * 
	 * @param object The access object.
	 * @param methodName The method name.
	 * @param parameterTypes Parameters of the method.
	 * 
	 * @return {@link Method}.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static Method getAccessibleMethod( final Object object, final String methodName, final Class<?>... parameterTypes ) throws ReflectionException
	{
		for( Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass() )
		{
			try
			{
				Method method = superClass.getDeclaredMethod( methodName, parameterTypes );
				if( !method.isAccessible() )
				{
					method.setAccessible( true ); // 忽略访问控制.
				}
				return method;
			}
			catch( NoSuchMethodException ex )
			{
				continue;
			}
			catch( SecurityException ex )
			{
				throw new ReflectionException( msgBySecurityViolation( object, methodName, ex ) );
			}
			catch( Exception ex )
			{
				throw new ReflectionException( ex );
			}
		}
		throw new ReflectionException( msgByNotSuchAny( object, methodName ) );
	}

	/**
	 * Gets the object's field.
	 * 
	 * @param object The access object.
	 * @param fieldName The field name.
	 * 
	 * @return {@link Field}.
	 * 
	 * @throws ReflectionException Maybe throws exception.
	 */
	public static Field getAccessibleField( final Object object, final String fieldName ) throws ReflectionException
	{
		for( Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass() )
		{
			try
			{
				Field field = superClass.getDeclaredField( fieldName );
				if( !field.isAccessible() )
				{
					field.setAccessible( true ); // 忽略访问控制.
				}
				return field;
			}
			catch( NoSuchFieldException ex )
			{
				continue;
			}
			catch( SecurityException ex )
			{
				throw new ReflectionException( msgBySecurityViolation( object, fieldName, ex ) );
			}
			catch( Exception ex )
			{
				throw new ReflectionException( ex );
			}
		}
		throw new ReflectionException( msgByNotSuchAny( object, fieldName ) );
	}

	private static String msgBySecurityViolation( Object object, String accessName, Throwable ex )
	{
		return MessageFormat.format( "Access control security violations at the object: {0}.{1} --- {2}", object, accessName, ex.getMessage() );
	}

	private static String msgByNotSuchAny( Object object, String accessName )
	{
		return MessageFormat.format( "Object {0} does not exist field or method: {1}.", object, accessName );
	}
	
	private static String msgByNotDefaultInstance(Object object)
	{
		return MessageFormat.format( "Object: {0} is no default instance.", object);
	}
	
	private static String msgByIllegalArguments()
	{
		return "Illegal arguments.";
	}

	private ReflectionUtil() { super(); }
}