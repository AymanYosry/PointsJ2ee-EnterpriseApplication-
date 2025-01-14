package com.ewhale.points.web.managedbean.agentseller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AgentBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerAgentBean extends AgentBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AgentSellerAgentBean()
	{

	}

	@Override
	protected void handlePostConstruct()
	{
		viewAgentDetails();
	}
	public void viewAgentDetails()
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> agentDetails = agentSellerServiceClient
				.viewAgentDetails(FacesUtil.getLoginData().get(EntityConstants.Login.agentId) + "");
		fillDetailsData(agentDetails);
	}

}
