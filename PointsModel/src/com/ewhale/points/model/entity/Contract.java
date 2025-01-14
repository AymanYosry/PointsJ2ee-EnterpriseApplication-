package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.model.entity.interfaces.InsertTracking;
import com.ewhale.points.model.entity.interfaces.UpdateTracking;

/**
 * The persistent class for the contract database table.
 */
@Entity
@Table(name = "contract")
@NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c")
@PrimaryKeyJoinColumn(name = "contract_id", referencedColumnName = "item_status_id")
public class Contract extends ItemStatus implements Serializable, UpdateTracking, InsertTracking
{
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = new String[]
		{ EntityConstants.Contract.contractId };

	@Column(name = "contract_id", unique = true, nullable = false, insertable = false, updatable = false)
	private long contractId;

	@Column(name = "discount_percent", nullable = false)
	private float discountPercent;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date", nullable = false)
	private Date insertDate;

	@Column(name = "points_percent", nullable = false)
	private float pointsPercent;

	@Column(name = "profit_percent", nullable = false)
	private float profitPercent;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_upd_date")
	private Date lastUpdDate;

	// bi-directional many-to-one association to Agent
	@ManyToOne
	@JoinColumn(name = "contract_agent_id", nullable = false)
	private Agent agent;

	// bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name = "contract_country_id", nullable = false)
	private Country country;

	// bi-directional many-to-one association to Currency
	@ManyToOne
	@JoinColumn(name = "contract_currency_id", nullable = false)
	private Currency currency;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "ins_emp_id", nullable = false)
	private Profile insEmp;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "upd_emp_id")
	private Profile updateEmp;

	public Contract()
	{
	}

	public long getContractId()
	{
		return this.contractId;
	}

	public void setContractId(long contractId)
	{
		this.contractId = contractId;
		setItemId(contractId);
	}

	public float getDiscountPercent()
	{
		return this.discountPercent;
	}

	public void setDiscountPercent(float discountPercent)
	{
		this.discountPercent = discountPercent;
	}

	public Date getEndDate()
	{
		return this.endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getInsertDate()
	{
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate)
	{
		this.insertDate = insertDate;
	}

	public float getPointsPercent()
	{
		return this.pointsPercent;
	}

	public void setPointsPercent(float pointsPercent)
	{
		this.pointsPercent = pointsPercent;
	}

	public float getProfitPercent()
	{
		return this.profitPercent;
	}

	public void setProfitPercent(float profitPercent)
	{
		this.profitPercent = profitPercent;
	}

	public Date getStartDate()
	{
		return this.startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getLastUpdDate()
	{
		return this.lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate)
	{
		this.lastUpdDate = lastUpdDate;
	}

	public Agent getAgent()
	{
		return this.agent;
	}

	public void setAgent(Agent agent)
	{
		this.agent = agent;
	}

	public Country getCountry()
	{
		return this.country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	public Currency getCurrency()
	{
		return this.currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Profile getInsEmp()
	{
		return this.insEmp;
	}

	public void setInsEmp(Profile insEmp)
	{
		this.insEmp = insEmp;
	}

	public Profile getUpdateEmp()
	{
		return this.updateEmp;
	}

	public void setUpdateEmp(Profile updateEmp)
	{
		this.updateEmp = updateEmp;
	}

	@Override
	public Map<String, Object> transformToMap()
	{
		Map<String, Object> map = transformMainDataToMap();
		map.put(EntityConstants.Contract.agent, agent.transformMainDataToMap());
		map.put(EntityConstants.Contract.country, country.transformMainDataToMap());
		map.put(EntityConstants.Contract.currency, currency.transformMainDataToMap());
		return map;
	}

	@Override
	public Map<String, Object> transformMainDataToMap()
	{
		Map<String, Object> map = super.transformMainDataToMap();
		map.put(EntityConstants.Contract.contractId, contractId);
		map.put(EntityConstants.Contract.discountPercent, discountPercent);
		map.put(EntityConstants.Contract.pointsPercent, pointsPercent);
		map.put(EntityConstants.Contract.profitPercent, profitPercent);
		map.put(EntityConstants.Contract.startDate, getStringFromDate(startDate));
		map.put(EntityConstants.Contract.endDate, getStringFromDate(endDate));
		// map.put(EntityConstants.Contract.insEmpId, insEmp.getProfileId());
		map.put(EntityConstants.Contract.insertDate, insertDate);
		if (updateEmp != null)
			map.put(EntityConstants.Contract.updateEmpFullName, updateEmp.getFullName());
		// map.put(EntityConstants.Contract.updateEmpId, updateEmp.getProfileId());
		if (lastUpdDate != null)
			map.put(EntityConstants.Contract.updateDate, getStringFromDate(lastUpdDate));
		map.put(EntityConstants.Contract.agentId, agent.getAgentId());
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);

		// Agent agent = new Agent();
		// agent.setAgentId(((Number) data.get(EntityConstants.Contract.agentId)).longValue());
		// setAgent(agent);
		setDiscountPercent(((Number) data.get(EntityConstants.Contract.discountPercent)).floatValue());
		setPointsPercent(((Number) data.get(EntityConstants.Contract.pointsPercent)).floatValue());
		setProfitPercent(((Number) data.get(EntityConstants.Contract.profitPercent)).floatValue());
		setStartDate(getDateFromLong((Long) data.get(EntityConstants.Contract.startDate)));
		setEndDate(getDateFromLong((Long) data.get(EntityConstants.Contract.endDate)));
		// Currency currency = new Currency();
		// currency.setCurrencyId(((Number) data.get(EntityConstants.Contract.currencyId)).shortValue());
		// setCurrency(currency);
		// Profile insEmp = new Profile();
		// insEmp.setProfileId(((Number) data.get(EntityConstants.Contract.insEmpId)).longValue());
		// setInsEmp(insEmp);
		// setInsertDate(getDateFromLong((Long) data.get(EntityConstants.Contract.insertDate)));
		// Profile updateEmp = new Profile();
		// updateEmp.setProfileId(((Number) data.get(EntityConstants.Contract.updateEmpId)).longValue());
		// setUpdateEmp(updateEmp);
		// setLastUpdDate(getDateFromLong((Long) data.get(EntityConstants.Contract.updateDate)));
		// Country country = new Country();
		// country.setCountryId(((Number) data.get(EntityConstants.Contract.countryId)).byteValue());
		// setCountry(country);
	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria) throws EntityException
	{
		List<Predicate> predicateList = super.setCriteria(criteriaBuilder, criteriaRoot, criteria);
		Number contractId = (Number) criteria.get(EntityConstants.Contract.contractId);
		if (contractId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.Contract.contractId), contractId.longValue()));

		Number agentId = (Number) criteria.get(EntityConstants.Contract.agentId);
		if (agentId != null)
		{
			Join<Contract, Agent> contractAgentJoin = criteriaRoot.join(EntityConstants.Contract.agent);
			predicateList.add(criteriaBuilder.equal(contractAgentJoin.get(EntityConstants.Agent.agentId), agentId.longValue()));
		}
		Number countryId = (Number) criteria.get(EntityConstants.Contract.countryId);
		if (countryId != null)
		{
			Join<Contract, Country> contractCountryJoin = criteriaRoot.join(EntityConstants.Contract.country);
			predicateList.add(criteriaBuilder.equal(contractCountryJoin.get(EntityConstants.Country.countryId), countryId.byteValue()));
		}

		Long startDateFrom = (Long) criteria.get(EntityConstants.Contract.startDate_From_Search);
		if (startDateFrom != null)
			predicateList.add(criteriaBuilder.greaterThan(criteriaRoot.get(EntityConstants.Contract.startDate), getDateFromLong(startDateFrom)));
		Long startDateTo = (Long) criteria.get(EntityConstants.Contract.startDate_To_Search);
		if (startDateTo != null)
			predicateList.add(criteriaBuilder.lessThan(criteriaRoot.get(EntityConstants.Contract.startDate), getDateFromLong(startDateTo)));
		Long endDateFrom = (Long) criteria.get(EntityConstants.Contract.endDate_From_Search);
		if (endDateFrom != null)
			predicateList.add(criteriaBuilder.greaterThan(criteriaRoot.get(EntityConstants.Contract.endDate), getDateFromLong(endDateFrom)));
		Long endDateTo = (Long) criteria.get(EntityConstants.Contract.endDate_To_Search);
		if (endDateTo != null)
			predicateList.add(criteriaBuilder.lessThan(criteriaRoot.get(EntityConstants.Contract.endDate), getDateFromLong(endDateTo)));
		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		long id = ((Number) data.get(EntityConstants.Contract.contractId)).longValue();
		setItemId(id);
		setContractId(id);
	}

	@Override
	public Object getEntityId()
	{
		return getContractId();
	}

	@Override
	public void setEntityId(Object idObj)
	{
		long id = ((Number) idObj).longValue();
		setItemId(id);
		setContractId(id);
	}
}
