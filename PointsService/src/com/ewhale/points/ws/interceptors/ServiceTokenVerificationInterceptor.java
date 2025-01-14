/**
 * 
 */
package com.ewhale.points.ws.interceptors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.logging.Logger;

import com.ewhale.points.common.annotations.VerifyToken;
import com.ewhale.points.common.stores.TokentStore;
import com.ewhale.points.common.util.AbortResponses;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 */
@Provider
@Priority(value = 10)
@VerifyToken
public class ServiceTokenVerificationInterceptor implements ContainerRequestFilter, ContainerResponseFilter
{
	protected Logger LOG = Logger.getLogger(ServiceTokenVerificationInterceptor.class);

	/**
	 * filter pre request (before calling the service method )
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		LOG.debug("########################## Service PreInterceptor Header");
		String tokenHeaderValue = requestContext.getHeaderString(AppConstants.SecurityConstants.TOKEN);
		LOG.debug("########################## Service PreInterceptor Header token = " + tokenHeaderValue + " ");
		// IMP_Ahmed read RoleId from TokenStore and put it on intercepted method input (Map)

		if (!isTokenValid(tokenHeaderValue))
		{
			requestContext.abortWith(AbortResponses.UNAUTHORIZED_RESPONSE);
		}

		LOG.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%% INTINTINT " + this.getClass().getSimpleName() + " ##########################");

	}

	/**
	 * filter post request (after calling the service method )
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
	{
		LOG.debug("########################## " + this.getClass().getSimpleName() + " ##########################");
		String requestToken = (String) requestContext.getHeaders().getFirst(AppConstants.SecurityConstants.TOKEN);
		responseContext.getHeaders().putSingle(AppConstants.SecurityConstants.TOKEN, requestToken);
		String token = (String) responseContext.getHeaders().getFirst(AppConstants.SecurityConstants.TOKEN);
		LOG.debug("########################## Service PostInterceptor response token :" + token);
		LOG.debug("########################## Service PostInterceptor request Token :" + requestToken);

		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method sreviceMethod = methodInvoker.getMethod();
		String methodName = sreviceMethod.getName();
		LOG.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%% INTINTINTPOST " + this.getClass().getSimpleName() + " ##########################");
		// LOG.debug("Receiving request from: " + servletRequest.getRemoteAddr());
		LOG.debug("Attempt to invoke method \"" + methodName + "\"");
		// LOG.debug("Servlet Path \"" + servletRequest.getRequestURI() + "\"");
		// LOG.debug("Context Path \"" + servletRequest.getContextPath() + "\"");
		// LOG.debug("Path Info \"" + servletRequest.getPathInfo() + "\"");
		// LOG.debug("Path Translated \"" + servletRequest.getPathTranslated() + "\"");
	}

	private boolean isTokenValid(String tokenHeaderValue)
	{
		boolean isValid = false;

		LOG.debug("** server ***" + tokenHeaderValue + "*****");
		if (tokenHeaderValue != null)
		{
			Object tokenDataObj = TokentStore.TokenMap.get(tokenHeaderValue);
			if (tokenDataObj != null)
			{
				isValid = true;

				String[] tokenData = (String[]) tokenDataObj;
				Calendar currentDate = Calendar.getInstance();
				tokenData[2] = currentDate.getTimeInMillis() + "";
				TokentStore.TokenMap.put(tokenHeaderValue, tokenData);
			}
		}

		return isValid;
	}
}
