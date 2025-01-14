package com.ewhale.points.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.ResourceHandler;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.security.AuthenticationBean;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
@WebFilter(dispatcherTypes =
	{ DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR }, urlPatterns =
	{ "/*" })
public class AuthorizationFilter implements Filter
{
	private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public AuthorizationFilter()
	{
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (javax.servlet.http.HttpServletResponse) response;

		// character encoding is set to support internationalization (supporting multiple languages)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// Disables caching of JSF pages
		String resourceIdentifier = httpServletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER;
		if (!httpServletRequest.getRequestURI().startsWith(resourceIdentifier))
		{
			// Skip JSF resources (CSS/JS/Images/etc)
			httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			httpServletResponse.setDateHeader("Expires", 0); // Proxies.
			LOGGER.info("#..# " + httpServletRequest.getRequestURI());
		}
		else
		{
			// LOG.debug(httpServletRequest.getRequestURI());
			chain.doFilter(request, response);
			return;
		}

		String requestUrl = httpServletRequest.getRequestURL().toString();
		LOGGER.info(requestUrl);
		HttpSession httpSession = httpServletRequest.getSession();
		// // check for authentication
		AuthenticationBean authenticationBean = ((AuthenticationBean) httpSession.getAttribute("authenticationBean"));
		Map<String, Object> loggedInUser = null;
		if (authenticationBean != null)
			loggedInUser = authenticationBean.getLoginData();

		int userURLIndicator = requestUrl.indexOf("pages/user");
		int agentURLIndicator = requestUrl.indexOf("pages/agent");
		int systemURLIndicator = requestUrl.indexOf("pages/system");

		int authenticatedURLindicator = userURLIndicator * agentURLIndicator * systemURLIndicator;
		if (authenticatedURLindicator > 0)
		{
			if (loggedInUser == null)
			{
				httpSession.setAttribute("requestedPageURL", httpServletRequest.getRequestURL().toString());
				httpServletResponse.sendRedirect("/PointsWeb/login.jsf");
			}
			else
			{
				if (userURLIndicator < 0)
				{
					int roleId = ((Number) loggedInUser.get(EntityConstants.Profile.roleId)).intValue();
					String roleNme = getRoleName(roleId);
					boolean validURL = (requestUrl.indexOf(roleNme) > 0) ? true : false;
					if (!validURL)
					{
						httpSession.invalidate();
						httpServletResponse.sendRedirect("/PointsWeb/logout.jsf");
					}
				}
			}
		}
		else
		{
			// go to the requested page
		}

		chain.doFilter(request, response);
	}

	private String getRoleName(int roleId)
	{
		String roleName = null;
		if (roleId == EntityConstants.Role.Fixed.userRole.ID)
			roleName = "user";
		if (roleId == EntityConstants.Role.Fixed.agentAdminRole.ID)
			roleName = "agent/admin";
		if (roleId == EntityConstants.Role.Fixed.agentSellerRole.ID)
			roleName = "agent/seller";
		if (roleId == EntityConstants.Role.Fixed.systemAdminRole.ID)
			roleName = "system/admin";
		if (roleId == EntityConstants.Role.Fixed.systemSalesRole.ID)
			roleName = "system/sales";

		return roleName;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
	}

	@SuppressWarnings("unused")
	private boolean isAJAXRequest(HttpServletRequest request)
	{
		boolean check = false;
		String facesRequest = request.getHeader("Faces-Request");
		if (facesRequest != null && facesRequest.equals("partial/ajax"))
		{
			check = true;
		}
		return check;
	}
}
