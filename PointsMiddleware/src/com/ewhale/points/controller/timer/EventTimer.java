package com.ewhale.points.controller.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;

import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.TimerException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.controller.facade.PointFacade;
import com.ewhale.points.controller.facade.ProfileFacade;
import com.ewhale.points.controller.facade.SysEventFacade;

/**
 * @author Ayman Yosry
 *         Session Bean implementation class EventTimer
 */
@Singleton
@LocalBean
@Asynchronous
public class EventTimer
{
	@EJB
	ProfileFacade profileFacade;

	@EJB
	SysEventFacade eventFacade;

	@EJB
	PointFacade poitntsFacade;

	@SuppressWarnings("unused")
	@Schedule(hour = "00", minute = "00", second = "00")
	public void execute(Timer timer)
	{
		List<Map<String, Object>> users;
		try
		{
			users = getBirthdayEventUsers(new Date());
//			addEventPoints(users, EntityConstants.SysEvent.Fixed.birthDay.ID);

			// users = getXXXXXEventUsers();
			// addEventPoints(users);

			// users = getYYYYYEventUsers();
			// addEventPoints(users);

			// IMP_Ayman add the points to the users
			List<Map<String, Object>> eventsList = getTodayEvents();
			//System.out.println("eventsList of today :"+new Date()+" - ");
			//System.out.println(eventsList );
		}
		catch (TimerException e)
		{
			e.printStackTrace();
		}

		//System.out.println("Executing EventTimer ...");
		//System.out.println("Execution Time EventTimer : " + new Date());
		//System.out.println("____________________________________________");
	}

	/**
	 * @param users
	 * @param eventType
	 * @throws TimerException
	 */
	@SuppressWarnings("unused")
	private void addEventPoints(List<Map<String, Object>> users, long eventId) throws TimerException
	{
		// IMP_Ayman need to be revised
		boolean isPeriodic = false;
		int pointsValue = 0;
		try
		{
			Map<String, Object> eventDetails = eventFacade.viewDetails(eventId);
			isPeriodic = (Boolean) eventDetails.get(EntityConstants.SysEvent.periodic);
			pointsValue = ((Number) eventDetails.get(EntityConstants.SysEvent.pointsValue)).intValue();

			if (isPeriodic)
			{
				Map<String, Object> data = new HashMap<>();
				data.put(EntityConstants.Point.eventId, eventId);
				data.put(EntityConstants.Point.pointsValue, pointsValue);
				data.put(EntityConstants.Point.insertDate, new Date());
				poitntsFacade.add(data);
			}

		}
		catch (FacadeException | ValidationException e)
		{
			throw new TimerException(e);
		}
	}

	/**
	 * @param birthDate
	 * @return
	 * @throws TimerException
	 */
	private List<Map<String, Object>> getBirthdayEventUsers(Date birthDate) throws TimerException
	{

		Map<String, Object> criteria = new HashMap<>();
		criteria.put(EntityConstants.Profile.birthDate, birthDate.getTime());

		List<Map<String, Object>> list;
		try
		{
			list = profileFacade.viewList(criteria);
		}
		catch (FacadeException e)
		{
			throw new TimerException(e);
		}
		// dbLoginData.forEach((key, val) -> //System.out.println(" login -------------> " + key + " = " + val));
		return list;
	}

	private List<Map<String, Object>> getTodayEvents() throws TimerException
	{

		Map<String, Object> criteria = new HashMap<>();
		criteria.put(EntityConstants.SysEvent.eventDate, new Date().getTime());

		List<Map<String, Object>> list;
		try
		{

			list = eventFacade.viewList(criteria);
		}
		catch (FacadeException e)
		{
			throw new TimerException(e);
		}
		return list;
	}
}
