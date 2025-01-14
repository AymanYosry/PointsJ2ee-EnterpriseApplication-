package com.ewhale.points.web.managedbean.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ewhale.points.common.util.EntityConstants;

public class PaymentBean extends ItemStatusBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long agentId;

	private Long sysPaymentId;

	private Short currencyId;

	private Short paymentMethodId;

	private Date insertDate;

	private Date paymentDate;

	private Float paymentValue;

	private String paymentDateStr, insertDateStr, paymentMethodName;

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public Long getSysPaymentId()
	{
		return sysPaymentId;
	}

	public void setSysPaymentId(Long sysPaymentId)
	{
		this.sysPaymentId = sysPaymentId;
	}

	public Date getInsertDate()
	{
		return insertDate;
	}

	public String getInsertDateStr()
	{
		return insertDateStr;
	}

	public Date getPaymentDate()
	{
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate)
	{
		this.paymentDate = paymentDate;
	}

	public String getPaymentDateStr()
	{
		return paymentDateStr;
	}

	public void setPaymentDateStr(String paymentDateStr)
	{
		this.paymentDateStr = paymentDateStr;
	}

	public Float getPaymentValue()
	{
		return paymentValue;
	}

	public void setPaymentValue(Float paymentValue)
	{
		this.paymentValue = paymentValue;
	}

	public Short getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(Short currencyId)
	{
		this.currencyId = currencyId;
	}

	public Short getPaymentMethodId()
	{
		return paymentMethodId;
	}

	public void setPaymentMethodId(Short paymentMethodId)
	{
		this.paymentMethodId = paymentMethodId;
	}

	public String getPaymentMethodName()
	{
		return paymentMethodName;
	}

	protected Map<String, Object> fillDataMap()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysPayment.paymentDate, paymentDate);
		data.put(EntityConstants.SysPayment.paymentValue, paymentValue);
		data.put(EntityConstants.SysPayment.currencyId, currencyId);
		data.put(EntityConstants.SysPayment.paymentMethodId, paymentMethodId);
		return data;
	}

	@SuppressWarnings("unchecked")
	protected void fillDetailsData(Map<String, Object> data)
	{
		paymentDateStr = (String) data.get(EntityConstants.SysPayment.paymentDate);
		paymentDate = FacesUtil.getDateFromString(paymentDateStr);
		insertDateStr = (String) data.get(EntityConstants.SysPayment.insertDate);
		insertDate = FacesUtil.getDateFromString(insertDateStr);
		paymentValue = ((Number) data.get(EntityConstants.SysPayment.paymentValue)).floatValue();
		currencyId = ((Number) ((Map<String, Object>) data.get(EntityConstants.SysPayment.currency)).get(EntityConstants.Currency.currencyId))
				.shortValue();
		Map<String, Object> paymentMethod = (Map<String, Object>) data.get(EntityConstants.SysPayment.paymentMethod);
		paymentMethodId = ((Number) paymentMethod.get(EntityConstants.PaymentMethod.paymentMethodId)).shortValue();
		paymentMethodName = ((String) paymentMethod.get(EntityConstants.PaymentMethod.paymentMethodName));
		agentId = ((Number) ((Map<String, Object>) data.get(EntityConstants.SysPayment.agent)).get(EntityConstants.Agent.agentId)).longValue();
		sysPaymentId = ((Number) data.get(EntityConstants.SysPayment.sysPaymentId)).longValue();
	}

}
