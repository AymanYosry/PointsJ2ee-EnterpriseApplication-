package com.ewhale.points.web.managedbean.agentseller;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerAgentRateBean extends AbsoluteBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long agentId;

	public AgentSellerAgentRateBean()
	{

	}

	public long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(long agentId)
	{
		this.agentId = agentId;
	}

	public void agentRateList()
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		List<Map<String,Object>> agentRateList = agentSellerServiceClient.agentRateList(agentId + "");
		System.err.println(agentRateList.size());
	}
}
