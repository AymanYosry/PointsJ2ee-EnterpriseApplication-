package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.ProductBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentProductBean extends ProductBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date validityDate_from, validityDate_to;

	private List<Map<String, Object>> currenciesList;

	private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> statusesList;

	private List<Map<String, Object>> allAgentsList;

	private Short statusIdSearch;

	private Long messageCenterId;

	private Short statusId;

	private String currencyName;

	private String agentName, refuseCause;

	public String getCurrencyName()
	{
		return currencyName;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public Short getStatusId()
	{
		return statusId;
	}

	public Long getMessageCenterId()
	{
		return messageCenterId;
	}

	public void setMessageCenterId(Long messageCenterId)
	{
		this.messageCenterId = messageCenterId;
	}

	public String getRefuseCause()
	{
		return refuseCause;
	}

	public void setRefuseCause(String refuseCause)
	{
		this.refuseCause = refuseCause;
	}

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

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public List<Map<String, Object>> getCurrenciesList()
	{
		return currenciesList;
	}

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public Short getStatusIdSearch()
	{
		return statusIdSearch;
	}

	public void setStatusIdSearch(Short statusIdSearch)
	{
		this.statusIdSearch = statusIdSearch;
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		countriesList = FacesUtil.loadCountriesList();
		currenciesList = FacesUtil.loadCurrenciesList();
		statusesList = FacesUtil.loadStatusesList();
		allAgentsList=SysAdminBeanUtils.getAllAgentsList();
		setDialogeHeight(500);
		setDialogeWidth(700);
	}

	public void activateProduct()
	{
		changeStatus(EntityConstants.Status.Fixed.activeStatus.ID);
		closeDialoge();
	}

	public void refuseProduct()
	{
		changeStatus(EntityConstants.Status.Fixed.pendingStatus.ID);
		closeDialoge();
	}

	public void blockProduct()
	{
		changeStatus(EntityConstants.Status.Fixed.blockedStatus.ID);
		closeDialoge();
	}

	private void changeStatus(short statusId)
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getProductId());
		data.put(EntityConstants.ItemStatus.statusId, statusId);
		if (messageCenterId != null)
		{
			data.put(EntityConstants.MessageCenter.messageCenterId, messageCenterId);
			data.put(EntityConstants.MessageCenter.responseMessage, refuseCause);
		}
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateItemStatus(data);
		getAgentProducts();
	}

	public void getAgentProducts()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Product.agentId, getAgentId());
		data.put(EntityConstants.Product.validityDate_From_Search, validityDate_from);
		data.put(EntityConstants.Product.validityDate_To_Search, validityDate_to);
		data.put(EntityConstants.Product.statusId, statusIdSearch);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> agentProductsList = systemAdminServiceClient.getAgentProductsList(data);

		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Product.productName, null },
					{ EntityConstants.Product.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Product.validityDate, null },
					{ EntityConstants.Product.pointsValue, null },
					{ EntityConstants.Product.price, null },
//					{ EntityConstants.Product.country, EntityConstants.Country.countryName },
//					{ EntityConstants.Product.currency, EntityConstants.Currency.currencyName },
					{ EntityConstants.Product.statusName, null } };

		populateTable(agentProductsList, columnKeys);
		// LOG.debug(agentProductsList.size());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> productData = systemAdminServiceClient.productDetails(data.get(EntityConstants.Product.productId) + "");
		fillDetailsData(productData);
		currencyName = ((String) ((Map<String, Object>) productData.get(EntityConstants.Product.currency))
				.get(EntityConstants.Currency.currencyName));
		agentName = ((String) ((Map<String, Object>) productData.get(EntityConstants.Product.agent)).get(EntityConstants.Agent.tradeMark));
		Number statusIdObj = ((Number) productData.get(EntityConstants.Product.statusId));
		statusId = statusIdObj.shortValue();
	}

	@Override
	protected String getDetailsPageName()
	{
		return "product_details.xhtml";
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

}
