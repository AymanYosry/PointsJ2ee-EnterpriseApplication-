package com.ewhale.points.controller.facade;

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
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Branch;
import com.ewhale.points.model.entity.Currency;
import com.ewhale.points.model.entity.PointsExchange;
import com.ewhale.points.model.entity.Product;
import com.ewhale.points.model.entity.Profile;
import com.ewhale.points.model.entity.SysEvent;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class PointsExchangeFacadeBean
 */
@Stateless
@Local(PointsExchangeFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PointsExchangeFacadeBean extends AbsoluteFacadeBean implements PointsExchangeFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PointFacade pointFacade;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.POINTEXCHANGE) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException, ValidationException
	{

		FacadeBeanUtils.addInsEmpDetails(em, entity, data);
		Map<String, Object> profileSeData = new HashMap<>();
		profileSeData.put(EntityConstants.Profile.mobile, data.get(EntityConstants.PointsExchange.profile_mobile));
		List<Profile> profileList = em.viewRecordList(Profile.class, profileSeData);
		if (profileList.size() == 0)
			throw new ValidationException(ExceptionConstants.WRONG_MOBILE_EX_MSG);
		Profile profile = profileList.get(0);

		boolean isExchange = ((boolean) data.get(EntityConstants.PointsExchange.exchange));

		Branch branch = em.viewRecordDetails(Branch.class, ((Number) data.get(EntityConstants.PointsExchange.branchId)).longValue());
		if (branch == null)
			throw new EntityException("wrong Branch id");
		Product product = em.viewRecordDetails(Product.class, ((Number) data.get(EntityConstants.PointsExchange.productId)).longValue());
		Currency currency = product.getCurrency();
		PointsExchange pointsExchange = (PointsExchange) entity;
		pointsExchange.setProduct(product);
		pointsExchange.setCurrency(currency);
		float priceValue = product.getPrice();
		pointsExchange.setPriceValue(priceValue);
		int points = FacadeBeanUtils.calculateExchangePointsValue(pointsExchange);
		if (isExchange)
		{
			try
			{
				int userPoints = pointFacade.getSumProfilePoints(profile.getProfileId());
				if (userPoints < points)
					throw new ValidationException(ExceptionConstants.POINTS_NOT_AVAILABLE_EX_MSG);
			}
			catch (FacadeException e)
			{
				throw new EntityException(e);
			}

		}

		// auto retrieved values
		long eventId = isExchange ? EntityConstants.SysEvent.Fixed.productExchange.ID : EntityConstants.SysEvent.Fixed.productReExchange.ID;
		SysEvent event = em.viewRecordDetails(SysEvent.class, eventId);
		SysInvoice invoice = FacadeBeanUtils.getAgentPendingInvoice(em, product.getAgent().getAgentId());

		pointsExchange.setBranch(branch);
		pointsExchange.setAgent(branch.getAgent());
		pointsExchange.setProfile(profile);
		pointsExchange.setSysEvent(event);
		pointsExchange.setSysInvoice(invoice);
		pointsExchange.setPointsValue(points);

		// update agent invoice
		updateSysInvoice(product, isExchange);
	}

	protected void updateSysInvoice(Product product, boolean isExchange) throws EntityException
	{
		SysInvoice invoice = FacadeBeanUtils.getAgentPendingInvoice(em, product.getAgent().getAgentId());
		float price = product.getPrice();
		// if exchange subtract the price , else add the price
		invoice.setInvoiceValue(invoice.getInvoiceValue() - (isExchange ? price : price * -1));
		em.updateRecord(invoice);
	}

	@Override
	public boolean isSynchronizationNeeded(long dateInTimeMillis, long profileId) throws FacadeException
	{
		try
		{
			long lastUpdateTimeInMillis = 0;
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("profileId", profileId);
			@SuppressWarnings("rawtypes")
			List lastUpdateDateList = em.executeNamedQuery(PointsExchange.lastUpdateDateNamedQuery, parameters, 1);
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

	@Override
	public Map<String, Object> update(Map<String, Object> data) throws FacadeException
	{
		return null;
	}

	@Override
	public int update(Map<String, Object> data, Map<String, Object> criteria, int expectedNoOfUpdatedRecords) throws FacadeException
	{
		return 0;
	}
}
