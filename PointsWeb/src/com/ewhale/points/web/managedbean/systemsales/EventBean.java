package com.ewhale.points.web.managedbean.systemsales;

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
import com.ewhale.points.ws.system.proxy.SystemSalesServiceClient;

@SessionScoped
@ManagedBean
public class EventBean extends AbsoluteBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long sysEventId;

	private Date eventDate;

	private String eventDesc;

	private Date lastUpdDate = new Date();

	private Boolean periodic;

	private Boolean periodicSearch;

	private Integer pointsValue;

	private Date eventDateFrom;

	private Date eventDateTo;

	public EventBean()
	{

	}

	public Long getSysEventId()
	{
		return sysEventId;
	}

	public void setSysEventId(Long sysEventId)
	{
		this.sysEventId = sysEventId;
	}

	public Date getEventDate()
	{
		return eventDate;
	}

	public void setEventDate(Date eventDate)
	{
		this.eventDate = eventDate;
	}

	public String getEventDesc()
	{
		return eventDesc;
	}

	public void setEventDesc(String eventDesc)
	{
		this.eventDesc = eventDesc;
	}

	public Date getLastUpdDate()
	{
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate)
	{
		this.lastUpdDate = lastUpdDate;
	}

	public Boolean getPeriodic()
	{
		return periodic;
	}

	public void setPeriodic(Boolean periodic)
	{
		this.periodic = periodic;
	}

	public Integer getPointsValue()
	{
		return pointsValue;
	}

	public void setPointsValue(Integer pointsValue)
	{
		this.pointsValue = pointsValue;
	}

	public Date getEventDateFrom()
	{
		return eventDateFrom;
	}

	public void setEventDateFrom(Date eventDateFrom)
	{
		this.eventDateFrom = eventDateFrom;
	}

	public Date getEventDateTo()
	{
		return eventDateTo;
	}

	public void setEventDateTo(Date eventDateTo)
	{
		this.eventDateTo = eventDateTo;
	}

	public Boolean getPeriodicSearch()
	{
		return periodicSearch;
	}

	public void setPeriodicSearch(Boolean periodicSearch)
	{
		this.periodicSearch = periodicSearch;
	}

	@Override
	protected String getDetailsPageName()
	{
		return "event_details.xhtml";
	}

	@Override
	protected String getUpdatePageName()
	{
		return "event_details.xhtml";
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> eventData = systemSalesServiceClient.eventDetails(data.get(EntityConstants.SysEvent.sysEventId).toString());
		sysEventId = ((Number) eventData.get(EntityConstants.SysEvent.sysEventId)).longValue();
		eventDate = FacesUtil.getDateFromString((String) eventData.get(EntityConstants.SysEvent.eventDate));
		eventDesc = (String) eventData.get(EntityConstants.SysEvent.eventDesc);
		periodic = (boolean) eventData.get(EntityConstants.SysEvent.periodic);
		pointsValue = ((Number) eventData.get(EntityConstants.SysEvent.pointsValue)).intValue();
	}

	public void viewEventsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysEvent.eventDate_From_Search, eventDateFrom);
		data.put(EntityConstants.SysEvent.eventDate_To_Search, eventDateTo);
		data.put(EntityConstants.SysEvent.periodic, periodicSearch);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> eventsList = systemSalesServiceClient.viewEventsList(data);
		populateTable(eventsList, EntityConstants.SysEvent.eventDesc, EntityConstants.SysEvent.eventDate, EntityConstants.SysEvent.pointsValue,
				EntityConstants.SysEvent.periodic);
	}

	public void addEvent()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysEvent.eventDate, eventDate);
		data.put(EntityConstants.SysEvent.eventDesc, eventDesc);
		data.put(EntityConstants.SysEvent.lastUpdDate, lastUpdDate);
		data.put(EntityConstants.SysEvent.periodic, periodic);
		data.put(EntityConstants.SysEvent.pointsValue, pointsValue);
		// IMP_Badawy add insEmpId and to data Map.
		// all insEmpId and UpdateEmpId are the login user
		// so this should be
		data.put(EntityConstants.SysEvent.insEmpId, FacesUtil.getLoginId());
		// not data.put(EntityConstants.SysEvent.insEmpId, 1);
		////////////////

		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.addSysEvent(data);

	}

	public void updateEvent()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysEvent.sysEventId, sysEventId);
		data.put(EntityConstants.SysEvent.eventDate, eventDate);
		data.put(EntityConstants.SysEvent.eventDesc, eventDesc);
		data.put(EntityConstants.SysEvent.periodic, periodic);
		data.put(EntityConstants.SysEvent.pointsValue, pointsValue);
		// IMP_Badawy add updateEmpId and to data Map
		// all insEmpId and UpdateEmpId are the login user
		// so this should be
		data.put(EntityConstants.SysEvent.updateEmpId, FacesUtil.getLoginId());
		// not data.put(EntityConstants.SysEvent.updateEmpId, 1);
		////////////////

		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.updateSysEvent(data);

	}

	public void deleteEvent()
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.deleteSysEvent(sysEventId + "");
	}

	@SuppressWarnings("unused")
	public void eventDetails()
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.SysEvent.sysEventId, sysEventId);
		//IMP_Ahmed what is the use of eventDetails
		Map<String, Object> eventDetails = systemSalesServiceClient.eventDetails(sysEventId + "");
	}

	@Override
	protected void resetToAdd()
	{
		setSysEventId(null);
		setEventDate(null);
		setEventDesc(null);
		setPeriodic(null);
		setPointsValue(null);
	}

}
