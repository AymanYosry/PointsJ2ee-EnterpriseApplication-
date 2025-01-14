/**
 * 
 */
package com.ewhale.points.controller.facade;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ValidationException;

/**
 * @author Ahmad Khlil
 */
public interface AbsoluteFacade extends Serializable
{
	Map<String, Object> add(Map<String, Object> data) throws FacadeException, ValidationException;

	Map<String, Object> update(Map<String, Object> data) throws FacadeException, ValidationException;

	int update(Map<String, Object> data, Map<String, Object> criteria, int expectedNoOfUpdatedRecords) throws FacadeException;

	Map<String, Object> delete(Number... id) throws FacadeException;

	Map<String, Object> viewDetails(Number... id) throws FacadeException;

	List<Map<String, Object>> viewList(Map<String, Object> criteria) throws FacadeException;

	List<Map<String, Object>> viewList(Map<String, Object> criteria, int maxResult) throws FacadeException;

	List<Map<String, Object>> viewAll() throws FacadeException;
}
