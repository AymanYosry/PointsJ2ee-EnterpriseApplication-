package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;

/**
 * The persistent class for the agent database table.
 */
@Entity
@Table(name = "agent")


@NamedQueries(value =
{ @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
		@NamedQuery(name = "Agent.lastUpdateDateNamedQuery", query = "SELECT MAX(a.statusDate) FROM Agent a ") })

@PrimaryKeyJoinColumn(name = "agent_id", referencedColumnName = "item_status_id")
public class Agent extends ItemStatus implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String lastUpdateDateNamedQuery = "Agent.lastUpdateDateNamedQuery";

	public static String[] entityIdNames = new String[]
		{ EntityConstants.Agent.agentId };

	@Column(name = "agent_id", unique = true, nullable = false, insertable = false, updatable = false)
	private long agentId;

	@Column(name = "administration_adderss", nullable = false, length = 255)
	private String administrationAdderss;

	@Column(name = "administration_phone", nullable = false, length = 20)
	private String administrationPhone;

	@Column(name = "call_center")
	private Integer callCenter;

	@Column(name = "commercial_record_no", nullable = false, length = 255)
	private String commercialRecordNo;

	@Lob
	@Column(nullable = false)
	private byte[] logo;

	@Column(name = "trade_mark", nullable = false, length = 45)
	private String tradeMark;

	@Column(name = "rate_value", nullable = false)
	private float rateValue;

	@Column(name = "rates_number", nullable = false)
	private int ratesNumber;

	// bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name = "agent_country_id", referencedColumnName = "country_id", nullable = false)
	private Country country;

	// bi-directional many-to-many association to Category
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "agent_category", joinColumns =
		{ @JoinColumn(name = "agent_category_agent_id", nullable = false) }, inverseJoinColumns =
		{ @JoinColumn(name = "agent_category_category_id", nullable = false) })
	private List<Category> categories;

	// bi-directional many-to-one association to AgentRate
	@OneToMany(mappedBy = "agent")
	private List<AgentRate> agentRates;

	// bi-directional many-to-one association to Branch
	@OneToMany(mappedBy = "agent", cascade = CascadeType.PERSIST)
	private List<Branch> branches;

	// bi-directional many-to-one association to Contract
	@OneToMany(mappedBy = "agent")
	private List<Contract> contracts;

	// bi-directional many-to-one association to MessageCenter
	@OneToMany(mappedBy = "agent")
	private List<MessageCenter> messageCenters;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "agent")
	private List<Product> products;

	// bi-directional many-to-one association to Profile
	@OneToMany(mappedBy = "agent")
	private List<Profile> employees;

	// bi-directional many-to-one association to Promotion
	@OneToMany(mappedBy = "agent")
	private List<Promotion> promotions;

	// bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy = "agent")
	private List<Purchase> purchases;

	// bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy = "agent")
	private List<PointsExchange> pointsExchanges;

	// bi-directional many-to-one association to SysInvoice
	@OneToMany(mappedBy = "agent", cascade = CascadeType.PERSIST)
	private List<SysInvoice> sysInvoices;

	// bi-directional many-to-one association to SysPayment
	@OneToMany(mappedBy = "agent")
	private List<SysPayment> sysPayments;

	public Agent()
	{
	}

	public long getAgentId()
	{
		return this.agentId;
	}

	public void setAgentId(long agentId)
	{
		this.agentId = agentId;
		setItemId(agentId);
	}

	public String getAdministrationAdderss()
	{
		return this.administrationAdderss;
	}

	public void setAdministrationAdderss(String administrationAdderss)
	{
		this.administrationAdderss = administrationAdderss;
	}

	public String getAdministrationPhone()
	{
		return this.administrationPhone;
	}

	public void setAdministrationPhone(String administrationPhone)
	{
		this.administrationPhone = administrationPhone;
	}

	public Integer getCallCenter()
	{
		return this.callCenter;
	}

	public void setCallCenter(Integer callCenter)
	{
		this.callCenter = callCenter;
	}

	public String getCommercialRecordNo()
	{
		return this.commercialRecordNo;
	}

	public void setCommercialRecordNo(String commercialRecordNo)
	{
		this.commercialRecordNo = commercialRecordNo;
	}

	public byte[] getLogo()
	{
		return this.logo;
	}

	public void setLogo(byte[] logo)
	{
		this.logo = logo;
	}

	public String getTradeMark()
	{
		return this.tradeMark;
	}

	public void setTradeMark(String tradeMark)
	{
		this.tradeMark = tradeMark;
	}

	public float getRateValue()
	{
		return rateValue;
	}

	public void setRateValue(float rateValue)
	{
		this.rateValue = rateValue;
	}

	public int getRatesNumber()
	{
		return ratesNumber;
	}

	public void setRatesNumber(int ratesNumber)
	{
		this.ratesNumber = ratesNumber;
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	public List<Category> getCategories()
	{
		return this.categories;
	}

	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
	}

	public List<AgentRate> getAgentRates()
	{
		return this.agentRates;
	}

	public void setAgentRates(List<AgentRate> agentRates)
	{
		this.agentRates = agentRates;
	}

	public AgentRate addAgentRate(AgentRate agentRate)
	{
		getAgentRates().add(agentRate);
		agentRate.setAgent(this);

		return agentRate;
	}

	public AgentRate removeAgentRate(AgentRate agentRate)
	{
		getAgentRates().remove(agentRate);
		agentRate.setAgent(null);

		return agentRate;
	}

	public List<Branch> getBranches()
	{
		return this.branches;
	}

	public void setBranches(List<Branch> branches)
	{
		this.branches = branches;
	}

	public Branch addBranch(Branch branch)
	{
		getBranches().add(branch);
		branch.setAgent(this);

		return branch;
	}

	public Branch removeBranch(Branch branch)
	{
		getBranches().remove(branch);
		branch.setAgent(null);

		return branch;
	}

	public List<Contract> getContracts()
	{
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts)
	{
		this.contracts = contracts;
	}

	public Contract addContract(Contract contract)
	{
		getContracts().add(contract);
		contract.setAgent(this);

		return contract;
	}

	public Contract removeContract(Contract contract)
	{
		getContracts().remove(contract);
		contract.setAgent(null);

		return contract;
	}

	public List<MessageCenter> getMessageCenters()
	{
		return this.messageCenters;
	}

	public void setMessageCenters(List<MessageCenter> messageCenters)
	{
		this.messageCenters = messageCenters;
	}

	public MessageCenter addMessageCenter(MessageCenter messageCenter)
	{
		getMessageCenters().add(messageCenter);
		messageCenter.setAgent(this);

		return messageCenter;
	}

	public MessageCenter removeMessageCenter(MessageCenter messageCenter)
	{
		getMessageCenters().remove(messageCenter);
		messageCenter.setAgent(null);

		return messageCenter;
	}

	public List<Product> getProducts()
	{
		return this.products;
	}

	public void setProducts(List<Product> products)
	{
		this.products = products;
	}

	public Product addProduct(Product product)
	{
		getProducts().add(product);
		product.setAgent(this);

		return product;
	}

	public Product removeProduct(Product product)
	{
		getProducts().remove(product);
		product.setAgent(null);

		return product;
	}

	public List<Profile> getEmployees()
	{
		return this.employees;
	}

	public void setEmployees(List<Profile> employees)
	{
		this.employees = employees;
	}

	public Profile addEmployee(Profile employee)
	{
		getEmployees().add(employee);
		employee.setAgent(this);

		return employee;
	}

	public Profile removeEmployee(Profile employee)
	{
		getEmployees().remove(employee);
		employee.setAgent(null);

		return employee;
	}

	public List<Promotion> getPromotions()
	{
		return this.promotions;
	}

	public void setPromotions(List<Promotion> promotions)
	{
		this.promotions = promotions;
	}

	public Promotion addPromotion(Promotion promotion)
	{
		getPromotions().add(promotion);
		promotion.setAgent(this);

		return promotion;
	}

	public Promotion removePromotion(Promotion promotion)
	{
		getPromotions().remove(promotion);
		promotion.setAgent(null);

		return promotion;
	}

	public List<Purchase> getPurchases()
	{
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases)
	{
		this.purchases = purchases;
	}

	public Purchase addPurchas(Purchase purchas)
	{
		getPurchases().add(purchas);
		purchas.setAgent(this);

		return purchas;
	}

	public Purchase removePurchas(Purchase purchas)
	{
		getPurchases().remove(purchas);
		purchas.setAgent(null);

		return purchas;
	}

	public List<PointsExchange> getPointsExchanges()
	{
		return pointsExchanges;
	}

	public void setPointsExchanges(List<PointsExchange> pointsExchanges)
	{
		this.pointsExchanges = pointsExchanges;
	}

	public PointsExchange addPointsExchange(PointsExchange pointsExchange)
	{
		getPointsExchanges().add(pointsExchange);
		pointsExchange.setAgent(this);

		return pointsExchange;
	}

	public PointsExchange removePointsExchange(PointsExchange pointsExchange)
	{
		getPointsExchanges().remove(pointsExchange);
		pointsExchange.setAgent(null);

		return pointsExchange;
	}

	public List<SysInvoice> getSysInvoices()
	{
		return this.sysInvoices;
	}

	public void setSysInvoices(List<SysInvoice> sysInvoices)
	{
		this.sysInvoices = sysInvoices;
	}

	public SysInvoice addSysInvoice(SysInvoice sysInvoice)
	{
		getSysInvoices().add(sysInvoice);
		sysInvoice.setAgent(this);

		return sysInvoice;
	}

	public SysInvoice removeSysInvoice(SysInvoice sysInvoice)
	{
		getSysInvoices().remove(sysInvoice);
		sysInvoice.setAgent(null);

		return sysInvoice;
	}

	public List<SysPayment> getSysPayments()
	{
		return this.sysPayments;
	}

	public void setSysPayments(List<SysPayment> sysPayments)
	{
		this.sysPayments = sysPayments;
	}

	public SysPayment addSysPayment(SysPayment sysPayment)
	{
		getSysPayments().add(sysPayment);
		sysPayment.setAgent(this);

		return sysPayment;
	}

	public SysPayment removeSysPayment(SysPayment sysPayment)
	{
		getSysPayments().remove(sysPayment);
		sysPayment.setAgent(null);

		return sysPayment;
	}

	@Override
	public Map<String, Object> transformToMap()
	{
		Map<String, Object> map = transformMainDataToMap();
		int[] categoryIds = new int[categories.size()];
		List<Map<String, Object>> categoryList = new ArrayList<>();
		int i = 0;
		for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext(); i++)
		{
			Category category = iterator.next();
			categoryList.add(category.transformMainDataToMap());
			categoryIds[i] = category.getCategoryId();
		}
		map.put(EntityConstants.Agent.categoryIds, categoryIds);
		map.put(EntityConstants.Agent.categories, categoryList);
		return map;
	}

	@Override
	public Map<String, Object> transformMainDataToMap()
	{
		Map<String, Object> map = super.transformMainDataToMap();
		map.put(EntityConstants.Agent.agentId, agentId);
		map.put(EntityConstants.Agent.tradeMark, tradeMark);
		map.put(EntityConstants.Agent.logo, logo);
		map.put(EntityConstants.Agent.administrationPhone, administrationPhone);
		map.put(EntityConstants.Agent.administrationAddress, administrationAdderss);
		map.put(EntityConstants.Agent.callCenter, callCenter);
		map.put(EntityConstants.Agent.commercialRecordNo, commercialRecordNo);
		map.put(EntityConstants.Agent.rateValue, rateValue);
		map.put(EntityConstants.Agent.ratesNumber, ratesNumber);
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);
		setTradeMark((String) data.get(EntityConstants.Agent.tradeMark));
		setLogo(Base64.getDecoder().decode((String) data.get(EntityConstants.Agent.logo)));
		setAdministrationPhone((String) data.get(EntityConstants.Agent.administrationPhone));
		setAdministrationAdderss((String) data.get(EntityConstants.Agent.administrationAddress));
		setCallCenter(((Number) data.get(EntityConstants.Agent.callCenter)).intValue());
		setCommercialRecordNo((String) data.get(EntityConstants.Agent.commercialRecordNo));
	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<T> criteriaQuery,
			final Root<T> criteriaRoot, Map<String, Object> criteria) throws EntityException
	{
		 List<Predicate> predicateList=setCriteria(criteriaBuilder, criteriaRoot, criteria);
		Boolean hasNoContract = (Boolean) criteria.get(EntityConstants.Agent.hasNoContract);
		if (hasNoContract != null)
		{
			Subquery<Contract> contractSubQuery = criteriaQuery.subquery(Contract.class);
			Root<Contract> contractRoot = contractSubQuery.from(Contract.class);
			contractSubQuery.select(contractRoot.get(EntityConstants.Contract.agent));
			predicateList.add(criteriaRoot.in(contractSubQuery).not());
		}
		return predicateList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria) throws EntityException
	{
		List<Predicate> predicateList = super.setCriteria(criteriaBuilder, criteriaRoot, criteria);
		Object agentIdSe = criteria.get(EntityConstants.Agent.agentId);
		if (agentIdSe != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.Agent.agentId), agentIdSe));
		String tradeMarkSe = (String) criteria.get(EntityConstants.Agent.tradeMark);
		if (tradeMarkSe != null)
			predicateList.add(criteriaBuilder.like(criteriaRoot.get(EntityConstants.Agent.tradeMark), tradeMarkSe + "%"));
		Number countryIdSearch = (Number) criteria.get(EntityConstants.Agent.countryId);
		if (countryIdSearch != null)
		{
			Join<Agent, Country> agentCountryJoin = criteriaRoot.join(EntityConstants.Agent.country);
			predicateList.add(criteriaBuilder.equal(agentCountryJoin.get(EntityConstants.Country.countryId), countryIdSearch.byteValue()));
		}
		// IMP_Badawy this is a sample of the join search criteria
		// first join the root to the new entity
		// second use the result of the join to search
		// search using category data
		Number categoryId = (Number) criteria.get(EntityConstants.Agent.categoryId);
		if (categoryId != null)
		{
			Join<Agent, Category> agentCategoryJoin = criteriaRoot.join(EntityConstants.Agent.categories);
			predicateList.add(criteriaBuilder.equal(agentCategoryJoin.get(EntityConstants.Category.categoryId), categoryId.byteValue()));
		}
		List categoryIds = (List) criteria.get(EntityConstants.Category.categoryIds);
		if (categoryIds != null && !categoryIds.isEmpty())
		{
			Join<Agent, Category> agentCategoryJoin = criteriaRoot.join(EntityConstants.Agent.categories);
			predicateList.add(agentCategoryJoin.get(EntityConstants.Category.categoryId).in(categoryIds));
		}
		// search using branch data
		Number stateId = (Number) criteria.get(EntityConstants.Agent.stateId);
		if (stateId != null)
		{
			Join<Agent, Branch> agentBranchJoin = criteriaRoot.join(EntityConstants.Agent.branches);
			Join<Branch, State> branchStateJoin = agentBranchJoin.join(EntityConstants.Branch.state);
			predicateList.add(criteriaBuilder.equal(branchStateJoin.get(EntityConstants.State.stateId), stateId.intValue()));
		}

		Number locationLatitude_From = (Number) criteria.get(EntityConstants.Agent.locationLatitude_From_Search);
		if (locationLatitude_From != null)
		{
			Join<Agent, Branch> agentBranshJoin = criteriaRoot.join(EntityConstants.Agent.branches);
			predicateList.add(
					criteriaBuilder.greaterThan(agentBranshJoin.get(EntityConstants.Agent.locationLatitude), locationLatitude_From.doubleValue()));
		}
		Number locationLatitude_TO = (Number) criteria.get(EntityConstants.Agent.locationLatitude_To_Search);
		if (locationLatitude_TO != null)
		{
			Join<Agent, Branch> agentBranshJoin = criteriaRoot.join(EntityConstants.Agent.branches);
			predicateList
					.add(criteriaBuilder.lessThan(agentBranshJoin.get(EntityConstants.Agent.locationLatitude), locationLatitude_TO.doubleValue()));
		}
		Number locationLongitude_From = (Number) criteria.get(EntityConstants.Agent.locationLongitude_From_Search);
		if (locationLongitude_From != null)
		{
			Join<Agent, Branch> agentBranshJoin = criteriaRoot.join(EntityConstants.Agent.branches);
			predicateList.add(
					criteriaBuilder.greaterThan(agentBranshJoin.get(EntityConstants.Agent.locationLongitude), locationLongitude_From.doubleValue()));
		}
		Number locationLongitude_To = (Number) criteria.get(EntityConstants.Agent.locationLatitude_To_Search);
		if (locationLongitude_To != null)
		{
			Join<Agent, Branch> agentBranshJoin = criteriaRoot.join(EntityConstants.Agent.branches);
			predicateList
					.add(criteriaBuilder.lessThan(agentBranshJoin.get(EntityConstants.Agent.locationLongitude), locationLongitude_To.doubleValue()));
		}
		// search using contracts data
		Number contractStartDate_From = (Number) criteria.get(EntityConstants.Agent.contractStartDate_From_Search);
		if (contractStartDate_From != null)
		{
			Join<Agent, Contract> agentContractJoin = criteriaRoot.join(EntityConstants.Agent.contracts);
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(agentContractJoin.get(EntityConstants.Contract.startDate),
					getDateFromLong(contractStartDate_From.longValue())));
		}
		Number contractStartDate_To = (Number) criteria.get(EntityConstants.Agent.contractStartDate_To_Search);
		if (contractStartDate_To != null)
		{
			Join<Agent, Contract> agentContractJoin = criteriaRoot.join(EntityConstants.Agent.contracts);
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(agentContractJoin.get(EntityConstants.Contract.startDate),
					getDateFromLong(contractStartDate_To.longValue())));
		}
		Number contractEndDate_From = (Number) criteria.get(EntityConstants.Agent.contractEndDate_From_Search);
		if (contractEndDate_From != null)
		{
			Join<Agent, Contract> agentContractJoin = criteriaRoot.join(EntityConstants.Agent.contracts);
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(agentContractJoin.get(EntityConstants.Contract.endDate),
					getDateFromLong(contractEndDate_From.longValue())));
		}
		Number contractEndDate_To = (Number) criteria.get(EntityConstants.Agent.contractEndDate_To_Search);
		if (contractEndDate_To != null)
		{
			Join<Agent, Contract> agentContractJoin = criteriaRoot.join(EntityConstants.Agent.contracts);
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(agentContractJoin.get(EntityConstants.Contract.endDate),
					getDateFromLong(contractEndDate_To.longValue())));
		}
		Number newly_Added_From_Date = (Number) criteria.get(EntityConstants.Agent.newly_Added_From_Date_Search);
		if (newly_Added_From_Date != null)
		{
			Join<Agent, Contract> agentContractJoin = criteriaRoot.join(EntityConstants.Agent.contracts);
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(agentContractJoin.get(EntityConstants.Contract.startDate),
					getDateFromLong(newly_Added_From_Date.longValue())));
		}
		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		long id = ((Number) data.get(EntityConstants.Agent.agentId)).longValue();
		setItemId(id);
		setAgentId(id);
	}

	@Override
	public Object getEntityId()
	{
		return getAgentId();
	}

	@Override
	public void setEntityId(Object idObj)
	{
		long id = ((Number) idObj).longValue();
		setItemId(id);
		setAgentId(id);
	}
}
