package com.ewhale.points.web.managedbean.agentseller;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.web.managedbean.main.ContractBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerContractBean extends ContractBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void viewAgentContractDetails()
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> contractDetails = agentSellerServiceClient.viewAgentContractDetails(getContractId() + "");
		fillDetailsData(contractDetails);
	}

}
