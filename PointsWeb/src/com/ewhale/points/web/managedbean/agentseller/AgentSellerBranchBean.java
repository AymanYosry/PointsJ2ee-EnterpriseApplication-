package com.ewhale.points.web.managedbean.agentseller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.BranchBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.LookUpServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerBranchBean extends BranchBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> statesList;

	public List<Map<String, Object>> getStatesList()
	{
		return statesList;
	}

	protected void handlePostConstruct()
	{
		dialogeOptions.put("width", 740);
		dialogeOptions.put("height", 440);

		setCanAdd(false);
		setCanUpdate(false);
		setCountryId(((Number) FacesUtil.getLoginData().get(EntityConstants.Login.countryId)).shortValue());
		loadStatesList();
		getAgentBranches();
	}

	public void loadStatesList()
	{
		LookUpServiceClient lookUpServiceClient = ServiceClientUtil.getLookUpServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.State.countryId, getCountryId());
		statesList = lookUpServiceClient.getAllStates(data);
	}

	public void getAgentBranches()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Branch.agentId, FacesUtil.getLoginAgentId());
		data.put(EntityConstants.Branch.stateId, getStateId());
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> agentBranchesList = agentSellerServiceClient.agentBranchesList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Branch.branchName, null },
					{ EntityConstants.Branch.tel, null },
					{ EntityConstants.Branch.state, EntityConstants.State.stateName },
					{ EntityConstants.Branch.address, null } };

		populateTable(agentBranchesList, columnKeys);
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> branchDetails = agentSellerServiceClient.viewBranchDetails(data.get(EntityConstants.Branch.branchId) + "");
		fillDetailsData(branchDetails);
	}

	@Override
	protected String getDetailsPageName()
	{
		return "branch_details.xhtml";
	}

	public void viewBranchDetails()
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> branchDetails = agentSellerServiceClient
				.viewBranchDetails(FacesUtil.getLoginData().get(EntityConstants.Login.branchId) + "");
		fillDetailsData(branchDetails);
	}
}
