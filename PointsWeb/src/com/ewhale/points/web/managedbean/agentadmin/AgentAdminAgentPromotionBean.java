package com.ewhale.points.web.managedbean.agentadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PromotionBean;
import com.ewhale.points.ws.agent.proxy.AgentAdminServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

@SessionScoped
@ManagedBean
public class AgentAdminAgentPromotionBean extends PromotionBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date promotionDate_from, promotionDate_to;

	// private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> currenciesList;

	private List<Map<String, Object>> statusesList;

	// private UploadedFile uploadedPhoto;

	private String currencyName, statusName, insEmpFullName, updateEmpFullName;

	private String insertDate, lastUpdateDate;

	private Short statusIdSearch;

	public Date getPromotionDate_from()
	{
		return promotionDate_from;
	}

	public void setPromotionDate_from(Date promotionDate_from)
	{
		this.promotionDate_from = promotionDate_from;
	}

	public Date getPromotionDate_to()
	{
		return promotionDate_to;
	}

	public void setPromotionDate_to(Date promotionDate_to)
	{
		this.promotionDate_to = promotionDate_to;
	}

	// public List<Map<String, Object>> getCountriesList()
	// {
	// return countriesList;
	// }
	//

	public List<Map<String, Object>> getCurrenciesList()
	{
		return currenciesList;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public String getCurrencyName()
	{
		return currencyName;
	}

	public void setCurrencyName(String currencyName)
	{
		this.currencyName = currencyName;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public String getInsEmpFullName()
	{
		return insEmpFullName;
	}

	public String getInsertDate()
	{
		return insertDate;
	}

	public String getUpdateEmpFullName()
	{
		return updateEmpFullName;
	}

	public String getLastUpdateDate()
	{
		return lastUpdateDate;
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
		setDialogeWidth(700);
		currenciesList = FacesUtil.loadCurrenciesList();
		statusesList = FacesUtil.loadStatusesList();
		// countriesList = FacesUtil.loadCountriesList();
	}

	@Override
	protected String getDetailsPageName()
	{
		return "promotion_details.xhtml";
	}

	@Override
	protected String getUpdatePageName()
	{
		return "promotion_details.xhtml";
	}

	public void addAgentPromotion()
	{
		try
		{
			Map<String, Object> data = fillDataMap();
			data.put(EntityConstants.Promotion.agentId, FacesUtil.getLoginData().get(EntityConstants.Login.agentId));
			data.put(EntityConstants.Promotion.insEmpId, FacesUtil.getLoginId());
			data.put(EntityConstants.Promotion.countryId, FacesUtil.getLoginData().get(EntityConstants.Login.countryId));
			AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
			agentAdminServiceClient.addAgentPromotion(data);
			getAgentPromotions();
		}
		catch (Throwable e)
		{
			FacesUtil.addErrorMessage("Error", e.getMessage());
		}
	}

	public void updateAgentPromotion()
	{
		try
		{
			Map<String, Object> data = fillDataMap();
			data.put(EntityConstants.Promotion.promotionId, getPromotionId());
			data.put(EntityConstants.Promotion.updateEmpId, FacesUtil.getLoginId());
			AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
			agentAdminServiceClient.updateAgentPromotion(data);
			getAgentPromotions();
			closeDialoge();
		}
		catch (Throwable e)
		{
			FacesUtil.addErrorMessage("Error", e.getMessage());
		}
	}

	public void deleteAgentPromotion()
	{
		AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
		agentAdminServiceClient.deleteAgentPromotion(getPromotionId() + "");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> promotionData = agentAdminServiceClient.promotionDetails(data.get(EntityConstants.Promotion.promotionId) + "");
		fillDetailsData(promotionData);
		currencyName = ((String) ((Map<String, Object>) data.get(EntityConstants.Promotion.currency)).get(EntityConstants.Currency.currencyName));
		statusName = ((String) data.get(EntityConstants.Promotion.statusName));
		insEmpFullName = ((String) data.get(EntityConstants.Promotion.insEmpFullName));
		insertDate = ((String) data.get(EntityConstants.Promotion.insertDate));
		lastUpdateDate = ((String) data.get(EntityConstants.Promotion.lastUpdDate));
	}

	public void getAgentPromotions()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Promotion.agentId, FacesUtil.getLoginData().get(EntityConstants.Login.agentId));
		data.put(EntityConstants.Promotion.promotionDate_From_Search, promotionDate_from);
		data.put(EntityConstants.Promotion.promotionDate_To_Search, promotionDate_to);
		data.put(EntityConstants.Promotion.statusId, statusIdSearch);
		AgentAdminServiceClient agentAdminServiceClient = ServiceClientUtil.getAgentAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> promotionData = agentAdminServiceClient.getAgentPromotionsList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Promotion.promotionDate, null },
					{ EntityConstants.Promotion.message, null },
					{ EntityConstants.Promotion.messageDetails, null },
					{ EntityConstants.Product.statusName, null } };
		populateTable(promotionData, columnKeys);

	}

	@Override
	protected void resetToAdd()
	{
		setPromotionDate(null);
		setPromotionFees(null);
		setPromotionId(null);
		setCurrencyId(null);
		setImage(null);
		setMessage(null);
		setMessageDetails(null);
	}

	@Override
	public boolean isCanUpdate(Map<String, Object> data)
	{
		return ((Number)data.get(EntityConstants.Promotion.statusId)).byteValue()!= EntityConstants.Status.Fixed.activeStatus.ID;
	}
}
