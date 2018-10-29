package com.mas.web.manage.controller.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.file.FileBean;
import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.core.service.ServiceException;
import com.mas.user.domain.entity.app.Apk;
import com.mas.user.service.app.ApkService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.app.ApkCtrDto;
import com.mas.web.springmvc.util.UploadUtil;

/**
 * ApkCtr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping( ApkCtr.URI_PREFIX )
public class ApkCtr extends ManageCtr<Apk, ApkCtrDto>
{
	public static final String URI_PREFIX = "manage/app/apk";
	private static final String upladPath = "upload/apk/";
	 
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
	@Override
	public String page(@PathVariable int pageNo, ApkCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() ){
			    Apk query = new Apk();
				dto.setQuery( query );
			}
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	
	@Override
	public String edit(@PathVariable String id, ApkCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
		    Apk apk = service.getTheLatestApk();
	        if(null != apk)
	            dto.setNowVersion(apk.getVersion());
	        else
	            dto.setNowVersion(0d);
			if ( VerifyUtil.isNotBlank(id) ){
				dto.setBean(service.get(id));
			}
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_INPUT( dto, model, params );
	}
	
	public static void main(String[] args) {
	    Thread thread= new Thread(new Runnable() {  
            
            @Override  
            public void run() {  
                System.err.println("线程"+Thread.currentThread().getId()+" 打印信息");  
            }  
        });  
        thread.start();  
          
        try {  
            thread.join();  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        System.err.println("主线程打印信息");  
        
        int a = 1;
        int b = 2;
        System.out.println(a |= b);
        
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
	@Override
	public String create(ApkCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
			String uri = null;
			if ( request instanceof MultipartHttpServletRequest ) {
	            MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
	            MultipartFile multipartFile = multipart.getFile("file");
	            FileBean fileBean  =  0 < multipartFile.getSize() ? UploadUtil.upload(multipartFile, upladPath,Apk.DOWN_LOAD)
	                            : null;
	            uri = null != fileBean ? fileBean.getFileUri(): null;
	        } 
            if( VerifyUtil.isNotBlank( uri ) ) {
                dto.getBean().setFilePath(uri);
            }else
                throw new ServiceException("请上传apk");
			service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			ex.printStackTrace();
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String modify(ApkCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		Object id = pathValue(params, "id");
		try {
			if( null == id || VerifyUtil.isBlank( id.toString() ) )
			{
				throw new Exception("更新数据ID不能为空！");
			}
			//service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_EDIT( dto, model, params, id );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}

	@Override
	protected BaseService<Apk> service()
	{
		return service;
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return URI_PREFIX;
    }

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private ApkService service;
	
	private static final Logger log = LogManager.getLogger( ApkCtr.class );
}