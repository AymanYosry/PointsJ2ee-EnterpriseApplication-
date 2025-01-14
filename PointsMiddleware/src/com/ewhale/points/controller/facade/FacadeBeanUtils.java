package com.ewhale.points.controller.facade;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.security.SecurityBuilder;
import com.ewhale.points.common.stores.StatusCodeStore;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Contract;
import com.ewhale.points.model.entity.ItemStatus;
import com.ewhale.points.model.entity.PointsExchange;
import com.ewhale.points.model.entity.Profile;
import com.ewhale.points.model.entity.Purchase;
import com.ewhale.points.model.entity.Status;
import com.ewhale.points.model.entity.SysInvoice;
import com.ewhale.points.model.entity.interfaces.InsertTracking;
import com.ewhale.points.model.entitymanager.AbsoluteEntityManager;

class FacadeBeanUtils
{

	protected static void addStatusDetails(AbsoluteEntityManager em, AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		Number statusIdObj = ((Number) data.get(EntityConstants.Status.statusId));
		short statusId = statusIdObj != null ? statusIdObj.shortValue() : EntityConstants.Status.Fixed.pendingStatus.ID;
		Status status = em.viewRecordDetails(Status.class, statusId);
		((ItemStatus) entity).setStatus(status);
		((ItemStatus) entity).setStatusDate(new Date());
		// add update employee
		Object updEmpId = data.get(EntityConstants.ItemStatus.updateStatusEmpId);
		if (updEmpId != null)
		{
			Profile updateEmp = em.viewRecordDetails(Profile.class, ((Number) updEmpId).longValue());
			((ItemStatus) entity).setUpdateStatusEmp(updateEmp);
		}
	}

	protected static void addInsEmpDetails(AbsoluteEntityManager em, AbsoluteEntity entity, Map<String, Object> data) throws EntityException
	{
		Profile insEmp = em.viewRecordDetails(Profile.class, ((Number) data.get(EntityConstants.ChangeTracking.insEmpId)).longValue());
		((InsertTracking) entity).setInsEmp(insEmp);
		((InsertTracking) entity).setInsertDate(new Date());
		// to gurantee the status emp id to be the same as ins emp id
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, data.get(EntityConstants.ChangeTracking.insEmpId));
	}

	protected static void addUpEmpDetails(Map<String, Object> data)
	{
		long updateEmpId = ((Number) data.remove(EntityConstants.ChangeTracking.updateEmpId)).longValue();
		Profile updateEmp = new Profile();
		updateEmp.setProfileId(updateEmpId);
		updateEmp.setItemId(updateEmpId);
		data.put(EntityConstants.ChangeTracking.updateEmp, updateEmp);
		data.put(EntityConstants.ChangeTracking.lastUpdDate, new Date());
	}

	protected static SysInvoice getAgentPendingInvoice(AbsoluteEntityManager em, long agentId) throws EntityException
	{
		Map<String, Object> map = new HashMap<>();
		map.put(EntityConstants.SysInvoice.agentId, agentId);
		map.put(EntityConstants.SysInvoice.active, true);
		List<SysInvoice> invoices = em.viewRecordList(SysInvoice.class, map);
		return invoices.get(0);
	}

	protected static void storeStatusCode(String mobile, String statusCode)
	{
		StatusCodeStore.StatusCodeMap.put(mobile, statusCode);
	}

	protected static String generateStatusCode() throws AuthenticationSecurityException
	{
		String statusCode = SecurityBuilder.getRandomNumber(5);
		return statusCode;
	}

	protected static String prepareSMS(String... smsData)
	{
		String smsMessage = "";
		for (String info : smsData)
		{
			smsMessage += info + "\n";
		}
		return smsMessage;
	}

	protected static void sendSMS(String mobile, String smsMessage) throws Exception
	{
		// IMP_Ayman send SMS tool
		writeToFile(smsMessage);
	}

	private static void writeToFile(String smsMessage) throws IOException
	{
//		String fileName = "sms.txt";
//		String filePath = "D:/";
//		String linesData[] = smsMessage.split("\n");
//		List<String> lines = Arrays.asList(linesData);
//
//		Path file = Paths.get(filePath + fileName);
//		Files.write(file, lines, Charset.forName(AppConstants.SecurityConstants.ENCODE_TYPE_8));
		
		System.out.println(" ---------------------------------------");
		System.out.println(smsMessage);
		System.out.println("----------------------------------------");
	}
	public static float calculatePurchaseProfitValue(Purchase purchase, Contract contract)
	{
		float profit = (purchase.getAgentInvoiceValue() * (contract.getProfitPercent() / 100));
		return profit;
	}
	public static int calculatePurchasePointsValue(Purchase purchase, Contract contract)
	{
		int points = (int) ((purchase.getAgentInvoiceValue() * (contract.getPointsPercent() / 100)) / purchase.getCurrency().getPointsValue());
		return points;
	}

	public static int calculateExchangePointsValue(PointsExchange pointsExchange)
	{
		int points = (int) (pointsExchange.getPriceValue() / pointsExchange.getCurrency().getPointsValue());
		return points;
	}
}
