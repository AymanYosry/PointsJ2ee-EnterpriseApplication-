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
import javax.validation.ValidationException;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Agent;
import com.ewhale.points.model.entity.Branch;
import com.ewhale.points.model.entity.Contract;
import com.ewhale.points.model.entity.Currency;
import com.ewhale.points.model.entity.Profile;
import com.ewhale.points.model.entity.Purchase;
import com.ewhale.points.model.entity.SysEvent;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class PurchaseFacadeBean
 */
@Stateless
@Local(PurchaseFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PurchaseFacadeBean extends AbsoluteFacadeBean implements PurchaseFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PointFacade pointFacade;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.PURCHASE) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		FacadeBeanUtils.addInsEmpDetails(em, entity, data);
		Map<String, Object> profileSeData = new HashMap<>();
		profileSeData.put(EntityConstants.Profile.mobile, data.get(EntityConstants.Purchase.profile_mobile));
		List<Profile> profileList = em.viewRecordList(Profile.class, profileSeData);
		if (profileList.size() == 0)
			throw new EntityException("wrong mobile no");
		Profile profile = profileList.get(0);
		boolean isFund = ((boolean) data.get(EntityConstants.Purchase.fund));
		Branch branch = em.viewRecordDetails(Branch.class, ((Number) data.get(EntityConstants.Purchase.branchId)).longValue());
		Agent agent = branch.getAgent();
		Contract contract = agent.getContracts().get(0);
		float discPercent = contract.getDiscountPercent();

		Currency currency = profile.getCountry().getCurrency();
		Purchase purchase = (Purchase) entity;
		purchase.setCurrency(currency);
		int points = FacadeBeanUtils.calculatePurchasePointsValue(purchase, contract);
		if (!isFund)
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
		long eventId = isFund ? EntityConstants.SysEvent.Fixed.fundPurchase.ID : EntityConstants.SysEvent.Fixed.refundPurchase.ID;
		SysEvent event = em.viewRecordDetails(SysEvent.class, eventId);
		SysInvoice invoice = FacadeBeanUtils.getAgentPendingInvoice(em, agent.getAgentId());
		purchase.setAgent(agent);
		purchase.setBranch(branch);
		purchase.setSysEvent(event);
		purchase.setSysInvoice(invoice);
		purchase.setProfile(profile);
		purchase.setDiscPercent(discPercent);
		float mainValue = purchase.getAgentInvoiceValue() * 100 / discPercent;
		purchase.setProfitValue(mainValue * discPercent / 100);
		// the points will be added when the user confirm the purchase
		// IMP_Ayman need to be discussed if the purchase is refund the points will be confirmed by default but not if rejected
		purchase.setPointsValue(isFund ? 0 : points);
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
			List lastUpdateDateList = em.executeNamedQuery(Purchase.lastUpdateDateNamedQuery, parameters, 1);
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
