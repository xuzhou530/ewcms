/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.web.context.EwcmsContextHolder;

/**
 * 首页Action
 *
 * @author 周冬初
 */
public class HomeAction extends EwcmsBaseAction {
    
    private Integer siteId;
    private String siteName;
    private String realName;
    private boolean siteExist = true;
    
	@Autowired
	private SiteFac siteFac;
	
	@Autowired
	private SecurityFacable securityFac;

	
	public String execute() {
	    
	    realName = securityFac.getCurrentUserInfo().getName();
	    
	    //TODO 得到用户所属组织，通过组织得到站点。
	    
		Site site = getSite(siteId);
		EwcmsContextHolder.getContext().setSite(site);
		
		setSiteName(site.getSiteName());
		
		return SUCCESS;
	}
	
	public String getRealName(){
	    return realName;
	}
	
	public boolean hasSite(){
	    return siteExist;
	}
	
	private Site getSite(Integer id) {
	    if(id != null){
	        Site site =  siteFac.getSite(siteId);
	        if(site == null){
	            addActionError("站点不存在");
	            siteExist = false;
	            return new Site();    
	        }
	        return site;
	    }else{
	        List<Site> list= siteFac.getSiteListByOrgans(new Integer[]{}, true);
	        if(list == null || list.isEmpty()){
	            siteExist = false;
	            return new Site();
	        }
	       return list.get(0);
	    }
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
