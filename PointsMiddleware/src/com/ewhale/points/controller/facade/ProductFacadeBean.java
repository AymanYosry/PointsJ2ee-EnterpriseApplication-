package com.ewhale.points.controller.facade;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Agent;
import com.ewhale.points.model.entity.Country;
import com.ewhale.points.model.entity.Currency;
import com.ewhale.points.model.entity.FunctionType;
import com.ewhale.points.model.entity.MessageCenter;
import com.ewhale.points.model.entity.Product;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class ProductFacadeBean
 */
@Stateless
@Local(ProductFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProductFacadeBean extends AbsoluteFacadeBean implements ProductFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.PRODUCT) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		// to add insEmp Data
		FacadeBeanUtils.addInsEmpDetails(em, entity, data);
		FacadeBeanUtils.addStatusDetails(em, entity, data);
		Agent agent = em.viewRecordDetails(Agent.class, ((Number) data.get(EntityConstants.Product.agentId)).longValue());
		Country country = em.viewRecordDetails(Country.class, agent.getCountry().getCountryId());
		Currency currency = em.viewRecordDetails(Currency.class, ((Number) data.get(EntityConstants.Product.currencyId)).shortValue());
		((Product) entity).setAgent(agent);
		((Product) entity).setCountry(country);
		((Product) entity).setCurrency(currency);
	}

	@Override
	protected void postAdd(Map<String, Object> data, AbsoluteEntity entity) throws EntityException, ValidationException
	{
		Product product = (Product) entity;
		MessageCenter messageCenter = new MessageCenter();
		messageCenter.setAgent(product.getAgent());
		FunctionType functionType = em.viewRecordDetails(FunctionType.class, EntityConstants.FunctionType.Fixed.activateProductFunction.ID);
		messageCenter.setFunctionType(functionType);
		messageCenter.setMessageCenterItemId(product.getItemId());
		messageCenter.setRequestAgentEmp(product.getInsEmp());
		messageCenter.setRequestDate(new Date());
		messageCenter.setRequestMessage("activateProduct");
		em.addRecord(messageCenter);
	}

	@Override
	protected void updateEntityDetails(Map<String, Object> data) throws EntityException
	{
		FacadeBeanUtils.addUpEmpDetails(data);
		// to gurantee that the agent id never changed
		data.remove(EntityConstants.Product.agentId);
		// to gurantee that the country id never changed
		data.remove(EntityConstants.Product.countryId);

		Number currencyId = ((Number) data.remove(EntityConstants.Product.currencyId));
		Currency currency = new Currency();
		currency.setCurrencyId(currencyId.shortValue());
		data.put(EntityConstants.Product.currency, currency);

		data.put(EntityConstants.Product.validityDate, new Date((Long) data.get(EntityConstants.Product.validityDate)));

		//
		String photo = (String) data.remove(EntityConstants.Product.photo);
		if (photo != null)
			data.put(EntityConstants.Product.photo, Base64.getDecoder().decode(photo));
	}

	@Override
	public boolean isSynchronizationNeeded(long dateInTimeMillis) throws FacadeException
	{
		try
		{
			@SuppressWarnings("rawtypes")
			List lastUpdateDateList = em.executeNamedQuery(Product.lastUpdateDateNamedQuery, null, 1);
			Object[] changeDates = (Object[]) lastUpdateDateList.get(0);
			for (Object date : changeDates)
			{
				Timestamp timeStamp = (Timestamp) date;
				if (timeStamp != null && timeStamp.getTime() > dateInTimeMillis)
					return true;
			}
			return false;
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}
}
