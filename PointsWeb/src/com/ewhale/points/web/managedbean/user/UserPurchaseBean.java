package com.ewhale.points.web.managedbean.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PurchaseBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.user.proxy.UserServiceClient;

@SessionScoped
@ManagedBean
public class UserPurchaseBean extends PurchaseBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> allAgentsList;

	private Date purchaseDate_from, purchaseDate_to;

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	public Date getPurchaseDate_from()
	{
		return purchaseDate_from;
	}

	public void setPurchaseDate_from(Date purchaseDate_from)
	{
		this.purchaseDate_from = purchaseDate_from;
	}

	public Date getPurchaseDate_to()
	{
		return purchaseDate_to;
	}

	public void setPurchaseDate_to(Date purchaseDate_to)
	{
		this.purchaseDate_to = purchaseDate_to;
	}

	public void getUserPurchasesList()
	{
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Purchase.purchaseId, null },
					{ EntityConstants.Purchase.insertDate, null },
					{ EntityConstants.Purchase.agentInvoiceNumber, null },
					{ EntityConstants.Purchase.agentInvoiceValue, null },
					{ EntityConstants.Purchase.pointsValue, null },
					{ EntityConstants.Purchase.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Purchase.branch, EntityConstants.Branch.branchName } };

		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Purchase.agentId, getAgentId());
		data.put(EntityConstants.Purchase.insertDate_From_Search, purchaseDate_from);
		data.put(EntityConstants.Purchase.insertDate_To_Search, purchaseDate_to);
		data.put(EntityConstants.Purchase.profileId, FacesUtil.getLoginId());
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> purchasesList = userServiceClient.userPurchasesList(data);
		populateTable(purchasesList, columnKeys);
		// LOG.debug(exchangesList.size());
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> purchaseDetails = userServiceClient.userPurchaseDetails(data.get(EntityConstants.Purchase.purchaseId) + "");
		fillDetailsData(purchaseDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.Agent.tradeMark))
		{
			UserAgentBean userAgentBean = FacesUtil.getObjectFromSession(UserAgentBean.class, true);
			userAgentBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Purchase.agent));
		}
		else if (selectedField.equals(EntityConstants.Branch.branchName))
		{
			UserAgentBranchBean userAgentBranchBean = FacesUtil.getObjectFromSession(UserAgentBranchBean.class, true);
			userAgentBranchBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Purchase.branch));
		}
	}

	@Override
	protected String getDetailsPageName()
	{
		return "purchase_details";
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		setHasMoreDetails(true);
		allAgentsList = UserBeanUtils.getAllAgentsList();
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

	// IMP_Ahmed should be removed -- only done from the mobile
	public void confirm()
	{
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		userServiceClient.confirmPointsId("" + getPurchaseId(), "" + FacesUtil.getLoginId());
		getUserPurchasesList();
		closeDialoge();
	}

	public void reject()
	{
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		userServiceClient.rejectPoints("" + getPurchaseId(), "" + FacesUtil.getLoginId());
		getUserPurchasesList();
		closeDialoge();
	}
}
