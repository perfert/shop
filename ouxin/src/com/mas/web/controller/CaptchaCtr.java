/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.web.captcha.Captcha;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
public class CaptchaCtr
{
    
    public static void main(String[] args) {
        Pattern exp=Pattern.compile("^www.+(?:/|(?:/[\\w\\.\\-]+)*(?:/[\\w\\.\\-]+\\.do))?$", Pattern.CASE_INSENSITIVE);
        System.out.println(exp.matcher("www.").matches());//true
        
        String str = "http://www.xxx.com/1噶为欧冠奇偶位i二姐http://www.xxx.com/1/2乌黑欧冠我几乎http://www.xxx.com/1/2/3的稿费欧文和岗位i偶尔给http://www.xxx.com/1.jpg";
        str = str.replaceAll("(?is)(http://[/\\.\\w]+\\.jpg)","<img src='$1'/>");
        str = str.replaceAll("(?is)(?<!')(http://[/\\.\\w]+)","<a href='$1'>$1</a>");
        System.out.println(str);
        
        String s = "<movie title=\"电影1\" src=\"https://www.qq.com:808/asdsadsd+_#$\"/> <movie title=\"电影2\" src=\"http://www.qq.com:808/asdsadsd+_#$\"/>";
        String mode = "<movie\\s*title=([^>]*)src=(?=\"?http|https)([^>]*)/>";
        Pattern p = Pattern.compile(mode);
        Matcher m = p.matcher(s);
        HashMap<String, String> map = new HashMap<String, String>();
        while (m.find()) {
        System.out.println("find...");
        String title = m.group(1);
        String url = m.group(2);
        System.out.println("title:" + title + ",src:" + url);
        map.put(title, url);
        }
    }
    
    
	@RequestMapping("/capatch")
	public void captcha(HttpServletRequest reqeust, HttpServletResponse response)
	{
		// 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        Captcha instance = new Captcha();
        HttpSession session = reqeust.getSession(true);
        session.setMaxInactiveInterval(60 * 1000);
        session.setAttribute( Captcha.CAPTCHA, instance.getCode() );
        try {
			instance.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
