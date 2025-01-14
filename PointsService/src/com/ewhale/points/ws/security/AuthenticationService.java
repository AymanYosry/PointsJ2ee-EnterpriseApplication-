/**
 * 
 */
package com.ewhale.points.ws.security;

import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.logging.Logger;

import com.ewhale.points.common.annotations.VerifyDigitaSignature;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ServiceException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.controller.facade.AuthenticationFacade;
import com.ewhale.points.ws.main.ServiceHeading;

/**
 * @author Ayman Yosry
 */
@Path("/Profile")
@Produces(ServiceHeading.MEDIA_TYPE)
public class AuthenticationService implements ServiceHeading
{
	private AuthenticationServiceControler controler = new AuthenticationServiceControler();
	protected Logger LOG = Logger.getLogger(AuthenticationService.class);

	@EJB
	AuthenticationFacade loginFacade;

	@POST
	@Path("/login")
	@VerifyDigitaSignature
	public Map<String, Object> login(Map<String, Object> loginData)
	{
		Map<String, Object> result = null;
		try
		{
			result = controler.login(loginFacade, loginData);
		}
		catch (FacadeException e)
		{
			result = null;
			LOG.error("Problem While login ", e);
			e.printStackTrace();
			throw new ServiceException("Problem While login ", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			LOG.debug("Problem While Validation ", e);
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}

		return result;
	}

	@POST
	@Path("/changePassword")
	@VerifyDigitaSignature
	public Map<String, Object> changePassword(Map<String, Object> loginData)
	{
		Map<String, Object> result = null;
		try
		{
			result = controler.changePassword(loginFacade, loginData);
		}
		catch (FacadeException e)
		{
			result = null;
			LOG.error("Problem in change password ", e);
			e.printStackTrace();
			throw new ServiceException("Problem While change password", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			LOG.error("Problem While Validation ", e);
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}

		return result;
	}

	@POST
	@Path("/resetPassword")
	@VerifyDigitaSignature
	public void resetPassword(String mobile)
	{
		try
		{
			controler.resetPassword(loginFacade, mobile);
		}
		catch (FacadeException e)
		{
			LOG.error("Problem While Resetting Password ", e);
			e.printStackTrace();
			throw new ServiceException("Problem While Resetting Password ", e.getStatusCode());
		}
	}

	@POST
	@Path("/validateUser")
	@VerifyDigitaSignature
	public Map<String, Object> validateUser(Map<String, Object> userData)
	{
		Map<String, Object> result = null;
		try
		{
			result = controler.validateUser(loginFacade, userData);
		}
		catch (FacadeException e)
		{
			result = null;
			LOG.error("Problem in Validate User ", e);
			e.printStackTrace();
			throw new ServiceException("Problem in Validate User ", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			LOG.error("Problem While Validation ", e);
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}

		return result;
	}

	@POST
	@Path("/logout")
	@VerifyDigitaSignature
	public void logout(String token)
	{
		try
		{
			controler.logout(loginFacade, token);
		}
		catch (FacadeException e)
		{
			LOG.error("Problem While Logging out ", e);
			e.printStackTrace();
			throw new ServiceException("Problem While Logging out ", e.getStatusCode());
		}
	}
}
