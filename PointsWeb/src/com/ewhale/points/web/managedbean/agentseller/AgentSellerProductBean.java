package com.ewhale.points.web.managedbean.agentseller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.ProductBean;
import com.ewhale.points.ws.agent.proxy.AgentSellerServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentSellerProductBean extends ProductBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date validityDate_from;

	private Date validityDate_to;

	private Short statusIdSearch;

	private List<Map<String, Object>> statusesList;

	private String agentName;

	private String viewValidityDate;

	private String countryName;

//	private Float exchangePoints;

	
	
	public Date getValidityDate_from()
	{
		return validityDate_from;
	}

	public void setValidityDate_from(Date validityDate_from)
	{
		this.validityDate_from = validityDate_from;
	}

	public Date getValidityDate_to()
	{
		return validityDate_to;
	}

	public void setValidityDate_to(Date validityDate_to)
	{
		this.validityDate_to = validityDate_to;
	}

	public Short getStatusIdSearch()
	{
		return statusIdSearch;
	}

	public void setStatusIdSearch(Short statusIdSearch)
	{
		this.statusIdSearch = statusIdSearch;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public void setStatusesList(List<Map<String, Object>> statusesList)
	{
		this.statusesList = statusesList;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}

	public String getViewValidityDate()
	{
		return viewValidityDate;
	}

	public void setViewValidityDate(String viewValidityDate)
	{
		this.viewValidityDate = viewValidityDate;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

//	public Float getExchangePoints()
//	{
//		return exchangePoints;
//	}
//
//	public void setExchangePoints(Float exchangePoints)
//	{
//		this.exchangePoints = exchangePoints;
//	}

	@Override
	protected void handlePostConstruct()
	{
		statusesList = FacesUtil.loadStatusesList();
		setCanAdd(false);
		setCanUpdate(false);
	}

	@Override
	protected String getDetailsPageName()
	{
		return "product_details.xhtml";
	}

	public void getAgentProductsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Product.agentId, FacesUtil.getLoginAgentId());
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> productsList = agentSellerServiceClient.agentProductsList(data);
		populateTable(productsList, EntityConstants.Product.productName, EntityConstants.Product.validityDate, EntityConstants.Product.pointsValue);

	}

	public String productReExchange()
	{
		return "product_re_exchange.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		AgentSellerServiceClient agentSellerServiceClient = ServiceClientUtil.getAgentSellerServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> productDetails = agentSellerServiceClient.viewProductDetails(data.get(EntityConstants.Product.productId) + "");
		fillDetailsData(productDetails);
		countryName = ((String) ((Map<String, Object>) data.get(EntityConstants.Product.country)).get(EntityConstants.Country.countryName));
		viewValidityDate = (String) productDetails.get(EntityConstants.Product.validityDate);
		agentName = ((String) ((Map<String, Object>) data.get(EntityConstants.Product.agent)).get(EntityConstants.Agent.tradeMark));
		// IMP_Ahmed show product photo and calculate exchange points to view them in details screen.
	}
}
