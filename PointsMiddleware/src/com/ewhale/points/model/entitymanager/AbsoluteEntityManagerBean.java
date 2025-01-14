/**
 * 
 */
package com.ewhale.points.model.entitymanager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;

/**
 * @author Ayman Yosry
 */
@Stateful
@Local(AbsoluteEntityManager.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AbsoluteEntityManagerBean implements AbsoluteEntityManager
{
	protected Logger LOG = Logger.getLogger(AbsoluteEntityManagerBean.class);

	/**
	 * 
	 */
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Resource
	private UserTransaction transaction;

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public <T extends AbsoluteEntity> T addRecord(T record) throws EntityException, ValidationException
	{
		try
		{
			em.persist(record);
		}
		catch (Exception e)
		{
			// get the root cause of the exception
			Throwable rootCause = e.getCause();
			while (rootCause != null)
			{
				Throwable cause = rootCause.getCause();
				if (cause == null)
					break;
				else
					rootCause = cause;
			}
			// check if exception is due to duplication data
			if (rootCause.getMessage().toLowerCase().startsWith("duplicate"))
			{
				LOG.debug("############ error data dublicated" + rootCause.getMessage());
				throw new ValidationException(ExceptionConstants.DUBLICATE_DATA_EX_MSG);
			}
			throw new EntityException(e.getClass().getName() + "==> Can not add new record in table :" + record.getClass().getSimpleName(), e);
		}
		return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public <T extends AbsoluteEntity> T updateRecord(T record) throws EntityException
	{
		try
		{
			// find the object first because merge will insert if it is not present
			T managedRecord = (T) em.find(record.getClass(), record.getEntityId());
			if (managedRecord == null)
				throw new EntityException("wrong ID");
			record = em.merge(record);
		}
		catch (Exception e)
		{
			throw new EntityException(e.getClass().getName() + "==> Can not update record in table :" + record.getClass().getSimpleName(), e);
		}
		return record;
	}

	@Override
	public <T extends AbsoluteEntity> int updateRecord(Class<T> entityClass, Map<String, Object> dataToUpdate, Map<String, Object> criteria)
			throws EntityException
	{
		Query query = null;
		try
		{
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			final CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entityClass);
			final Root<T> criteriaRoot = criteriaUpdate.from(entityClass);
			for (Map.Entry<String, Object> fieldToUpdate : dataToUpdate.entrySet())
			{
				LOG.debug("field to update : " + fieldToUpdate.getKey() + " -- " + fieldToUpdate.getValue());
				criteriaUpdate.set(fieldToUpdate.getKey(), fieldToUpdate.getValue());
			}
			AbsoluteEntity entity = entityClass.newInstance();
			List<Predicate> criteriaList = entity.setCriteria(criteriaBuilder, criteriaRoot, criteria);

			criteriaUpdate.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
			query = em.createQuery(criteriaUpdate);
			int noOfRecordsUpdated = query.executeUpdate();

			return noOfRecordsUpdated;
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e)
		{
			throw new EntityException(e.getClass().getName() + "==> Unexpected error while updating record in table :" + entityClass.getSimpleName(),
					e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public <T extends AbsoluteEntity> T deleteRecord(Class<T> entityClass, Object id) throws EntityException
	{
		T record = null;
		try
		{
			record = viewRecordDetails(entityClass, id);
			em.remove(record);
		}
		catch (Exception e)
		{
			throw new EntityException(
					e.getClass().getName() + "==> Can not delete record from table :" + entityClass.getSimpleName() + e.getStackTrace());
		}
		return record;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbsoluteEntity> List<T> viewAllRecords(Class<T> entityClass) throws EntityException
	{
		List<T> recordList = null;
		try
		{
			Query namedQuery = em.createNamedQuery(entityClass.getSimpleName() + ".findAll");
			recordList = namedQuery.getResultList();
		}
		catch (Exception e)
		{
			throw new EntityException(e.getClass().getName() + "==> Unexpected error while listing all records from table :"
					+ entityClass.getSimpleName() + e.getStackTrace());
		}

		return recordList;
	}

	@Override
	public <T extends AbsoluteEntity> List<T> viewRecordList(Class<T> entityClass, Map<String, Object> criteria) throws EntityException
	{
		TypedQuery<T> query = getTypedQuery(entityClass, criteria);
		return query.getResultList();
	}

	@Override
	public <T extends AbsoluteEntity> List<T> viewRecordList(Class<T> entityClass, Map<String, Object> criteria, int maxResult) throws EntityException
	{
		TypedQuery<T> query = getTypedQuery(entityClass, criteria);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	private <T extends AbsoluteEntity> TypedQuery<T> getTypedQuery(Class<T> entityClass, Map<String, Object> criteria) throws EntityException
	{
		TypedQuery<T> query = null;
		try
		{
			final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			final Root<T> criteriaRoot = criteriaQuery.from(entityClass);
			AbsoluteEntity entity = entityClass.newInstance();
			List<Predicate> criteriaList = entity.setCriteria(criteriaBuilder, criteriaQuery, criteriaRoot, criteria);

			criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
			criteriaQuery.distinct(true);
			Order[] order = entity.getDefaultOrderFields(criteriaBuilder, criteriaRoot);
			if (order != null && order.length > 0)
				criteriaQuery.orderBy(order);
			query = em.createQuery(criteriaQuery);
			return query;
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e)
		{
			throw new EntityException(
					e.getClass().getName() + "==> Unexpected error while listing records from table :" + entityClass.getSimpleName(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeNamedQuery(String namedQuery, Map<String, Object> parameters) throws EntityException
	{
		Query namedNativeQuery = em.createNamedQuery(namedQuery);
		for (Map.Entry<String, Object> parameter : parameters.entrySet())
		{
			namedNativeQuery.setParameter(parameter.getKey(), parameter.getValue());
		}
		List resultList = namedNativeQuery.getResultList();
		return resultList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List executeNamedQuery(String namedQuery, Map<String, Object> parameters, int maxResult) throws EntityException
	{
		Query namedNativeQuery = em.createNamedQuery(namedQuery);
		if (parameters != null)
			for (Map.Entry<String, Object> parameter : parameters.entrySet())
			{
				namedNativeQuery.setParameter(parameter.getKey(), parameter.getValue());
			}
		List resultList = namedNativeQuery.setMaxResults(maxResult).getResultList();
		return resultList;
	}

	@Override
	public int executeNamedNativeUpdate(String namedNativeUpdate, Map<String, Object> parameters) throws EntityException
	{
		Query namedNativeUpdateQuery = em.createNamedQuery(namedNativeUpdate);
		for (Map.Entry<String, Object> parameter : parameters.entrySet())
		{
			namedNativeUpdateQuery.setParameter(parameter.getKey(), parameter.getValue());
		}
		int rowsUpdated = namedNativeUpdateQuery.executeUpdate();
		return rowsUpdated;
	}

	@Override
	public <T extends AbsoluteEntity> T viewRecordDetails(Class<T> entityClass, Object id) throws EntityException
	{
		T record = null;
		try
		{
			record = em.find(entityClass, id);
		}
		catch (Exception e)
		{
			throw new EntityException(
					e.getClass().getName() + "==> Unexpected error while view record details from table :" + entityClass.getSimpleName(), e);
		}
		return record;
	}

	@Override
	public boolean begin() throws EntityException
	{
		boolean status = false;
		try
		{
			transaction.begin();
			status = true;
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			status = false;
			throw new EntityException(e.getClass().getName() + "==> Can't begin transaction " + e.getStackTrace());
		}
		return status;
	}

	@Override
	public boolean commit() throws EntityException
	{
		boolean status = false;
		try
		{
			transaction.commit();
			status = true;
		}
		catch (Exception e)
		{
			status = false;
			throw new EntityException(e.getClass().getName() + "==> Can't commit transaction " + e.getStackTrace());
		}
		return status;
	}

	@Override
	public boolean rollback() throws EntityException
	{
		boolean status = false;
		try
		{
			transaction.rollback();
			status = true;
		}
		catch (Exception e)
		{
			status = false;
			throw new EntityException(e.getClass().getName() + "==> Can't commit transaction " + e.getStackTrace());
		}
		return status;
	}
}
