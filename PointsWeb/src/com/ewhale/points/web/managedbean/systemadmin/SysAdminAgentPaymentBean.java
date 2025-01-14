package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.PaymentBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentPaymentBean extends PaymentBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date paymentDateFromSearch;

	private Date paymentDateToSearch;

	private List<Map<String, Object>> currenciesList;

	private List<Map<String, Object>> allAgentsList;

	private short statusId;

	private String currencyName;

	private String agentName;

	@Override
	protected void handlePostConstruct()
	{
		currenciesList = FacesUtil.loadCurrenciesList();
		allAgentsList=SysAdminBeanUtils.getAllAgentsList();
		setHasMoreDetails(false);
		setCanUpdate(false);
	}

	public String getCurrencyName()
	{
		return currencyName;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public short getStatusId()
	{
		return statusId;
	}

	public List<Map<String, Object>> getCurrenciesList()
	{
		return currenciesList;
	}

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	public Date getPaymentDateFromSearch()
	{
		return paymentDateFromSearch;
	}

	public void setPaymentDateFromSearch(Date paymentDateFromSearch)
	{
		this.paymentDateFromSearch = paymentDateFromSearch;
	}

	public Date getPaymentDateToSearch()
	{
		return paymentDateToSearch;
	}

	public void setPaymentDateToSearch(Date paymentDateToSearch)
	{
		this.paymentDateToSearch = paymentDateToSearch;
	}

	public void addAgentPayment()
	{
		Map<String, Object> data = fillDataMap();
		data.put(EntityConstants.SysPayment.agentId, getAgentId());
		data.put(EntityConstants.SysPayment.insEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.addAgentPayment(data);
		getAgentPayments();
		FacesUtil.growlInfoMessage("Success", "Added Successfully");
	}

	public void cancelPayment()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getSysPaymentId());
		data.put(EntityConstants.ItemStatus.statusId, EntityConstants.Status.Fixed.blockedStatus.ID);
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginId());
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		systemAdminServiceClient.updateItemStatus(data);
		closeDialoge();

	}

	public void getAgentPayments()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysPayment.agentId, getAgentId());
		data.put(EntityConstants.SysPayment.paymentDate_From_Search, paymentDateFromSearch);
		data.put(EntityConstants.SysPayment.paymentDate_To_Search, paymentDateToSearch);

		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> agentPaymentsList = systemAdminServiceClient.getAgentPaymentsList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.SysPayment.sysPaymentId, null },
					{ EntityConstants.SysPayment.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.SysPayment.paymentDate, null },
					{ EntityConstants.SysPayment.paymentValue, null },
					{ EntityConstants.SysPayment.currency, EntityConstants.Currency.currencyName },
					{ EntityConstants.SysPayment.paymentMethod, EntityConstants.PaymentMethod.paymentMethodName } };
		populateTable(agentPaymentsList, columnKeys);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> paymentData = systemAdminServiceClient.paymentDetails(data.get(EntityConstants.SysPayment.sysPaymentId) + "");
		fillDetailsData(paymentData);
		currencyName = ((String) ((Map<String, Object>) paymentData.get(EntityConstants.SysPayment.currency))
				.get(EntityConstants.Currency.currencyName));
		agentName = ((String) ((Map<String, Object>) paymentData.get(EntityConstants.SysPayment.agent)).get(EntityConstants.Agent.tradeMark));
		Number statusIdObj = ((Number) paymentData.get(EntityConstants.SysPayment.statusId));
		statusId = statusIdObj.shortValue();

	}

	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.Agent.tradeMark))
		{
			SysAdminAgentBean sysAdminAgentBean = FacesUtil.getObjectFromSession(SysAdminAgentBean.class, true);
			sysAdminAgentBean.viewDetails((Map<String, Object>) data.get(EntityConstants.SysPayment.agent));
		}
	}

	@Override
	protected String getDetailsPageName()
	{
		return "payment_details.xhtml";
	}

	@Override
	protected void resetToAdd()
	{
		setCurrencyId(null);
		setPaymentDate(new Date());
		setPaymentValue(null);
		setPaymentMethodId(null);
	}

	@Override
	public void resetParentPage()
	{
		super.resetParentPage();
		setAgentId(null);
	}

	@Override
	public void postUpdateDialog(SelectEvent event)
	{
		super.postUpdateDialog(event);
		getAgentPayments();
	}
	// @Override
	// public boolean isCanAdd()
	// {
	// return super.isCanAdd() && (getAgentId() != null);
	// }
}
