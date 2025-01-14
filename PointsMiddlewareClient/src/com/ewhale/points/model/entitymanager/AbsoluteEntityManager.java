package com.ewhale.points.model.entitymanager;

import java.util.List;
import java.util.Map;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.model.entity.AbsoluteEntity;

/**
 * @author Ayman Yosry
 */

public interface AbsoluteEntityManager
{
	public <T extends AbsoluteEntity> T addRecord(T record) throws EntityException, ValidationException;

	public <T extends AbsoluteEntity> T updateRecord(T record) throws EntityException;

	public <T extends AbsoluteEntity> int updateRecord(Class<T> entityClass, Map<String, Object> dataToUpdate, Map<String, Object> criteria)
			throws EntityException;

	public <T extends AbsoluteEntity> T deleteRecord(Class<T> entityClass, Object id) throws EntityException;

	public <T extends AbsoluteEntity> List<T> viewAllRecords(Class<T> entityClass) throws EntityException;

	public <T extends AbsoluteEntity> List<T> viewRecordList(Class<T> entityClass, Map<String, Object> criteria) throws EntityException;

	public <T extends AbsoluteEntity> List<T> viewRecordList(Class<T> entityClass, Map<String, Object> criteria, int maxResult)
			throws EntityException;
	
	@SuppressWarnings("rawtypes")
	public List executeNamedQuery(String namedQuery, Map<String, Object> parameters) throws EntityException;

	@SuppressWarnings("rawtypes")
	public List executeNamedQuery(String namedQuery, Map<String, Object> parameters, int maxResult) throws EntityException;

	public int executeNamedNativeUpdate(String namedNativeUpdate, Map<String, Object> parameters) throws EntityException;

	public <T extends AbsoluteEntity> T viewRecordDetails(Class<T> entityClass, Object id) throws EntityException;

	public boolean commit() throws EntityException;

	boolean begin() throws EntityException;

	boolean rollback() throws EntityException;
}
