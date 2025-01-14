/**
 * 
 */
package com.ewhale.points.web.managedbean.main;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.ws.agent.proxy.AgentAdminServiceClient;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

/**
 * @author Ayman Yosry
 */
@SessionScoped
@ManagedBean
public class RoleSwitcherBean implements Serializable
{

	protected Logger LOG = Logger.getLogger(RoleSwitcherBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int currentRoleId = EntityConstants.Role.Fixed.userRole.ID;

	private String loginAgentName = null;

	/**
	 * 
	 */
	public RoleSwitcherBean()
	{
	}

	public String activateAsUser()
	{
		currentRoleId = EntityConstants.Role.Fixed.userRole.ID;
		return "/pages/user/index.html";
	}

	public String activateAsAgent()
	{
		LOG.debug("activate as agent .. ");
		int roleId = ((Number) FacesUtil.getLoginData().get(EntityConstants.Login.roleId)).intValue();
		currentRoleId = roleId;
		if (roleId == EntityConstants.Role.Fixed.agentAdminRole.ID)
		{

			AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
			loginAgentName = (String) ((Map<String, Object>) agentAdminServiceClient
					.agentDetails("" + FacesUtil.getLoginData().get(EntityConstants.Login.agentId))).get(EntityConstants.Agent.tradeMark);
			return "/pages/agent/admin/index.xhtml";
		}
		else if (roleId == EntityConstants.Role.Fixed.agentSellerRole.ID)
		{
			AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
			loginAgentName = (String) ((Map<String, Object>) agentSellerServiceClient
					.viewAgentDetails("" + FacesUtil.getLoginData().get(EntityConstants.Login.agentId))).get(EntityConstants.Agent.tradeMark);
			return "/pages/agent/seller/index.xhtml";
		}
		else
			return "";
	}

	public String activateAsSystemEmployee()
	{	int roleId = ((Number) FacesUtil.getLoginData().get(EntityConstants.Login.roleId)).intValue();
		currentRoleId = roleId;

		LOG.debug("activate as System Employee .. currentRoleId ="+currentRoleId +"role id="+ roleId);
		if (roleId == EntityConstants.Role.Fixed.systemAdminRole.ID)
			return "/pages/system/admin/index.xhtml";
		else if (roleId == EntityConstants.Role.Fixed.systemSalesRole.ID)
			return "/pages/system/sales/index.xhtml";
		else
			return "";
	}

	/**
	 * @return the currentRoleId
	 */
	public int getCurrentRoleId()
	{
		return currentRoleId;
	}

	/**
	 * @param currentRoleId the currentRoleId to set
	 */
	public void setCurrentRoleId(int currentRoleId)
	{
		this.currentRoleId = currentRoleId;
	}

	public String getLoginName()
	{
		return FacesUtil.getLoginData().get(EntityConstants.Login.fullName).toString();
	}

	public String getLoginAgentName()
	{
		return loginAgentName;
	}
}
