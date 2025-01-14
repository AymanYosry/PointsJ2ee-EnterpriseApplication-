package com.ewhale.points.web.managedbean.user;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.ProfileBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.user.proxy.UserServiceClient;

@SessionScoped
@ManagedBean
public class UserProfileBean extends ProfileBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserServiceClient userServiceClient = null;

	public UserProfileBean()
	{
	}

	public String register()
	{
		String page = null;
		try
		{
			userServiceClient = ServiceClientUtil.getUserServiceClient(ServiceClientUtil.WEB);
			Map<String, Object> registrationData = fillInsertDataMap();

			userServiceClient.register(registrationData);
			page = "confirm_request.jsf";
		}
		catch (Exception e)
		{
			page = null;//"error.jsf";
			//e.printStackTrace();
		}
		return page;
	}

	public String updateUserProfile()
	{
		String page = null;
		try
		{
			Map<String, Object> data = fillUpdateDataMap();
			data.put(EntityConstants.Profile.profileId, getProfileId());
			UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
			userServiceClient.updateUserProfile(data);
			//IMP_Ayman set Success Message in faces context 
			page = "pages/user/update_profile.jsf";
		}
		catch (Exception e)
		{
			page = null;//"error.jsf";
			//e.printStackTrace();
		}
		return page;
	}
}
