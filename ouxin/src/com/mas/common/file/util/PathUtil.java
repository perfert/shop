package com.mas.common.file.util;

import java.io.File;

public class PathUtil
{
	// protected static Configuration config;
	//
	// static {
	// try {
	// config = new PropertiesConfiguration("com/macauslot/cms/other/upload/path.properties");
	// } catch (ConfigurationException e) {
	// e.printStackTrace();
	// }
	// }
	// public static String getPath(String key, String... defaultPath){
	// if(config.containsKey(key)){
	// return config.getString(key);
	// }else if(null != defaultPath && 0 < defaultPath.length ){
	// return defaultPath[0];
	// }
	// return "";
	// }
	/**
	 * 获取WEB-INF/classes/
	 * 
	 * @return
	 */
	public static String getTomcatClassesPath()
	{
		String result = Thread.currentThread().getContextClassLoader().getResource( "" ).toString().split( "file:/" )[ 1 ];
		return
				result.startsWith( File.separator ) ? result 
						: File.separator + result;
	}

	/**
	 * 获取WebRoot
	 * 
	 * @return
	 */
	public static String getTomcatWebRootPath()
	{
		String result = getTomcatClassesPath().split( "WEB-INF/classes/" )[ 0 ];
		return
				result.startsWith( File.separator ) ? result 
						: File.separator + result;
	}

	/**
	 * 获取WEB-INF
	 * 
	 * @return
	 */
	public static String getTomcatWEBINFPath()
	{
		String result = getTomcatClassesPath().split( "classes/" )[ 0 ];
		return
				result.startsWith( File.separator ) ? result 
						: File.separator + result;
	}

	/**
	 * 获取项目名
	 * 
	 * @return
	 */
	public static String getProjectName()
	{
		String[] str = System.getProperty( "user.dir" ).split( "\\\\" );
		return str[ str.length - 1 ];
	}

	public static String removeIfStartWithSlash(String uri) {
        if ((uri.startsWith("/")) || (uri.startsWith("\\"))) {
            return removeIfStartWithSlash(uri.substring(1));
        }
        return uri;
    }

    public static String removeIfEndWithSlash(String uri) {
        if ((uri.startsWith("/")) || (uri.startsWith("\\"))) {
            return removeIfStartWithSlash(uri.substring(1));
        }
        return uri;
    }
    
	/**
	 * 处理系统路径
	 * 
	 */
	public static String replace( String path )
	{
		// windows下
		if( "\\".equals( File.separator ) )
		{
			path = path.replace( "/", "\\" );
		}
		// linux下
		if( "/".equals( File.separator ) )
		{
			path = path.replace( "\\", "/" );
		}
		return path;
	}
}
