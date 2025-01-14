package com.ewhale.points.web.managedbean.agentseller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PromotionBean;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerPromotionBean extends PromotionBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void userPromotionsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Promotion.agentId, FacesUtil.getLoginData().get(EntityConstants.Login.agentId));
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> promotionsList = agentSellerServiceClient.agentPromotionsList(data);
		populateTable(promotionsList, EntityConstants.Promotion.message, EntityConstants.Promotion.messageDetails);

	}

	public void viewPromotionDetails()
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> promotionDetails = agentSellerServiceClient.viewPromotionDetails(getPromotionId() + "");
		fillDetailsData(promotionDetails);
	}

}
