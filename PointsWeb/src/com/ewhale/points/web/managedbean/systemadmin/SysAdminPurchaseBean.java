package com.ewhale.points.web.managedbean.systemadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PurchaseBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminPurchaseBean extends PurchaseBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> allAgentsList;

	protected Logger logger = Logger.getLogger(SysAdminPurchaseBean.class);

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		allAgentsList=SysAdminBeanUtils.getAllAgentsList();
	}

	public void getPurchasesList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Purchase.insertDate_From_Search, getPurchaseDate_from());
		data.put(EntityConstants.Purchase.insertDate_To_Search, getPurchaseDate_to());
		data.put(EntityConstants.Purchase.profile_mobile, getMobile());
		data.put(EntityConstants.Purchase.invoiceId, getSysInvoiceId());
		data.put(EntityConstants.Purchase.agentId, getAgentId());
		data.put(EntityConstants.Purchase.profileId, getProfileId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> allPurchasesList = systemAdminServiceClient.getPurchasesList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Purchase.insertDate, null },
					{ EntityConstants.Purchase.profile, EntityConstants.Profile.fullName },
					{ EntityConstants.Purchase.agentInvoiceNumber, null },
					{ EntityConstants.Purchase.agentInvoiceValue, null },
					{ EntityConstants.Purchase.pointsValue, null },
					{ EntityConstants.Purchase.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Purchase.branch, EntityConstants.Branch.branchName } };
		// IMP_Ahmed calculate ,EntityConstants.Purchase.system_profit
		populateTable(allPurchasesList, columnKeys);
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> purchaseData = systemAdminServiceClient.purchaseDetails(data.get(EntityConstants.Purchase.purchaseId) + "");
		fillDetailsData(purchaseData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.Agent.tradeMark))
		{
			SysAdminAgentBean sysAdminAgentBean = FacesUtil.getObjectFromSession(SysAdminAgentBean.class, true);
			sysAdminAgentBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Purchase.agent));
		}
		else if (selectedField.equals(EntityConstants.Branch.branchName))
		{
			SysAdminAgentBranchBean sysAdminAgentBranchBean = FacesUtil.getObjectFromSession(SysAdminAgentBranchBean.class, true);
			sysAdminAgentBranchBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Purchase.branch));
		}
	}

	@Override
	protected String getDetailsPageName()
	{
		return "purchase_details";
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
		setSysInvoiceId(null);
		setProfileId(null);
	}
}
