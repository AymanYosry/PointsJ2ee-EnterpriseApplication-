package com.ewhale.points.web.managedbean.systemadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminPointBean extends AbsoluteBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long pointsId,profileId;

	private Date pointDate_from, pointDate_to;

	public Long getPointsId()
	{
		return pointsId;
	}

	public void setPointsId(Long pointsId)
	{
		this.pointsId = pointsId;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Date getPointDate_from()
	{
		return pointDate_from;
	}

	public void setPointDate_from(Date pointDate_from)
	{
		this.pointDate_from = pointDate_from;
	}

	public Date getPointDate_to()
	{
		return pointDate_to;
	}

	public void setPointDate_to(Date pointDate_to)
	{
		this.pointDate_to = pointDate_to;
	}

	public void userGainedPointsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Point.insertDate_From_Search, pointDate_from);
		data.put(EntityConstants.Point.insertDate_To_Search, pointDate_to);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> gainedPointsList = systemAdminServiceClient.userGainedPointsList("" + profileId, data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Point.pointsId, null },
					{ EntityConstants.Point.insertDate, null },
					{ EntityConstants.Point.pointsValue, null },
					{ EntityConstants.Point.sysEvent, EntityConstants.SysEvent.eventDesc } };
		populateTable(gainedPointsList, columnKeys);
	}

	public void userReleasedPointsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Point.insertDate_From_Search, pointDate_from);
		data.put(EntityConstants.Point.insertDate_To_Search, pointDate_to);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> releasedPointsList = systemAdminServiceClient.userReleasedPointsList("" + profileId, data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Point.pointsId, null },
					{ EntityConstants.Point.insertDate, null },
					{ EntityConstants.Point.pointsValue, null },
					{ EntityConstants.Point.sysEvent, EntityConstants.SysEvent.eventDesc } };
		populateTable(releasedPointsList, columnKeys);
	}

	public void userPointsList()
	{

		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Point.insertDate_From_Search, pointDate_from);
		data.put(EntityConstants.Point.insertDate_To_Search, pointDate_to);
		SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> pointsList = systemAdminServiceClient.userPointsList("" + profileId, data);

		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Point.insertDate, null },
					{ EntityConstants.Point.pointsValue, null },
					{ EntityConstants.Point.sysEvent, EntityConstants.SysEvent.eventDesc } };
		populateTable(pointsList, columnKeys);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void viewDetails(Map<String, Object> data, String selectedField)
	{
		if (selectedField.equals(EntityConstants.SysEvent.eventDesc))
		{
			Map<String, Object> eventData = (Map<String, Object>) data.get(EntityConstants.Point.sysEvent);
			Object targetId = data.get(EntityConstants.Point.pointsId);
			Map<String, Object> targetData = new HashMap<String, Object>();
			short eventId = ((Number) eventData.get(EntityConstants.SysEvent.sysEventId)).shortValue();
			if (eventId == EntityConstants.SysEvent.Fixed.fundPurchase.ID || eventId == EntityConstants.SysEvent.Fixed.refundPurchase.ID)
			{
				SysAdminPurchaseBean sysAdminPurchaseBean = FacesUtil.getObjectFromSession(SysAdminPurchaseBean.class, true);
				targetData.put(EntityConstants.Purchase.purchaseId, targetId);
				sysAdminPurchaseBean.viewDetails(targetData);
			}
			else if (eventId == EntityConstants.SysEvent.Fixed.productExchange.ID || eventId == EntityConstants.SysEvent.Fixed.productReExchange.ID)
			{
				SysAdminExchangeBean sysAdminExchangeBean = FacesUtil.getObjectFromSession(SysAdminExchangeBean.class, true);
				targetData.put(EntityConstants.PointsExchange.pointsExchangeId, targetId);
				sysAdminExchangeBean.viewDetails(targetData);
			}

		}
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanAdd(false);
		setCanUpdate(false);
		setHasMoreDetails(false);
	}

	@Override
	public String getRowStyleClass(Map<String, Object> data)
	{
		if (((Number) data.get(EntityConstants.Point.txnType)).byteValue() < 0)
			return "redRow";
		else
			return null;
	}

}
