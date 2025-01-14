package com.ewhale.points.controller.facade;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Agent;
import com.ewhale.points.model.entity.Branch;
import com.ewhale.points.model.entity.Category;
import com.ewhale.points.model.entity.Country;
import com.ewhale.points.model.entity.State;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class AgentFacadeBean
 */
@Stateless
@Local(AgentFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgentFacadeBean extends AbsoluteFacadeBean implements AgentFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	CategoryFacade categoryFacade;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.AGENT) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		Agent agent = (Agent) entity;
		FacadeBeanUtils.addStatusDetails(em, entity, data);
		// branches
		Branch branch = new Branch();
		branch.setEntityData(data, false);
		State state = em.viewRecordDetails(State.class, ((Number) data.get(EntityConstants.Branch.stateId)).intValue());
		Country country = em.viewRecordDetails(Country.class, ((Number) data.get(EntityConstants.Branch.countryId)).byteValue());
		branch.setState(state);
		branch.setCountry(country);
		agent.setBranches(new ArrayList<Branch>());
		agent.addBranch(branch);

		// categories
		Map<String, Object> categorySearchCriteria = new HashMap<String, Object>();
		categorySearchCriteria.put(EntityConstants.Category.categoryIds, data.get(EntityConstants.Agent.categoryIds));
		List<Category> catList = em.viewRecordList(EntityClassEnum.CATEGORY.entityClass, categorySearchCriteria);
		agent.setCategories(catList);
		// the first most invoice
		SysInvoice sysInvoice = new SysInvoice();
		sysInvoice.setActive(true);
		sysInvoice.setInvoiceValue(0);
		sysInvoice.setCurrency(country.getCurrency());
		sysInvoice.setInsertDate(new Date());

		agent.setSysInvoices(new ArrayList<SysInvoice>());
		agent.addSysInvoice(sysInvoice);
		agent.setCountry(country);

	}

	@Override
	protected void updateEntityDetails(Map<String, Object> data) throws EntityException
	{
		Agent agent = em.viewRecordDetails(Agent.class, ((Number) data.get(EntityConstants.Agent.agentId)).longValue());
		agent.setCategories(null);
		Map<String, Object> categorySearchCriteria = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		List categoryIds = (List) data.remove(EntityConstants.Agent.categoryIds);
		if (categoryIds == null || categoryIds.size() == 0)
			throw new EntityException("Category is needed");
		categorySearchCriteria.put(EntityConstants.Category.categoryIds, categoryIds);
		@SuppressWarnings("unchecked")
		List<Category> catList = em.viewRecordList(EntityClassEnum.CATEGORY.entityClass, categorySearchCriteria);
		agent.setCategories(catList);
		//
		String logo = (String) data.remove(EntityConstants.Agent.logo);
		if (logo != null)
			data.put(EntityConstants.Agent.logo, Base64.getDecoder().decode(logo));

	}

	@Override
	public boolean isSynchronizationNeeded(long dateInTimeMillis) throws FacadeException
	{
		try
		{
			long lastUpdateTimeInMillis = 0;
			@SuppressWarnings("rawtypes")
			List lastUpdateDateList = em.executeNamedQuery(Agent.lastUpdateDateNamedQuery, null, 1);
			Date lastUpdateDate = (Date) lastUpdateDateList.get(0);
			if (lastUpdateDate != null)
				lastUpdateTimeInMillis = lastUpdateDate.getTime();
			return lastUpdateTimeInMillis > dateInTimeMillis;
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}
}
