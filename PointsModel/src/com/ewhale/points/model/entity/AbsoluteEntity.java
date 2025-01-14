/**
 * 
 */
package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 */
public abstract class AbsoluteEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = null;

	/**
	 * 
	 */
	public AbsoluteEntity()
	{
		// LOG.debug("Inside AbsoluteEntity");
	}

	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<T> criteriaQuery,
			final Root<T> criteriaRoot, Map<String, Object> criteria) throws EntityException
	{
		return setCriteria(criteriaBuilder, criteriaRoot, criteria);
	}

	public abstract <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria) throws EntityException;

	public abstract Map<String, Object> transformToMap();

	public abstract Map<String, Object> transformMainDataToMap();

	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		if (data != null)
		{
			if (useId)
				setEntityId(data);
		}
		else
		{
			throw new EntityException("entityData is empty (NULL)");
		}
	}

	public void setEntityData(Map<String, Object> data) throws EntityException
	{
		setEntityData(data, false);
	}

	protected abstract void setEntityId(Map<String, Object> data);

	public static Date getDateFromLong(long date) throws EntityException
	{
		return new Date(date);
	}

	// public static Date getDateFromString(String dateStr) throws EntityException
	// {
	// if (dateStr == null)
	// return null;
	// try
	// {
	// return dateFormat.parse(dateStr);
	// }
	// catch (ParseException e)
	// {
	// throw new EntityException("Wrong date format, Expected 'yyyy-MM-dd hh:mm'");
	// }
	// }
	//

	public static String getStringFromDate(Date date)
	{
		if (date == null)
			return null;
		return AppConstants.dateTimeFormat.format(date);
	}

	public Object getEntityId()
	{
		return null;
	}

	public void setEntityId(Object idObj)
	{
	}

	public <T extends AbsoluteEntity> Order[] getDefaultOrderFields(CriteriaBuilder criteriaBuilder, Root<T> criteriaRoot)
	{
		return null;
	}

}
