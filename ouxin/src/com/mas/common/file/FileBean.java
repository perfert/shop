/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file;

import java.util.ArrayList;
import java.util.List;

import com.mas.common.file.util.FileUtil;

/**
 * 上传后的文件对象.
 * 
 * @version 1.0
 * 
 * @author MAS
 */
public class FileBean 
{
	private final String fileName;
	private final String fileUrl;
	private final String fileUri;
	private final String fileContentType;
	private final String suffix;
	private final long size;
	private final List<String> compressionUris = new ArrayList<String>();
	private final List<String> markUris = new ArrayList<String>();
	
	public FileBean(String fileName, String fileUrl, String fileUri, String fileContentType, String suffix, long size)
	{
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileUri = fileUri;
		this.fileContentType = fileContentType;
		this.suffix = suffix;
		this.size = size;
	}
	
	/**
	 * 删除对象本身所有文件.
	 */
	public void removeAll()
	{
		FileUtil.remove(fileUri);
		remove(compressionUris);
		remove(markUris);
	}
	
	public FileBean addCompressionUris(String uri)
	{
		this.compressionUris.add(uri);
		return this;
	}
	
	public FileBean addMarkUris(String uri)
	{
		this.markUris.add(uri);
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getFileUri() {
		return fileUri;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public String getSuffix() {
		return suffix;
	}

	public long getSize() {
		return size;
	}

	public List<String> getCompressionUris() {
		return compressionUris;
	}

	public List<String> getMarkUris() {
		return markUris;
	}
	
	private void remove(List<String> uris)
	{
		for ( String uri : uris )
			FileUtil.remove(uri);
	}
}