package com.ewhale.points.web.managedbean.agentadmin;

import java.util.Base64;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AgentBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.agent.proxy.AgentAdminServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentAdminAgentBean extends AgentBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void handlePostConstruct()
	{
		logger = Logger.getLogger(AgentAdminAgentBean.class);
		loadAgentData();
	}

	public void updateAgent()
	{
		try
		{
			Map<String, Object> data = fillAgentDataMap();
			data.put(EntityConstants.Agent.agentId, FacesUtil.getLoginData().get(EntityConstants.Login.agentId));
			AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
			agentAdminServiceClient.updateAgent(data);
			setLogo(Base64.getEncoder().encodeToString(getUploadedLogo().getContents()));
		}
		catch (Throwable e)
		{
			FacesUtil.addErrorMessage("Error", e.getMessage());
		}
	}

	private void loadAgentData()
	{
		AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> agentData = agentAdminServiceClient.agentDetails(FacesUtil.getLoginAgentId() + "");
		fillDetailsData(agentData);
	}

}
