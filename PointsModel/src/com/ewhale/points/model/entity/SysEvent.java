package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.model.entity.interfaces.InsertTracking;
import com.ewhale.points.model.entity.interfaces.UpdateTracking;

/**
 * The persistent class for the sys_event database table.
 */
@Entity
@Table(name = "sys_event")
@NamedQuery(name = "SysEvent.findAll", query = "SELECT s FROM SysEvent s")
public class SysEvent extends AbsoluteEntity implements Serializable, UpdateTracking, InsertTracking
{
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = new String[]
		{ EntityConstants.SysEvent.sysEventId };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sys_event_id", unique = true, nullable = false)
	private long sysEventId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "event_date", nullable = false)
	private Date eventDate;

	@Column(name = "event_desc", nullable = false, length = 200)
	private String eventDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date", nullable = true)
	private Date insertDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_upd_date")
	private Date lastUpdDate;

	@Column(nullable = false)
	private boolean periodic;

	@Column(name = "points_value", nullable = false)
	private int pointsValue;

	// bi-directional many-to-one association to Point
	@OneToMany(mappedBy = "sysEvent")
	private List<Point> points;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "ins_emp_id", nullable = false)
	private Profile insEmp;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "upd_emp_id")
	private Profile updateEmp;

	public SysEvent()
	{
	}

	public long getSysEventId()
	{
		return this.sysEventId;
	}

	public void setSysEventId(long sysEventId)
	{
		this.sysEventId = sysEventId;
	}

	public Date getEventDate()
	{
		return this.eventDate;
	}

	public void setEventDate(Date eventDate)
	{
		this.eventDate = eventDate;
	}

	public String getEventDesc()
	{
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc)
	{
		this.eventDesc = eventDesc;
	}

	public Date getInsertDate()
	{
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate)
	{
		this.insertDate = insertDate;
	}

	public Date getLastUpdDate()
	{
		return this.lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate)
	{
		this.lastUpdDate = lastUpdDate;
	}

	public boolean getPeriodic()
	{
		return this.periodic;
	}

	public void setPeriodic(boolean periodic)
	{
		this.periodic = periodic;
	}

	public int getPointsValue()
	{
		return this.pointsValue;
	}

	public void setPointsValue(int pointsValue)
	{
		this.pointsValue = pointsValue;
	}

	public List<Point> getPoints()
	{
		return this.points;
	}

	public void setPoints(List<Point> points)
	{
		this.points = points;
	}

	public Point addPoint(Point point)
	{
		getPoints().add(point);
		point.setSysEvent(this);

		return point;
	}

	public Point removePoint(Point point)
	{
		getPoints().remove(point);
		point.setSysEvent(null);

		return point;
	}

	public Profile getInsEmp()
	{
		return this.insEmp;
	}

	public void setInsEmp(Profile insEmp)
	{
		this.insEmp = insEmp;
	}

	public Profile getUpdateEmp()
	{
		return this.updateEmp;
	}

	public void setUpdateEmp(Profile updateEmp)
	{
		this.updateEmp = updateEmp;
	}

	@Override
	public Map<String, Object> transformToMap()
	{
		Map<String, Object> map = transformMainDataToMap();
		return map;
	}

	@Override
	public Map<String, Object> transformMainDataToMap()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(EntityConstants.SysEvent.sysEventId, sysEventId);
		map.put(EntityConstants.SysEvent.eventDesc, eventDesc);
		map.put(EntityConstants.SysEvent.pointsValue, pointsValue);
		map.put(EntityConstants.SysEvent.eventDate, getStringFromDate(eventDate));
		map.put(EntityConstants.SysEvent.periodic, periodic);
		map.put(EntityConstants.SysEvent.insertDate, getStringFromDate(insertDate));
		// map.put(EntityConstants.SysEvent.insEmpId, insEmp.getProfileId());
		// map.put(EntityConstants.SysEvent.updateEmpId, updateEmp != null ? updateEmp.getProfileId() : null);
		map.put(EntityConstants.SysEvent.lastUpdDate, getStringFromDate(lastUpdDate));
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);

		setEventDesc((String) data.get(EntityConstants.SysEvent.eventDesc));
		setPointsValue(((Number) data.get(EntityConstants.SysEvent.pointsValue)).intValue());
		setEventDate(getDateFromLong((Long) data.get(EntityConstants.SysEvent.eventDate)));
		setPeriodic((boolean) data.get(EntityConstants.SysEvent.periodic));
	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria) throws EntityException
	{
		List<Predicate> predicateList = new ArrayList<Predicate>();
		Number sysEventId = (Number) criteria.get(EntityConstants.SysEvent.sysEventId);
		if (sysEventId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.SysEvent.sysEventId), sysEventId.longValue()));
		Boolean periodicSearch = (Boolean) criteria.get(EntityConstants.SysEvent.periodic);
		if (periodicSearch != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.SysEvent.periodic), periodicSearch));
		Long eventDateFrom = (Long) criteria.get(EntityConstants.SysEvent.eventDate_From_Search);
		if (eventDateFrom != null)
			predicateList.add(criteriaBuilder.greaterThan(criteriaRoot.get(EntityConstants.SysEvent.eventDate), getDateFromLong(eventDateFrom)));
		Long eventDateTo = (Long) criteria.get(EntityConstants.SysEvent.eventDate_To_Search);
		if (eventDateTo != null)
			predicateList.add(criteriaBuilder.lessThan(criteriaRoot.get(EntityConstants.SysEvent.eventDate), getDateFromLong(eventDateTo)));
		Long eventDate = (Long) criteria.get(EntityConstants.SysEvent.eventDate);
		// get the events of the today which will be added to all the users
		// e.g Mother day 21-03
		// this includes periodic (compare using the month and day only without year)
		// and non periodic events (compare using the whole date)
		if (eventDate != null)
		{
			Calendar eventDateCal = Calendar.getInstance();
			eventDateCal.setTimeInMillis(eventDate);
			// reset time to zero
			eventDateCal.set(Calendar.MINUTE, 0);
			eventDateCal.set(Calendar.SECOND, 0);
			eventDateCal.set(Calendar.HOUR, 0);
			// create the condition of periodic and has the same month and day
			Predicate monthPredicate = criteriaBuilder.equal(
					criteriaBuilder.function("month", Integer.class, criteriaRoot.get(EntityConstants.SysEvent.eventDate)),
					eventDateCal.get(Calendar.MONTH) + 1);
			Predicate dayPredicate = criteriaBuilder.equal(
					criteriaBuilder.function("day", Integer.class, criteriaRoot.get(EntityConstants.SysEvent.eventDate)),
					eventDateCal.get(Calendar.DAY_OF_MONTH));
			Predicate periodicPredicate = criteriaBuilder.equal(criteriaRoot.get(EntityConstants.SysEvent.periodic), true);
			Predicate firstPart = criteriaBuilder.and(monthPredicate, dayPredicate, periodicPredicate);
			// create the condition of not periodic and has the same full date
			Predicate eventDatePredicate = criteriaBuilder.equal(criteriaRoot.get(EntityConstants.SysEvent.eventDate), eventDateCal.getTime());
			Predicate notPeriodicPredicate = criteriaBuilder.equal(criteriaRoot.get(EntityConstants.SysEvent.periodic), false);
			Predicate secondPart = criteriaBuilder.and(eventDatePredicate, notPeriodicPredicate);
			// the final condition
			predicateList.add(criteriaBuilder.or(firstPart, secondPart));
		}
		// if (insEmpId != null)
		// {
		// Join<SysEvent, Profile> sysEventInsProfileJoin = criteriaRoot.join(EntityConstants.SysEvent.insEmp);
		// predicateList.add(criteriaBuilder.equal(sysEventInsProfileJoin.get(EntityConstants.Profile.profileId), insEmpId.longValue()));
		// }

		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		setSysEventId(((Number) data.get(EntityConstants.SysEvent.sysEventId)).longValue());
		setInsertDate((Date) data.get(EntityConstants.SysEvent.insertDate));
	}

	@Override
	public Object getEntityId()
	{
		return getSysEventId();
	}
}
