package com.ewhale.points.web.managedbean.agentseller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PointsExchangeBean;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerExchangeBean extends PointsExchangeBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void mobileChanged(ValueChangeEvent e)
	{
		setProfileMobile(e.getNewValue().toString());
	}

	public String exchangeUserPoints()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.PointsExchange.branchId, FacesUtil.getLoginData().get(EntityConstants.Login.branchId));
		data.put(EntityConstants.PointsExchange.productId, getProductId());
		data.put(EntityConstants.PointsExchange.profile_mobile, getProfileMobile());
		data.put(EntityConstants.PointsExchange.insEmpId, FacesUtil.getLoginId());
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken(), ServiceClientUtil.WEB);
		fillDetailsData(agentSellerServiceClient.exchangeUserPoints(data));
		resetToAdd();
		closeDialoge();
		
		return "";
	}

	public void postExchangeDialog(SelectEvent event)
	{
		FacesUtil.growlInfoMessage("Success", "Exchange has been done.");
	}
	public String reExchangeUserPoints()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.PointsExchange.branchId, FacesUtil.getLoginData().get(EntityConstants.Login.branchId));
		data.put(EntityConstants.PointsExchange.productId, getProductId());
		data.put(EntityConstants.PointsExchange.profile_mobile, getProfileMobile());
		data.put(EntityConstants.PointsExchange.insEmpId, FacesUtil.getLoginId());
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken(), ServiceClientUtil.WEB);
		fillDetailsData(agentSellerServiceClient.reExchangeUserPoints(data));
		FacesUtil.growlInfoMessage("Success", "Re-Exchange has been done.");
		resetToAdd();
		return ""/* exchange_details.xhtml*/;
	}

	public void getUserExchangesList()
	{
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.PointsExchange.pointsExchangeId, null },
					{ EntityConstants.PointsExchange.insertDate, null },
					{ EntityConstants.PointsExchange.product, EntityConstants.Product.productName },
					{ EntityConstants.PointsExchange.pointsValue, null },
					{ EntityConstants.PointsExchange.branch, EntityConstants.Branch.branchName } };

		if (getProfileMobile() == null)
		{
			populateTable(new ArrayList<>(), columnKeys);
			return;
		}
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.PointsExchange.agentId, FacesUtil.getLoginData().get(EntityConstants.Login.agentId));
		data.put(EntityConstants.PointsExchange.profile_mobile, getProfileMobile());

		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> userExchangesList = agentSellerServiceClient.userExchangesList(data);

		populateTable(userExchangesList, columnKeys);
	}

	public void setReExchangeData(Map<String, Object> exchangeDetails)
	{
		@SuppressWarnings("unchecked")
		Map<String, Object> profileData = (Map<String, Object>) exchangeDetails.get(EntityConstants.PointsExchange.profile);
		if (!profileData.get(EntityConstants.Profile.mobile).equals(getProfileMobile()))
		{
			FacesUtil.addErrorMessage("Error", "Wrong user");
			getUserExchangesList();
			return;
		}
		setProductId(((Number) exchangeDetails.get(EntityConstants.PointsExchange.productId)).longValue());
		@SuppressWarnings("unchecked")
		Map<String, Object> productData = (Map<String, Object>) exchangeDetails.get(EntityConstants.PointsExchange.product);
		setProductName((String) productData.get(EntityConstants.Product.productName));
		setProductPhoto((String) productData.get(EntityConstants.Product.photo));
		setPointsValue(((Number) productData.get(EntityConstants.Product.pointsValue)).intValue());
	}

	public void setProductExchange(Map<String, Object> productData)
	{
		resetToAdd();
		setProductId(((Number) productData.get(EntityConstants.Product.productId)).longValue());
		setProductName((String) productData.get(EntityConstants.Product.productName));
		setProductPhoto((String) productData.get(EntityConstants.Product.photo));
		setPointsValue(((Number) productData.get(EntityConstants.Product.pointsValue)).intValue());
		RequestContext.getCurrentInstance().openDialog("product_exchange.xhtml", dialogeOptions, null);

	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> exchangeDetails = agentSellerServiceClient.viewExchangeDetails(getPointsExchangeId() + "");
		fillDetailsData(exchangeDetails);
	}

	@Override
	protected void resetToAdd()
	{
		setTableData(null);
		super.resetToAdd();
	}
	public String goToAddReExchange()
	{
		resetToAdd();
		return "product_re_exchange";
	}
	
}
