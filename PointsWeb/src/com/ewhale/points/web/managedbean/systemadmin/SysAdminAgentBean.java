package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.LatLng;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AgentBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;
import com.ewhale.points.ws.system.proxy.SystemSalesServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentBean extends AgentBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(SysAdminAgentBean.class);

	private String tradeMarkSe;

	private Date startContractDate_From;

	private Date startContractDate_To;

	private Date insertDate_From;

	private Short statusIdSe;

	private Integer countryIdSe;

	private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> statusesList;

	private List<Map<String, Object>> statesList;

	@Override
	protected void handlePostConstruct()
	{
		countriesList = FacesUtil.loadCountriesList();
		statusesList = FacesUtil.loadStatusesList();
		dialogeOptions.put("width", 740);
		dialogeOptions.put("height", 440);
	}

	public String getTradeMarkSe()
	{
		return tradeMarkSe;
	}

	public void setTradeMarkSe(String tradeMarkSe)
	{
		this.tradeMarkSe = tradeMarkSe;
	}

	public Date getStartContractDate_From()
	{
		return startContractDate_From;
	}

	public void setStartContractDate_From(Date startContractDate_From)
	{
		this.startContractDate_From = startContractDate_From;
	}

	public Date getStartContractDate_To()
	{
		return startContractDate_To;
	}

	public void setStartContractDate_To(Date startContractDate_To)
	{
		this.startContractDate_To = startContractDate_To;
	}

	public Date getInsertDate_From()
	{
		return insertDate_From;
	}

	public void setInsertDate_From(Date insertDate_From)
	{
		this.insertDate_From = insertDate_From;
	}

	public Integer getCountryIdSe()
	{
		return countryIdSe;
	}

	public void setCountryIdSe(Integer countryIdSe)
	{
		this.countryIdSe = countryIdSe;
	}

	public Short getStatusIdSe()
	{
		return statusIdSe;
	}

	public void setStatusIdSe(Short statusIdSe)
	{
		this.statusIdSe = statusIdSe;
	}

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public List<Map<String, Object>> getStatusesList()
	{
		return statusesList;
	}

	public List<Map<String, Object>> getStatesList()
	{
		return statesList;
	}

	public void addAgent() throws Exception
	{
		logger.debug("-- logo size " + getUploadedLogo().getSize());
		Map<String, Object> data = fillAgentDataMap();
		data.put(EntityConstants.Agent.updateStatusEmpId, FacesUtil.getLoginId());
		fillBranchDataMap(data);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.addAgent(data);
		FacesUtil.growlInfoMessage("Successful", "Agent Added ");
	}

	public void updateAgent() throws Exception
	{
		Map<String, Object> data = fillAgentDataMap();
		data.put(EntityConstants.Agent.agentId, getAgentId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateAgent(data);
		viewAgentsList();
		closeDialoge();
	}

	public void activateAgent()
	{
		changeStatus(EntityConstants.Status.Fixed.activeStatus.ID);
		closeDialoge();
	}

	public void blockAgent()
	{
		changeStatus(EntityConstants.Status.Fixed.blockedStatus.ID);
		closeDialoge();
	}

	private void changeStatus(int statusId)
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getAgentId());
		data.put(EntityConstants.ItemStatus.statusId, statusId);
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginData().get(EntityConstants.Login.loginId));
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateItemStatus(data);
		viewAgentsList();
	}

	public void viewAgentsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Agent.statusId, statusIdSe);
		data.put(EntityConstants.Agent.tradeMark, tradeMarkSe);
		data.put(EntityConstants.Agent.contractStartDate_From_Search, startContractDate_From);
		data.put(EntityConstants.Agent.contractStartDate_To_Search, startContractDate_To);
		data.put(EntityConstants.Agent.countryId, countryIdSe);
		data.put(EntityConstants.Agent.newly_Added_From_Date_Search, insertDate_From);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> allAgentsList = systemAdminServiceClient.getAgentsList(data);
		String[] linkableLists = new String[]
			{ EntityConstants.Agent.branches, EntityConstants.Agent.products, EntityConstants.Agent.promotions, EntityConstants.Agent.contracts,
					EntityConstants.Agent.sysPayments, EntityConstants.Agent.sysInvoices, EntityConstants.Agent.employees };
		populateTable(allAgentsList, linkableLists, EntityConstants.Agent.tradeMark, EntityConstants.Agent.callCenter,
				EntityConstants.Agent.administrationPhone, EntityConstants.Agent.administrationAddress);
	}

	@Override
	public String viewListDetails(Map<String, Object> data, String selectedField)
	{
		logger.debug("-----------" + selectedField);
		long selectedAgentId = ((Number) data.get(EntityConstants.Agent.agentId)).longValue();
		String selectedAgentTradeMark = (String) data.get(EntityConstants.Agent.tradeMark);
		if (selectedField.equals(EntityConstants.Agent.branches))
		{
			SysAdminAgentBranchBean sysAdminAgentBranchBean = FacesUtil.getObjectFromSession(SysAdminAgentBranchBean.class, true);
			sysAdminAgentBranchBean.setAgentId(selectedAgentId);
			sysAdminAgentBranchBean.setParentPageTitle(selectedAgentTradeMark);
			sysAdminAgentBranchBean.setParentPage("agent_search");
			sysAdminAgentBranchBean.getAgentBranches();
			return "branch_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.contracts))
		{
			SysAdminAgentContractBean sysAdminAgentContractBean = FacesUtil.getObjectFromSession(SysAdminAgentContractBean.class, true);
			sysAdminAgentContractBean.setAgentId(selectedAgentId);
			sysAdminAgentContractBean.setAgentIdSearch(selectedAgentId);
			// sysAdminAgentContractBean.setParentPageTitle(selectedAgentTradeMark);
			// sysAdminAgentContractBean.setParentPage("agent_search");
			sysAdminAgentContractBean.getAgentContracts();
			return "contract_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.sysPayments))
		{
			SysAdminAgentPaymentBean sysAdminAgentPaymentBean = FacesUtil.getObjectFromSession(SysAdminAgentPaymentBean.class, true);
			sysAdminAgentPaymentBean.setAgentId(selectedAgentId);
			sysAdminAgentPaymentBean.setParentPageTitle(selectedAgentTradeMark);
			sysAdminAgentPaymentBean.setParentPage("agent_search");
			sysAdminAgentPaymentBean.getAgentPayments();
			return "payment_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.products))
		{
			SysAdminAgentProductBean sysAdminAgentProductBean = FacesUtil.getObjectFromSession(SysAdminAgentProductBean.class, true);
			sysAdminAgentProductBean.setAgentId(selectedAgentId);
			// sysAdminAgentProductBean.setParentPageTitle(selectedAgentTradeMark);
			// sysAdminAgentProductBean.setParentPage("agent_search");
			sysAdminAgentProductBean.getAgentProducts();
			return "product_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.employees))
		{
			SysAdminAgentEmployeeBean sysAdminAgentEmployeeBean = FacesUtil.getObjectFromSession(SysAdminAgentEmployeeBean.class, true);
			sysAdminAgentEmployeeBean.setAgentId(selectedAgentId);
			// sysAdminAgentEmployeeBean.setParentPageTitle(selectedAgentTradeMark);
			// sysAdminAgentEmployeeBean.setParentPage("agent_search");
			sysAdminAgentEmployeeBean.getAgentEmployeesList();
			return "agent_employee_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.promotions))
		{
			SysAdminAgentPromotionBean sysAdminAgentPromotionBean = FacesUtil.getObjectFromSession(SysAdminAgentPromotionBean.class, true);
			sysAdminAgentPromotionBean.setAgentId(selectedAgentId);
			// sysAdminAgentPromotionBean.setParentPageTitle(selectedAgentTradeMark);
			// sysAdminAgentPromotionBean.setParentPage("agent_search");
			sysAdminAgentPromotionBean.getPromotions();
			return "promotion_search";
		}
		else if (selectedField.equals(EntityConstants.Agent.sysInvoices))
		{
			SysAdminAgentInvoiceBean sysAdminAgentInvoiceBean = FacesUtil.getObjectFromSession(SysAdminAgentInvoiceBean.class, true);
			sysAdminAgentInvoiceBean.setAgentId(selectedAgentId);
			// sysAdminAgentInvoiceBean.setParentPageTitle(selectedAgentTradeMark);
			// sysAdminAgentInvoiceBean.setParentPage("agent_search");
			sysAdminAgentInvoiceBean.getAgentInvoices();
			return "invoice_search";
		}
		return null;
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> agentData = systemAdminServiceClient.agentDetails(data.get(EntityConstants.Agent.agentId) + "");
		fillDetailsData(agentData);

	}

	public void deleteAgent()
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.deleteAgent(getAgentId() + "");
	}

	@Override
	protected String getDetailsPageName()
	{
		return "agent_details.xhtml";
	}

	@Override
	protected String getUpdatePageName()
	{
		return "agent_details.xhtml";
	}

	public void loadStatesList()
	{
		Map<String, Object> stateSeMap = new HashMap<>();
		stateSeMap.put(EntityConstants.State.countryId, getBranchBean().getCountryId());
		logger.debug("-----" + getToken());
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		statesList = systemSalesServiceClient.getStatesList(stateSeMap);
	}

	public void onPointSelect(PointSelectEvent event)
	{
		LatLng latlng = event.getLatLng();
		getBranchBean().setLocationLatitude(latlng.getLat());
		getBranchBean().setLocationLongitude(latlng.getLng());
	}

	// public void handleFileUpload(FileUploadEvent event)
	// {
	// setUploadedLogo(event.getFile());
	// FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
	// FacesContext.getCurrentInstance().addMessage(null, message);
	// }

}
