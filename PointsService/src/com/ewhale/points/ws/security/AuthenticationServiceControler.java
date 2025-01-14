package com.ewhale.points.ws.security;

import java.util.Map;

import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.controller.facade.AuthenticationFacade;
import com.ewhale.points.ws.main.ServiceControler;

/**
 * @author Ayman Yosry
 */
public class AuthenticationServiceControler extends ServiceControler
{
	/**
	 * 
	 * @param loginFacade
	 * @param loginData
	 * @return
	 * @throws FacadeException
	 * @throws ValidationException
	 */
	public Map<String, Object> login(AuthenticationFacade loginFacade, Map<String, Object> loginData) throws FacadeException, ValidationException
	{
		return loginFacade.login(loginData);
	}

	/**
	 * 
	 * @param loginFacade
	 * @param data
	 * @return
	 * @throws FacadeException
	 * @throws ValidationException
	 */
	public Map<String, Object> changePassword(AuthenticationFacade loginFacade, Map<String, Object> data) throws FacadeException, ValidationException
	{
		Map<String, Object> loginData = loginFacade.update(data);
		if(loginData != null)
		{
			loginData = login(loginFacade, loginData);
		}
		return loginData;
	}

	/**
	 * 
	 * @param loginFacade
	 * @param mobile
	 * @throws FacadeException
	 */
	public void resetPassword(AuthenticationFacade loginFacade, String mobile) throws FacadeException
	{
		loginFacade.resetPassword(mobile);
	}

	/**
	 * 
	 * @param loginFacade
	 * @param data
	 * @return
	 * @throws FacadeException
	 * @throws ValidationException
	 */
	public Map<String, Object> validateUser(AuthenticationFacade loginFacade, Map<String, Object> data) throws FacadeException, ValidationException
	{
		return loginFacade.validateUser(data);
	}
	
	/**
	 * 
	 * @param loginFacade
	 * @param mobile
	 * @throws FacadeException
	 */
	public void logout(AuthenticationFacade loginFacade, String token) throws FacadeException
	{
		loginFacade.logout(token);
	}
}