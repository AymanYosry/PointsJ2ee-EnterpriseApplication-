package com.ewhale.points.controller.facade;

import java.math.BigDecimal;
import java.util.HashMap;
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
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Agent;
import com.ewhale.points.model.entity.Contract;
import com.ewhale.points.model.entity.Point;
import com.ewhale.points.model.entity.PointsExchange;
import com.ewhale.points.model.entity.Profile;
import com.ewhale.points.model.entity.Purchase;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class PointFacadeBean
 */
@Stateless
@Local(PointFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PointFacadeBean extends AbsoluteFacadeBean implements PointFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.POINT) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	@Override
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		FacadeBeanUtils.addInsEmpDetails(em, entity, data);
	}

	@Override
	public int getSumProfilePoints(long profileId) throws FacadeException
	{
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(EntityConstants.Point.profileId, profileId);
		try
		{
			@SuppressWarnings("rawtypes")
			List resultList = em.executeNamedQuery(Point.sumProfilePointsNamedQuery, parameters);
			BigDecimal sum = (BigDecimal) resultList.get(0);
			if (sum == null)
				return 0;
			return sum.intValue();
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}

	private void updatePointsValue(long pointsId, Profile profile, int points) throws FacadeException, EntityException
	{
		Map<String, Object> criteria = new HashMap<>();
		criteria.put(EntityConstants.Point.pointsId, pointsId);
		criteria.put(EntityConstants.Point.profile, profile);
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Point.pointsValue, points);
		update(data, criteria, 1);

	}

	@Override
	public void rejectPoints(long pointsId, long profileId) throws FacadeException
	{
		try
		{
			Profile profile = em.viewRecordDetails(Profile.class, profileId);
			updatePointsValue(pointsId, profile, 0);
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}

	@Override
	public int confirmPoints(long pointsId, long profileId) throws FacadeException, ValidationException
	{
		try
		{
			Point point = em.viewRecordDetails(Point.class, pointsId);
			long eventId = point.getSysEvent().getSysEventId();
			int points = 0;
			if (eventId == EntityConstants.SysEvent.Fixed.fundPurchase.ID || eventId == EntityConstants.SysEvent.Fixed.refundPurchase.ID)
			{
				Purchase purchase = em.viewRecordDetails(Purchase.class, pointsId);
				Agent agent = purchase.getAgent();
				Contract contract = agent.getContracts().get(0);
				points = FacadeBeanUtils.calculatePurchasePointsValue(purchase, contract);
				// in case of fund purchase update the agent invoice with the value agreed on
				if (eventId == EntityConstants.SysEvent.Fixed.fundPurchase.ID)
					updateSysInvoice(purchase, contract);

			}
			else if (eventId == EntityConstants.SysEvent.Fixed.productExchange.ID || eventId == EntityConstants.SysEvent.Fixed.productReExchange.ID)
			{
				PointsExchange pointsExchange = em.viewRecordDetails(PointsExchange.class, pointsId);
				points = FacadeBeanUtils.calculateExchangePointsValue(pointsExchange);
			}
			Profile profile = point.getPointsProfile();
			if (profileId != profile.getProfileId())
			{
				throw new ValidationException(ExceptionConstants.WRONG_MOBILE_EX_MSG);
			}
			updatePointsValue(pointsId, profile, points);
			return points;
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}

	@Override
	public Map<String, Object> confirmPoints(String mobile, String qrCode) throws FacadeException, ValidationException
	{
		try
		{
			String[] qrData = getQRCodeDetails(qrCode);
			String qrMobile = qrData[0];
			String qrPointsId = qrData[1];
			String qrInvoiceNo = qrData[2];
			String qrInvoiceValue = qrData[3];
			long pointsId = Long.parseLong(qrPointsId);

			Point point = em.viewRecordDetails(Point.class, pointsId);
			Profile profile = point.getPointsProfile();
			// check if the user scans his QRcode
			if (!qrMobile.equals(mobile) || !qrMobile.equals(profile.getMobile()))
			{
				throw new ValidationException(ExceptionConstants.WRONG_MOBILE_EX_MSG);
			}
			long eventId = point.getSysEvent().getSysEventId();
			int points = 0;
			Map<String, Object> transactionData=null; 
			if (eventId == EntityConstants.SysEvent.Fixed.fundPurchase.ID || eventId == EntityConstants.SysEvent.Fixed.refundPurchase.ID)
			{
				Purchase purchase = em.viewRecordDetails(Purchase.class, pointsId);
				// validate the QRCode
				if (purchase.getAgentInvoiceNumber() != Long.parseLong(qrInvoiceNo)
						|| purchase.getAgentInvoiceValue() != Float.parseFloat(qrInvoiceValue))
				{
					throw new ValidationException(ExceptionConstants.INVALID_QR_CODE_EX_MSG);
				}
				Contract contract = purchase.getAgent().getContracts().get(0);
				points = FacadeBeanUtils.calculatePurchasePointsValue(purchase, contract);
				// in case of fund purchase update the agent invoice with the value agreed on
				// IMP_Ahmed put the purchase in a map to block it till the update of invoice is done
				if (eventId == EntityConstants.SysEvent.Fixed.fundPurchase.ID)
					updateSysInvoice(purchase, contract);
				//set the points in the purchase and prepare it to be returned 
				purchase.setPointsValue(points);
				transactionData=purchase.transformToMap();
			}
			else if (eventId == EntityConstants.SysEvent.Fixed.productExchange.ID || eventId == EntityConstants.SysEvent.Fixed.productReExchange.ID)
			{
				PointsExchange pointsExchange = em.viewRecordDetails(PointsExchange.class, pointsId);
				points = FacadeBeanUtils.calculateExchangePointsValue(pointsExchange);
				//set the points in the pointsExchange and prepare it to be returned 
				pointsExchange.setPointsValue(points);
				transactionData=pointsExchange.transformToMap();
			}
			updatePointsValue(pointsId, profile, points);
			return transactionData;
		}
		catch (NumberFormatException | NullPointerException e)
		{
			throw new ValidationException(ExceptionConstants.INVALID_QR_CODE_EX_MSG);
		}
		catch (EntityException e)
		{
			throw new FacadeException(e);
		}
	}

	protected void updateSysInvoice(Purchase purchase, Contract contract) throws EntityException
	{
		SysInvoice invoice = FacadeBeanUtils.getAgentPendingInvoice(em, purchase.getAgent().getAgentId());
		float profit = FacadeBeanUtils.calculatePurchaseProfitValue(purchase, contract);
		invoice.setInvoiceValue(invoice.getInvoiceValue() + profit);
		em.updateRecord(invoice);
	}

	private String[] getQRCodeDetails(String qrCode)
	{
		String qrString = decodeQRCode(qrCode);
		String[] qrData = qrString.split("##");
		return qrData;
	}

	private String decodeQRCode(String qrCode)
	{
		// IMP_Ayman Implementation needed
		return qrCode;
	}
}
