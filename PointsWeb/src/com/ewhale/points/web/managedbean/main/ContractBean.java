package com.ewhale.points.web.managedbean.main;

import java.util.Date;
import java.util.Map;

import com.ewhale.points.common.util.EntityConstants;

public class ContractBean extends ItemStatusBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long contractId;

	private Float discountPercent;

	private String startDateStr, endDateStr,updateDateStr;

	private Float pointsPercent;

	private Float profitPercent;

	private Date startDate, endDate,updateDate;

	private Long agentId;

	private String agentTradeMark, updateEmpFullName;

	private Short countryId;

	private String countryName;

	private Short currencyId, statusId;

	private String currencyName;

	public Long getContractId()
	{
		return contractId;
	}

	public void setContractId(Long contractId)
	{
		this.contractId = contractId;
	}

	public Float getDiscountPercent()
	{
		return discountPercent;
	}

	public void setDiscountPercent(Float discountPercent)
	{
		this.discountPercent = discountPercent;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public Float getPointsPercent()
	{
		return pointsPercent;
	}

	public void setPointsPercent(Float pointsPercent)
	{
		this.pointsPercent = pointsPercent;
	}

	public Float getProfitPercent()
	{
		return profitPercent;
	}

	public void setProfitPercent(Float profitPercent)
	{
		this.profitPercent = profitPercent;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public String getStartDateStr()
	{
		return startDateStr;
	}

	public String getEndDateStr()
	{
		return endDateStr;
	}

	public String getUpdateDateStr()
	{
		return updateDateStr;
	}

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public String getAgentTradeMark()
	{
		return agentTradeMark;
	}

	public void setAgentTradeMark(String agentTradeMark)
	{
		this.agentTradeMark = agentTradeMark;
	}

	public Short getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Short countryId)
	{
		this.countryId = countryId;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public Short getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(Short currencyId)
	{
		this.currencyId = currencyId;
	}

	public String getCurrencyName()
	{
		return currencyName;
	}

	public void setCurrencyName(String currencyName)
	{
		this.currencyName = currencyName;
	}

	public Short getStatusId()
	{
		return statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public String getUpdateEmpFullName()
	{
		return updateEmpFullName;
	}

	@SuppressWarnings("unchecked")
	protected void fillDetailsData(Map<String, Object> contractData)
	{
		setStatusId(((Number) contractData.get(EntityConstants.Contract.statusId)).shortValue());
		setDiscountPercent(((Number) contractData.get(EntityConstants.Contract.discountPercent)).floatValue());
		startDateStr = (String) contractData.get(EntityConstants.Contract.startDate);
		setStartDate(FacesUtil.getDateFromString(startDateStr));
		endDateStr = (String) contractData.get(EntityConstants.Contract.endDate);
		setEndDate(FacesUtil.getDateFromString(endDateStr));
		updateDateStr = (String) contractData.get(EntityConstants.Contract.updateDate);
		setUpdateDate(FacesUtil.getDateFromString(updateDateStr));
		setPointsPercent(((Number) contractData.get(EntityConstants.Contract.pointsPercent)).floatValue());
		setProfitPercent(((Number) contractData.get(EntityConstants.Contract.profitPercent)).floatValue());
		setAgentId(((Number) contractData.get(EntityConstants.Contract.agentId)).longValue());
		currencyName = ((String) ((Map<String, Object>) contractData.get(EntityConstants.Contract.currency))
				.get(EntityConstants.Currency.currencyName));
		countryName = ((String) ((Map<String, Object>) contractData.get(EntityConstants.Contract.country)).get(EntityConstants.Country.countryName));
		agentTradeMark = ((String) ((Map<String, Object>) contractData.get(EntityConstants.Contract.agent)).get(EntityConstants.Agent.tradeMark));
		updateEmpFullName = (String) contractData.get(EntityConstants.Contract.updateEmpFullName);
		setCountryId(((Number) ((Map<String, Object>) contractData.get(EntityConstants.Contract.country)).get(EntityConstants.Country.countryId))
				.shortValue());
		setCurrencyId(((Number) ((Map<String, Object>) contractData.get(EntityConstants.Contract.currency)).get(EntityConstants.Currency.currencyId))
				.shortValue());
		setContractId(((Number) contractData.get(EntityConstants.Contract.contractId)).longValue());

		// LOG.debug(contractData.get(EntityConstants.Contract.agentId));
	}
}
