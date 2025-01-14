package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.InvoiceBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentInvoiceBean extends InvoiceBean
{
	protected Logger LOG = Logger.getLogger(SysAdminAgentInvoiceBean.class);

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private Date invoiceDate_from, invoiceDate_to;

	private List<Map<String, Object>> countriesList, allAgentsList;

	private String agentName;

	public String getAgentName()
	{
		return agentName;
	}

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	public Date getInvoiceDate_from()
	{
		return invoiceDate_from;
	}

	public void setInvoiceDate_from(Date invoiceDate_from)
	{
		this.invoiceDate_from = invoiceDate_from;
	}

	public Date getInvoiceDate_to()
	{
		return invoiceDate_to;
	}

	public void setInvoiceDate_to(Date invoiceDate_to)
	{
		this.invoiceDate_to = invoiceDate_to;
	}

	public void getAgentInvoices()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysInvoice.agentId, getAgentId());
		data.put(EntityConstants.SysInvoice.invoiceDate_From_Search, invoiceDate_from);
		data.put(EntityConstants.SysInvoice.invoiceDate_To_Search, invoiceDate_to);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> agentInvoicesList = systemAdminServiceClient.getAgentInvoicesList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.SysInvoice.sysInvoiceId, null },
					{ EntityConstants.SysInvoice.agent, EntityConstants.Agent.tradeMark },
					{ EntityConstants.SysInvoice.insertDate, null },
					{ EntityConstants.SysInvoice.invoiceValue, null },
					{ EntityConstants.SysInvoice.currency, EntityConstants.Currency.currencyName } };
		String[] linkableLists = new String[]
			{ EntityConstants.SysInvoice.purchases, EntityConstants.SysInvoice.pointsExchanges, EntityConstants.SysInvoice.promotions };
		populateTable(agentInvoicesList, linkableLists, columnKeys);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.Agent.tradeMark))
		{
			SysAdminAgentBean sysAdminAgentBean = FacesUtil.getObjectFromSession(SysAdminAgentBean.class, true);
			sysAdminAgentBean.viewDetails((Map<String, Object>) data.get(EntityConstants.SysInvoice.agent));
		}
	}

	@Override
	public String viewListDetails(Map<String, Object> data, String selectedField)
	{
		LOG.debug("-----------" + selectedField);
		long selectedInvoice = ((Number) data.get(EntityConstants.SysInvoice.sysInvoiceId)).longValue();
		if (selectedField.equals(EntityConstants.SysInvoice.promotions))
		{
			SysAdminAgentPromotionBean sysAdminAgentPromotionBean = FacesUtil.getObjectFromSession(SysAdminAgentPromotionBean.class, true);
			sysAdminAgentPromotionBean.setSysInvoiceId(selectedInvoice);
			sysAdminAgentPromotionBean.setParentPageTitle("" + selectedInvoice);
			sysAdminAgentPromotionBean.setParentPage("invoice_search");
			sysAdminAgentPromotionBean.getPromotions();
			return "promotion_search";
		}
		else if (selectedField.equals(EntityConstants.SysInvoice.purchases))
		{
			SysAdminPurchaseBean sysAdminPurchaseBean = FacesUtil.getObjectFromSession(SysAdminPurchaseBean.class, true);
			sysAdminPurchaseBean.setSysInvoiceId(selectedInvoice);
			sysAdminPurchaseBean.setParentPageTitle("Invoice " + selectedInvoice);
			sysAdminPurchaseBean.setParentPage("invoice_search");
			sysAdminPurchaseBean.getPurchasesList();
			return "purchase_search";
		}
		else if (selectedField.equals(EntityConstants.SysInvoice.pointsExchanges))
		{
			SysAdminExchangeBean sysAdminExchangeBean = FacesUtil.getObjectFromSession(SysAdminExchangeBean.class, true);
			sysAdminExchangeBean.setSysInvoiceId(selectedInvoice);
			sysAdminExchangeBean.setParentPageTitle("" + selectedInvoice);
			sysAdminExchangeBean.setParentPage("invoice_search");
			sysAdminExchangeBean.getExchangesList();
			return "exchange_search";
		}
		return null;
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
		setHasMoreDetails(false);
		countriesList = FacesUtil.loadCountriesList();
		allAgentsList = SysAdminBeanUtils.getAllAgentsList();
		setDialogeHeight(500);
		setDialogeWidth(700);
	}

	@Override
	public String getDefaultSortOrder()
	{
		return "descending";
	}
}
