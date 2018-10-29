package com.mas.web.manage.controller.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.system.domain.entity.Country;
import com.mas.system.service.impl.CountryService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.system.CountryCtrDto;

/**
 * 
 * CountryCtr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping( CountryCtr.URI_PREFIX )
public class CountryCtr extends ManageCtr<Country, CountryCtrDto>
{
	public static final String URI_PREFIX = "manage/system/country";

	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return URI_PREFIX;
	}
	
	
	
	@Override
	@RequestMapping( value = "create", method = RequestMethod.POST )
    public String create(CountryCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
    {
        try {
                BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E://arrays.txt"))));
                String line = null;  
                while((line = bufr.readLine()) != null){  
                    if(StringUtils.isNotEmpty(line)){
                        String contry = line.substring(line.indexOf(">") + 1,line.lastIndexOf("<"));
                        String[] country = contry.split("\\*");
                        String countryName = country[0].trim();
                        String countryNumber = country[1].trim();
                            
                        Country c = new Country();
                        c.setName(countryName);
                        c.setCode(countryNumber);
                        c.setState(1);
                        c.setId( null );
                        //service().persistence( c );
                    }
                }  
                
                BufferedReader bufr2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("E://arrays2.txt"))));
                Map<String,Country> map = new HashMap<String,Country>();
                while((line = bufr2.readLine()) != null){  
                    if(StringUtils.isNotEmpty(line)){
                        String contry = line.substring(line.indexOf(">") + 1,line.lastIndexOf("<"));
                        String[] country = contry.split("\\*");
                        String countryName = country[0].trim();
                        String countryNumber = country[1].trim();
                            
                        Country c = new Country();
                        c.setCode(countryNumber);
                        c.setState(1);
                        c.setId( null );
                        map.put(countryNumber, c);
                    }
                }  
                for(Country cp:service.queryAll()){//map.get(cp.getCode()).getCnName()
                    //cp.setCnName(new String(map.get(cp.getCode()).getCnName().getBytes("GBK"), "UTF-8"));
                    //System.out.println(cp.getCnName());
                    if(cp.getCode().contains("+")){
                        cp.setCode(cp.getCode().substring(1));
                        service.persistence(cp);
                    }
                }
                bufr.close();  
        } catch (Exception ex) {
            handlerException( ex, log(), model );
            return REDIRECT_VIEW_CREATE( dto, model, params );
        }
        bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
        return REDIRECT_VIEW_LIST( dto, model, params );
    }
    
	@Override
	protected BaseService<Country> service()
	{
		return service;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired private CountryService service;
	
	private static final Logger log = LogManager.getLogger( CountryCtr.class );
}
