package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.ContractBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.systemsales.CountryBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentContractBean extends ContractBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> currenciesList;

	private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> statusesList;

	private List<Map<String, Object>> agentsList, allAgentsList;

	private Short statusIdSearch;

	private Long agentIdSearch;

	private Date startDateFromSearch;

	private Date startDateToSearch;

	private Date endDateFromSearch;

	private Date endDateToSearch;

	public List<Map<String, Object>> getCurrenciesList()
	{
		return currenciesList;
	}

	public void setCurrenciesList(List<Map<String, Object>> currenciesList)
	{
		this.currenciesList = currenciesList;
	}

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public void setCountriesList(List<Map<String, Object>> countriesList)
	{
		this.countriesList = countriesList;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public void setStatusesList(List<Map<String, Object>> statusesList)
	{
		this.statusesList = statusesList;
	}

	public List<Map<String, Object>> getAgentsList()
	{
		return agentsList;
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

	public Long getAgentIdSearch()
	{
		return agentIdSearch;
	}

	public void setAgentIdSearch(Long agentIdSearch)
	{
		this.agentIdSearch = agentIdSearch;
	}

	public Date getStartDateFromSearch()
	{
		return startDateFromSearch;
	}

	public void setStartDateFromSearch(Date startDateFromSearch)
	{
		this.startDateFromSearch = startDateFromSearch;
	}

	public Date getStartDateToSearch()
	{
		return startDateToSearch;
	}

	public void setStartDateToSearch(Date startDateToSearch)
	{
		this.startDateToSearch = startDateToSearch;
	}

	public Date getEndDateFromSearch()
	{
		return endDateFromSearch;
	}

	public void setEndDateFromSearch(Date endDateFromSearch)
	{
		this.endDateFromSearch = endDateFromSearch;
	}

	public Date getEndDateToSearch()
	{
		return endDateToSearch;
	}

	public void setEndDateToSearch(Date endDateToSearch)
	{
		this.endDateToSearch = endDateToSearch;
	}

	@Override
	protected void handlePostConstruct()
	{
		countriesList = FacesUtil.loadCountriesList();
		currenciesList = FacesUtil.loadCurrenciesList();
		statusesList = FacesUtil.loadStatusesList();
		loadAgentsList();
	}

	private void loadAgentsList()
	{
		allAgentsList=SysAdminBeanUtils.getAllAgentsList();
		Map<String, Object> data = new HashMap<>();
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		data.put(EntityConstants.Agent.hasNoContract, true);
		agentsList = systemAdminServiceClient.getAgentsList(data);
	}

	@Override
	protected String getUpdatePageName()
	{
		return "contract_details.xhtml";
	}

	@Override
	protected String getDetailsPageName()
	{
		return "contract_details.xhtml";
	}

	public void addAgentContract()
	{
		Map<String, Object> data = fillDataMap();
		data.put(EntityConstants.Contract.insEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.addAgentContract(data);
		FacesUtil.growlInfoMessage("Success", "Contract Has Been Added successfully");
		getAgentContracts();
	}

	public void updateAgentContract()
	{
		Map<String, Object> data = fillDataMap();
		data.put(EntityConstants.Contract.contractId, getContractId());
		data.put(EntityConstants.Contract.updateEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateAgentContract(data);
		FacesUtil.growlInfoMessage("Success", "Contract Has Been Changed successfully");
		getAgentContracts();
		closeDialoge();
	}

	private Map<String, Object> fillDataMap()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Contract.discountPercent, getDiscountPercent());
		data.put(EntityConstants.Contract.endDate, getEndDate());
		data.put(EntityConstants.Contract.pointsPercent, getPointsPercent());
		data.put(EntityConstants.Contract.profitPercent, getProfitPercent());
		data.put(EntityConstants.Contract.startDate, getStartDate());
		data.put(EntityConstants.Contract.agentId, getAgentId());
		data.put(EntityConstants.Contract.currencyId, getCurrencyId());
		return data;
	}

	public void activateContract()
	{
		changeStatus(EntityConstants.Status.Fixed.activeStatus.ID);
		closeDialoge();
	}

	public void blockContract()
	{
		changeStatus(EntityConstants.Status.Fixed.blockedStatus.ID);
		closeDialoge();
	}

	private void changeStatus(Short statusId)
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getContractId());
		data.put(EntityConstants.ItemStatus.statusId, statusId);
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateItemStatus(data);
		FacesUtil.growlInfoMessage("Success", "Status Has Been Changed successfully");
		getAgentContracts();
	}

	public void getAgentContracts()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Contract.agentId, agentIdSearch);
		data.put(EntityConstants.Contract.startDate_From_Search, startDateFromSearch);
		data.put(EntityConstants.Contract.startDate_To_Search, startDateToSearch);
		data.put(EntityConstants.Contract.endDate_From_Search, endDateFromSearch);
		data.put(EntityConstants.Contract.endDate_To_Search, endDateToSearch);
		data.put(EntityConstants.Contract.countryId, getCountryId());

		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> agentContractsList = systemAdminServiceClient.getAgentContractsList(data);

		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Contract.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.Contract.startDate, null },
					{ EntityConstants.Contract.endDate, null },
					{ EntityConstants.Contract.discountPercent, null },
					{ EntityConstants.Contract.pointsPercent, null },
					{ EntityConstants.Contract.profitPercent, null },
					{ EntityConstants.Contract.country, EntityConstants.Country.countryName } };

		populateTable(agentContractsList, columnKeys);
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> contractData = systemAdminServiceClient.contractDetails(data.get(EntityConstants.Contract.contractId) + "");
		fillDetailsData(contractData);
	}

	public void deleteContract()
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.deleteContract(getContractId() + "");
	}

	@Override
	protected void resetToAdd()
	{
		setCountryId(null);
		setCurrencyId(null);
		setDiscountPercent(null);
		setPointsPercent(null);
		setProfitPercent(null);
		setStartDate(null);
		setEndDate(null);
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

	// @Override
	// public boolean isCanAdd()
	// {
	// List<Map<String, Object>> data = getTableData();
	// return super.isCanAdd() && (getAgentId() != null) && (data == null || data.size() == 0);
	// }

	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.Agent.tradeMark))
		{
			SysAdminAgentBean sysAdminAgentBean = FacesUtil.getObjectFromSession(SysAdminAgentBean.class, true);
			sysAdminAgentBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Contract.agent));
		}
		if (selectedField.equals(EntityConstants.Country.countryName))
		{
			CountryBean countryBean = FacesUtil.getObjectFromSession(CountryBean.class, true);
			countryBean.viewDetails((Map<String, Object>) data.get(EntityConstants.Contract.country));
		}
	}
}
