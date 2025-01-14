package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PromotionBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentPromotionBean extends PromotionBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date promotionDate_from, promotionDate_to;

	private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> statusesList;

	private List<Map<String, Object>> allAgentsList;

	private Short statusIdSearch;

	private Long messageCenterId;

	private Short statusId;

	private String currencyName, statusName, insEmpFullName, updateEmpFullName, agentName;

	private String insertDate, lastUpdateDate;

	private String refuseCause;

	public Long getMessageCenterId()
	{
		return messageCenterId;
	}

	public void setMessageCenterId(Long messageCenterId)
	{
		this.messageCenterId = messageCenterId;
	}

	public Short getStatusId()
	{
		return statusId;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public String getCurrencyName()
	{
		return currencyName;
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

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	public Short getStatusIdSearch()
	{
		return statusIdSearch;
	}

	public void setStatusIdSearch(Short statusIdSearch)
	{
		this.statusIdSearch = statusIdSearch;
	}

	public String getRefuseCause()
	{
		return refuseCause;
	}

	public void setRefuseCause(String refuseCause)
	{
		this.refuseCause = refuseCause;
	}

	public void activatePromotion()
	{
		changeStatus(EntityConstants.Status.Fixed.activeStatus.ID);
		closeDialoge();
	}

	public void blockPromotion()
	{
		changeStatus(EntityConstants.Status.Fixed.blockedStatus.ID);
		closeDialoge();
	}

	// public void pendPromotion()
	// {
	// changeStatus(EntityConstants.Status.Fixed.pendingStatus.ID);
	// }

	public void refusePromotion()
	{
		changeStatus(EntityConstants.Status.Fixed.pendingStatus.ID);
		closeDialoge();
	}

	private void changeStatus(short statusId)
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getPromotionId());
		data.put(EntityConstants.ItemStatus.statusId, statusId);
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginId());
		if (messageCenterId != null)
		{
			data.put(EntityConstants.MessageCenter.messageCenterId, messageCenterId);
			data.put(EntityConstants.MessageCenter.responseMessage, refuseCause);
		}
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateItemStatus(data);
		FacesUtil.growlInfoMessage("Success", "Status Has Been Changed");
	}

	public void getPromotions()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Promotion.agentId, getAgentId());
		data.put(EntityConstants.Promotion.invoiceId, getSysInvoiceId());
		data.put(EntityConstants.Promotion.promotionDate_From_Search, promotionDate_from);
		data.put(EntityConstants.Promotion.promotionDate_To_Search, promotionDate_to);
		data.put(EntityConstants.Promotion.statusId, statusIdSearch);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> promotionData = systemAdminServiceClient.getPromotionsList(data);

		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Promotion.promotionDate, null },
					{ EntityConstants.Promotion.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Promotion.message, null },
					{ EntityConstants.Promotion.statusName, null },
					{ EntityConstants.Promotion.country, EntityConstants.Country.countryName } };

		populateTable(promotionData, columnKeys);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> promotionData = systemAdminServiceClient.promotionDetails(data.get(EntityConstants.Promotion.promotionId) + "");
		fillDetailsData(promotionData);
		currencyName = ((String) ((Map<String, Object>) promotionData.get(EntityConstants.Promotion.currency))
				.get(EntityConstants.Currency.currencyName));
		agentName = ((String) ((Map<String, Object>) promotionData.get(EntityConstants.Promotion.agent)).get(EntityConstants.Agent.tradeMark));
		statusName = ((String) promotionData.get(EntityConstants.Promotion.statusName));
		Number statusIdObj = ((Number) promotionData.get(EntityConstants.Promotion.statusId));
		statusId = statusIdObj.shortValue();
		insEmpFullName = ((String) promotionData.get(EntityConstants.Promotion.insEmpFullName));
		insertDate = ((String) promotionData.get(EntityConstants.Promotion.insertDate));
		lastUpdateDate = ((String) promotionData.get(EntityConstants.Promotion.lastUpdDate));
	}

	@Override
	protected String getDetailsPageName()
	{
		return "promotion_details.xhtml";
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		countriesList = FacesUtil.loadCountriesList();
		statusesList = FacesUtil.loadStatusesList();
		allAgentsList=SysAdminBeanUtils.getAllAgentsList();
		setDialogeHeight(500);
		setDialogeWidth(700);
	}
}
