package com.ewhale.points.web.managedbean.main;

import java.util.Map;

import com.ewhale.points.common.util.EntityConstants;

public class InvoiceBean extends AbsoluteBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long agentId;

	private Long invoiceId;

	private String insertDate;

	private Float invoiceValue;

	private Boolean active;

	public InvoiceBean()
	{

	}

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public Long getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId)
	{
		this.invoiceId = invoiceId;
	}


	public Float getInvoiceValue()
	{
		return invoiceValue;
	}

	public void setInvoiceValue(Float invoiceValue)
	{
		this.invoiceValue = invoiceValue;
	}

	public String getInsertDate()
	{
		return insertDate;
	}

	public void setInsertDate(String insertDate)
	{
		this.insertDate = insertDate;
	}

	public Boolean isActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	protected void fillDetailsData(Map<String, Object> data)
	{
		invoiceId = ((Number) data.get(EntityConstants.SysInvoice.sysInvoiceId)).longValue();
		agentId = ((Number) data.get(EntityConstants.SysInvoice.agentId)).longValue();
		invoiceValue = ((Number) data.get(EntityConstants.SysInvoice.invoiceValue)).floatValue();
		insertDate = ((String) data.get(EntityConstants.SysInvoice.insertDate));
		active = (Boolean) data.get(EntityConstants.SysInvoice.active);
	}
	
	@Override
	public String getRowStyleClass(Map<String, Object> data)
	{
		if ((Boolean) data.get(EntityConstants.SysInvoice.active))
			return "redRow";
		else
			return null;
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
	}
}
