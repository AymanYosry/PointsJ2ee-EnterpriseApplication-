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
import com.ewhale.points.model.entity.Promotion;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class PromotionFacadeBean
 */
@Stateless
@Local(PromotionFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PromotionFacadeBean extends AbsoluteFacadeBean implements PromotionFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.PROMOTION) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		Promotion promotion = (Promotion) entity;
		FacadeBeanUtils.addInsEmpDetails(em, entity, data);
		FacadeBeanUtils.addStatusDetails(em, entity, data);

		SysInvoice sysInvoice = FacadeBeanUtils.getAgentPendingInvoice(em, ((Number) data.get(EntityConstants.Promotion.agentId)).longValue());
		promotion.setSysInvoice(sysInvoice);

		Currency currency = em.viewRecordDetails(Currency.class, ((Number) data.get(EntityConstants.Promotion.currencyId)).shortValue());
		promotion.setCurrency(currency);
		Agent agent = em.viewRecordDetails(Agent.class, ((Number) data.get(EntityConstants.Promotion.agentId)).longValue());
		promotion.setAgent(agent);

		Country country = em.viewRecordDetails(Country.class, agent.getCountry().getCountryId());
		promotion.setCountry(country);

		promotion.setPromotionFees(calculatePromotionFees(data));

	}

	@Override
	protected void postAdd(Map<String, Object> data, AbsoluteEntity entity) throws EntityException, ValidationException
	{
		Promotion promotion = (Promotion) entity;
		MessageCenter messageCenter = new MessageCenter();
		messageCenter.setAgent(promotion.getAgent());
		FunctionType functionType = em.viewRecordDetails(FunctionType.class, EntityConstants.FunctionType.Fixed.activatePromotionFunction.ID);
		messageCenter.setFunctionType(functionType);
		messageCenter.setMessageCenterItemId(promotion.getItemId());
		messageCenter.setRequestAgentEmp(promotion.getInsEmp());
		messageCenter.setRequestDate(new Date());
		messageCenter.setRequestMessage("activatePromotion");
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

		Number currencyId = ((Number) data.remove(EntityConstants.Promotion.currencyId));
		Currency currency = new Currency();
		currency.setCurrencyId(currencyId.shortValue());
		data.put(EntityConstants.Promotion.currency, currency);
		data.put(EntityConstants.Promotion.promotionFees, calculatePromotionFees(data));
		data.put(EntityConstants.Promotion.promotionDate, new Date((Long) data.get(EntityConstants.Promotion.promotionDate)));

		//
		String image = (String) data.remove(EntityConstants.Promotion.image);
		if (image != null)
			data.put(EntityConstants.Promotion.image, Base64.getDecoder().decode(image));
	}

	private float calculatePromotionFees(Map<String, Object> data)
	{
		return 0;
	}

	@Override
	public boolean isSynchronizationNeeded(long dateInTimeMillis) throws FacadeException
	{
		try
		{
			@SuppressWarnings("rawtypes")
			List lastUpdateDateList = em.executeNamedQuery(Promotion.lastUpdateDateNamedQuery, null, 1);
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
