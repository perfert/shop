/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.file.util.FileUtil;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.core.service.ServiceException;
import com.mas.security.domain.vo.Principal;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.vo.MemberVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.service.MemberService;
import com.mas.user.service.wallet.WealthService;
import com.mas.user.service.wallet.WealthTypeService;
import com.mas.web.job.ReadFile;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.member.MemberCtrDto;
import com.mas.web.util.ServletUtil;

/**
 * 会员
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping(MemberCtr.URI_PREFIX)
public class MemberCtr extends ManageCtr<MemberVo, MemberCtrDto>
{
	public static final String URI_PREFIX = "manage/chat/member";;

	/**
	 * 根据页码, 页行, 分页检索数据.
	 * 
	 * @param pageNo 页码.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@RequestMapping( "list/{pageNo}")
	public String page(@PathVariable int pageNo, MemberCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@RequestMapping( "inser")
    public String inser(MemberCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
    {
        try {
            Page page = new Page(1,100);
            service.queryPage(page, null);
            while(null != page.getResult() && page.getResult().size() > 0){
                List<MemberVo> vo = (List<MemberVo>) page.getResult();
                for(Member m : vo){
                    System.out.println(m.getId());
                    Wealth wealth = wealthService.getIfNotExistCreate(m.getId().toString(), "1", false);
                    /*if(null  == wealth){
                        wealth = new Wealth();
                        wealth.setCash(new BigDecimal(0));
                        wealth.setFreeze(new BigDecimal(0));
                        wealth.setWealthType("2");
                        wealth.setMemberId(m.getId());
                        Object res = wealthService.persistence(wealth);
                        if (VerifyUtil.isBlank(res) || 0 >= Integer.valueOf(res.toString()))
                            throw new ServiceException("register.error");
                        System.out.println(wealth.getId() + "===wealth===");
                    }else{
                        System.out.println(wealth.getId() + "===wealth===");
                    }*/
                }
                int nextPage = page.getPageNo() + 1;
                page = new Page(nextPage,100);
                service.queryPage(page, null);
                System.out.println(nextPage + "====nextPage========");
            }
            
           /* List<String> list = getAllFile("D:/a/down", false);//C:/Users/Administrator.WIN10-611251539/Desktop/file/upload
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i);
                if(fileDao.exist(name)){
                    fileDao.deleteByName(list.get(i));
                }else{
                    MFile file = new MFile();
                    file.setState(1);
                    file.setInsert(0);
                    file.setName(list.get(i));
                    fileDao.persistence(file);
                }
                
                MFile file = new MFile();
                file.setState(1);
                file.setInsert(1);
                file.setName(list.get(i));
                fileDao.persistence(file);
            }
            System.out.println("=====================" + list.size());*/
        } catch (Exception ex) {
            handlerException( ex, log(), model );
        }
        return VIEW_LIST( dto, model, params );
    }
	
	@RequestMapping( "down")
    public String down(MemberCtrDto dto, HttpServletRequest request,HttpServletResponse resp, ModelMap model, @PathVariable Map<String, Object> params)
    {
        try {
            List<String> list = fileDao.getAll();
            for (int i = 0; i < list.size(); i++) {
                String path = ServletUtil.realPath() + "upload/" + list.get(i);
                System.out.println(path);
                
                request.setCharacterEncoding("UTF-8");
                String name = request.getParameter("name");//获取要下载的文件名
                //第一步：设置响应类型
                resp.setContentType("application/force-download");//应用程序强制下载
                //第二读取文件
                InputStream in = new FileInputStream(path);
                //设置响应头，对文件进行url编码
                name = URLEncoder.encode(name, "UTF-8");
                resp.setHeader("Content-Disposition", "attachment;filename="+name);   
                resp.setContentLength(in.available());
                
                //第三步：老套路，开始copy
                OutputStream out = resp.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while((len = in.read(b))!=-1){
                  out.write(b, 0, len);
                }
                out.flush();
                out.close();
                in.close();
            }
            
            
        } catch (Exception ex) {
            handlerException( ex, log(), model );
        }
        return VIEW_LIST( dto, model, params );
    }
	
	@Autowired private FileDao fileDao;//7573
    
	
	/*@RequestMapping(value = "recharge", method = RequestMethod.POST)
    public void recharge(MemberCtrDto dto, HttpServletRequest request, HttpServletResponse response, @PathVariable
    Map<String, Object> params) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        try {
            Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
            wealthService.recharge(principal.getId(),dto.getId(), dto.getCash());
            result.put("statusCode", 200);
            result.put("msg", "充值成功！");
        } catch (Exception ex) {
            if (!(ex instanceof ServiceException))
                log.error(ex.getMessage());
            result.put("statusCode", 0);
            result.put("msg", ex instanceof ServiceException ? ex.getMessage() : "充值失败！");
        }
        JsonUtil.renderJson(response, result);
    }
	*/
	
	/**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            list.add(file.getName());
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    
	public static void main(String[] args) {
	    MemberCtr ctr  = new MemberCtr();
	    //所有的
	    List<String> list = ctr.getAllFile("D:/down", true);
        for (int i = 0; i < list.size(); i++) {
            String fileName = list.get(i);
            System.out.println(fileName);
            if(fileName.contains("msg")){
                try {
                    ReadFile.deletefile(fileName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	//充值
    @RequestMapping( value = "recharge/{id}", method = RequestMethod.GET )
    public String recharge(@PathVariable String id,MemberCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params){
        try {
            if ( VerifyUtil.isNotBlank(id) ) {
                Member member = service.get(id);
                
                List<WealthType> list = wealthTypeService.getAll();
                
                MemberVo vo = new MemberVo();
                vo.setId(member.getId());
                vo.setAccount(member.getAccount());
                vo.setTypeList(list);
                dto.setBean(vo);
            }
        } catch (Exception ex) {
            handlerException( ex, log(), model );
        }
        bind( dto, model, params );
        return VIEW_PREFIX( params ) + SPLIT + "recharge";
    }
    
    
    /**
     * 充值.
     * 
     * @param dto Controller DTO Context.
     * @param model 模型参数.
     * @param params {@link PathVariable} 参数集
     * 
     * @return view uri
     */
    @RequestMapping( value = "recharge", method = RequestMethod.POST )
    public String recharge(MemberCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
    {
        try {
            /*Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
            wealthService.recharge(principal.getId(),dto.getId(),dto.getTypeId(), dto.getCash());*/
            throw new ServiceException("暂款开放");
        } catch (Exception ex) {
            handlerException( ex, log(), model );
            return REDIRECT_VIEW_CREATE( dto, model, params );
        }
        //bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
        //return REDIRECT_VIEW_LIST( dto, model, params );
    }
	
	//重置密码
	@RequestMapping( value = "pwd/{id}", method = RequestMethod.GET )
	public String setPwd(@PathVariable String id,MemberCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params){
	    try {
            if ( VerifyUtil.isNotBlank(id) ) {
                Member member = service.get(id);
                MemberVo vo = new MemberVo();
                vo.setId(member.getId());
                vo.setAccount(member.getAccount());
                dto.setBean(vo);
            }
        } catch (Exception ex) {
            handlerException( ex, log(), model );
        }
	    bind( dto, model, params );
        return VIEW_PREFIX( params ) + SPLIT + VIEW_INPUT_BASE;
    }
    
	
	/**
     * 添加数据.
     * 
     * @param dto Controller DTO Context.
     * @param model 模型参数.
     * @param params {@link PathVariable} 参数集
     * 
     * @return view uri
     */
    @RequestMapping( value = "pwd", method = RequestMethod.POST )
    public String setPwd(MemberCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
    {
        try {
            service.updatePassword(dto.getBean().getId(), dto.getBean().getPassword());
        } catch (Exception ex) {
            handlerException( ex, log(), model );
            return REDIRECT_VIEW_CREATE( dto, model, params );
        }
        bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
        return REDIRECT_VIEW_LIST( dto, model, params );
    }
	
	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return URI_PREFIX;
	}

	@Override
	protected BaseService<MemberVo> service()
	{
		return null;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired private MemberService service;
    @Autowired
    private WealthService wealthService;
    @Autowired
    private WealthTypeService wealthTypeService;
	
	private static final Logger log = LogManager.getLogger( MemberCtr.class );
}