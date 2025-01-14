package com.ewhale.points.web.managedbean.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.ProductBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.user.proxy.UserServiceClient;

@SessionScoped
@ManagedBean
public class UserAgentProductBean extends ProductBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String agentName;

	private int points;

	private boolean showAll = false;

	private List<Map<String, Object>> allAgentsList;

	public String getAgentName()
	{
		return agentName;
	}

	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}

	public boolean isShowAll()
	{
		return showAll;
	}

	public void setShowAll(boolean showAll)
	{
		this.showAll = showAll;
	}

	public List<Map<String, Object>> getAgentsList()
	{
		return allAgentsList;
	}

	public void getProducts()
	{
		Map<String, Object> data = new HashMap<>();
		if (!showAll)
			data.put(EntityConstants.Product.pointsValue_Max, points);
		data.put(EntityConstants.Product.agentId, getAgentId());
		data.put(EntityConstants.Product.countryId, FacesUtil.getLoginData().get(EntityConstants.Login.countryId));
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> productsList = userServiceClient.agentProductsList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Product.productName, null },
					{ EntityConstants.Product.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Product.validityDate, null },
					{ EntityConstants.Product.pointsValue, null } };
		populateTable(productsList, columnKeys);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> productData = userServiceClient.agentProductDetails(data.get(EntityConstants.Product.productId) + "");
		fillDetailsData(productData);
		agentName = ((String) ((Map<String, Object>) productData.get(EntityConstants.Product.agent)).get(EntityConstants.Agent.tradeMark));
	}

	@Override
	protected String getDetailsPageName()
	{
		return "product_details";
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		setHasMoreDetails(false);

		UserServiceClient userServiceClient = ServiceClientUtil.getUserServiceClient(FacesUtil.getLoginToken());
		points = userServiceClient.getSumProfilePoints("" + FacesUtil.getLoginId());
		allAgentsList = UserBeanUtils.getAllAgentsList();
		getProducts();
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

}
