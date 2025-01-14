/**
 * 
 */
package com.ewhale.points.web.managedbean.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.exception.SessionExpiredException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.security.SecurityBuilder;
import com.ewhale.points.common.security.SecurityFactory;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.security.proxy.AuthenticationServiceClient;

/**
 * @author Ayman Yosry
 */
@SessionScoped
@ManagedBean
public class AuthenticationBean extends AbsoluteBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(AuthenticationBean.class.getName());

	private AuthenticationServiceClient authenticationServiceClient = null;

	private String mobile;

	private String password;

	private String oldPassword;

	private String hashsum1;

	private String hashsum2;

	private String secretKey;

	private String requestCode;

	// IMP_Ahmed ==> put change_password.xhtml for layout after login without menu

	private Map<String, Object> loginData;

	/**
	 * @return
	 * @throws ValidationException
	 * @throws AuthenticationSecurityException
	 */
	public String login() throws ValidationException, AuthenticationSecurityException
	{
		LOGGER.info(this.getClass().getSimpleName() + " --> login()");
		String page = null;

		boolean isPasswordValid = validatePassword(this.mobile, this.password, this.secretKey, this.hashsum1, this.hashsum2);

		if (isPasswordValid)
		{
			authenticationServiceClient = ServiceClientUtil.getAuthenticationServiceClient(ServiceClientUtil.WEB);

			Map<String, Object> userData = new HashMap<>();
			String encryptedPassword = SecurityFactory.getSecurityBuilder(SecurityFactory.CMS_SECURITY_BUILDER).encrypt(password);
			userData.put(EntityConstants.Login.mobile, mobile);
			userData.put(EntityConstants.Login.password, encryptedPassword);

			loginData = authenticationServiceClient.login(userData);

			// page = (loginData != null) ? "pages/user/index.jsf" : "error.jsf";
			page = "pages/user/index.jsf";
		}
		else
		{
			throw new ValidationException("Invalid password");
		}

		release();
		return page;
	}

	/**
	 * @return
	 * @throws ValidationException
	 * @throws AuthenticationSecurityException
	 */
	public String changePassword() throws ValidationException, AuthenticationSecurityException
	{
		LOGGER.info(this.getClass().getSimpleName() + " --> changePassword()");
		String page = null;

		boolean isPasswordValid = validatePassword(this.mobile, this.password, this.secretKey, this.hashsum1, this.hashsum2);
		if (isPasswordValid)
		{
			authenticationServiceClient = ServiceClientUtil.getAuthenticationServiceClient(ServiceClientUtil.WEB);

			String encryptedOldPassword = SecurityFactory.getSecurityBuilder(SecurityFactory.CMS_SECURITY_BUILDER).encrypt(oldPassword);
			String encryptedNewPassword = SecurityFactory.getSecurityBuilder(SecurityFactory.CMS_SECURITY_BUILDER).encrypt(password);

			Map<String, Object> passwordData = new HashMap<>();
			passwordData.put(EntityConstants.Login.mobile, mobile);
			passwordData.put(EntityConstants.Login.password, encryptedOldPassword);
			passwordData.put(EntityConstants.Login.newPassword, encryptedNewPassword);

			loginData = authenticationServiceClient.changePassword(passwordData);
			// page = (loginData != null) ? "pages/user/index.jsf" : "error.jsf";
			page = "pages/user/index.jsf";
		}
		else
		{
			throw new ValidationException("Invalid password");
		}

		release();
		return page;
	}

	/**
	 * @return
	 */
	public String resetPassword()
	{
		LOGGER.info(this.getClass().getSimpleName() + " --> resetPassword()");
		String page = "confirm_request.jsf";

		authenticationServiceClient = ServiceClientUtil.getAuthenticationServiceClient(ServiceClientUtil.WEB);
		authenticationServiceClient.resetPassword(this.mobile);

		release();
		return page;
	}

	/**
	 * @return
	 * @throws ValidationException
	 * @throws AuthenticationSecurityException
	 */
	public String confirmRequest() throws ValidationException, AuthenticationSecurityException
	{
		LOGGER.info(this.getClass().getSimpleName() + " --> confirmRequest()");
		String page = null;

		boolean isPasswordValid = validatePassword(this.mobile, this.password, this.secretKey, this.hashsum1, this.hashsum2);
		if (isPasswordValid)
		{
			authenticationServiceClient = ServiceClientUtil.getAuthenticationServiceClient(ServiceClientUtil.WEB);

			Map<String, Object> confirmData = new HashMap<>();
			String encryptedCode = SecurityFactory.getSecurityBuilder(SecurityFactory.CMS_SECURITY_BUILDER).encrypt(password);
			confirmData.put(EntityConstants.Login.mobile, mobile);
			confirmData.put(EntityConstants.Login.requestCode, encryptedCode);

			// Map<String, Object> userData =
			authenticationServiceClient.validateUser(confirmData);

			// page = (userData != null) ? page = "change_password.jsf" : "error.jsf";
			page = "change_password.jsf";
		}
		else
		{
			throw new ValidationException("Invalid password");
		}

		release();
		return page;
	}

	/**
	 * @return
	 */
	public String logout()
	{
		try
		{
			String token = FacesUtil.getLoginToken();
			if (token != null)
			{
				authenticationServiceClient = ServiceClientUtil.getAuthenticationServiceClient(ServiceClientUtil.WEB);
				authenticationServiceClient.logout(token);
			}
		}
		catch (SessionExpiredException e1)
		{
			//do nothing
		}
		FacesContext context = FacesContext.getCurrentInstance();

		final ExternalContext externalContext = context.getExternalContext();
		externalContext.invalidateSession();

		context.setViewRoot(context.getApplication().getViewHandler().createView(context, "login.jsf"));
		// this try should be tested to know where exactly to put
		try
		{
			externalContext.redirect("login.jsf");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		logger.debug("logout method call");

		context.getPartialViewContext().setRenderAll(true);
		context.renderResponse();

		return "logout.jsf";
	}

	/**
	 * @param userName
	 * @param password
	 * @param secretKey
	 * @param hashsum1
	 * @param hashsum2
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	private boolean validatePassword(String mobile, String password, String secretKey, String hashsum1, String hashsum2)
			throws AuthenticationSecurityException
	{
		boolean isPasswordValid = false;

		if (this.secretKey != null)
		{
			String expectedHashsum1 = null;
			String expectedHashsum2 = null;

			expectedHashsum1 = new String(SecurityBuilder.toHex(SecurityBuilder.hashSHA512(password + secretKey)));
			expectedHashsum2 = new String(SecurityBuilder.toHex(SecurityBuilder.hashSHA512(password + mobile)));

			isPasswordValid = (expectedHashsum1.equals(hashsum1) && expectedHashsum2.equals(hashsum2)) ? true : false;
		}

		return isPasswordValid;
	}

	/**
	 * 
	 */
	private void release()
	{
		password = null;
		oldPassword = null;
		hashsum1 = null;
		hashsum2 = null;
		secretKey = null;
		requestCode = null;
	}

	/**
	 * @return the loginData
	 */
	public Map<String, Object> getLoginData()
	{
		return loginData;
	}

	/**
	 * @param loginData the loginData to set
	 */
	public void setLoginData(Map<String, Object> loginData)
	{
		this.loginData = loginData;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile()
	{
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword()
	{
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the hashsum1
	 */
	public String getHashsum1()
	{
		return hashsum1;
	}

	/**
	 * @param hashsum1 the hashsum1 to set
	 */
	public void setHashsum1(String hashsum1)
	{
		this.hashsum1 = hashsum1;
	}

	/**
	 * @return the hashsum2
	 */
	public String getHashsum2()
	{
		return hashsum2;
	}

	/**
	 * @param hashsum2 the hashsum2 to set
	 */
	public void setHashsum2(String hashsum2)
	{
		this.hashsum2 = hashsum2;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey()
	{
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey)
	{
		this.secretKey = secretKey;
	}

	/**
	 * @return the requestCode
	 */
	public String getRequestCode()
	{
		return requestCode;
	}

	/**
	 * @param requestCode the requestCode to set
	 */
	public void setChangeCode(String requestCode)
	{
		this.requestCode = requestCode;
	}

	public Long getAgentId()
	{
		return FacesUtil.getLoginAgentId();
	}

	public int getRoleId()
	{
		return (Integer) FacesUtil.getLoginData().get(EntityConstants.Login.roleId);
	}
}
