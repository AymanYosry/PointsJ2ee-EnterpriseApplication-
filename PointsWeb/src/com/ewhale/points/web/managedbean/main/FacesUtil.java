package com.ewhale.points.web.managedbean.main;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import com.ewhale.points.common.exception.SessionExpiredException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.AppConstants;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.web.managedbean.security.AuthenticationBean;
import com.ewhale.points.ws.main.proxy.LookUpServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

public class FacesUtil
{
	private static Logger LOG = Logger.getLogger(FacesUtil.class);

	public static Map<String, Object> getLoginData()
	{

		AuthenticationBean authenticationBean = (AuthenticationBean) getObjectFromSession(AuthenticationBean.class, true);
		Map<String, Object> loginData = authenticationBean.getLoginData();
		if (loginData == null)
			throw new SessionExpiredException();
		return loginData;
	}

	@SuppressWarnings("unchecked")
	public static <T extends AbsoluteBean> T getObjectFromSession(Class<T> managedBeanClass, boolean initializeIfNull)
	{
		String managedBeanName = getManagedBeanName(managedBeanClass);
		Map<String, Object> sessionMap = getSessionMap();
		T managedBean = (T) sessionMap.get(managedBeanName);
		if (managedBean == null && initializeIfNull)
		{
			try
			{
				managedBean = managedBeanClass.newInstance();
				putObjectInSession(managedBean);
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				// e.printStackTrace();
			}
		}
		return managedBean;
	}

	public static <T extends AbsoluteBean> void putObjectInSession(T managedBean)
	{
		String managedBeanName = getManagedBeanName(managedBean.getClass());
		getSessionMap().put(managedBeanName, managedBean);
	}

	private static Map<String, Object> getSessionMap()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		return sessionMap;
	}

	private static <T extends AbsoluteBean> String getManagedBeanName(Class<T> managedBeanClass)
	{
		String managedBeanClassName = managedBeanClass.getSimpleName();
		String managedBeanName = managedBeanClassName.substring(0, 1).toLowerCase() + managedBeanClassName.substring(1);
		return managedBeanName;
	}

	public static void growlInfoMessage(String title, String details)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(title, details));
	}

	public static void addErrorMessage(String title, String details)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, details));
	}

	public static Long getLoginId()
	{
		long loginId = ((Number) (getLoginData().get(EntityConstants.Login.loginId))).longValue();
		return loginId;
	}

	public static Long getLoginAgentId()
	{
		long agentId = ((Number) (getLoginData().get(EntityConstants.Login.agentId))).longValue();
		return agentId > 0 ? agentId : null;
	}

	public static String getLoginToken()
	{
		String token = (String) getLoginData().get(EntityConstants.Login.token);
		return token;
	}

	public static Date getDateFromString(String dateStr)
	{
		if (dateStr == null)
			return null;
		try
		{
			return AppConstants.dateTimeFormat.parse(dateStr);
		}
		catch (ParseException e)
		{
			// e.printStackTrace();
			return null;
		}

	}

	public static List<Map<String, Object>> loadCurrenciesList()
	{
		LookUpServiceClient lookUpServiceClient = ServiceClientUtil.getLookUpServiceClient(FacesUtil.getLoginToken());
		return lookUpServiceClient.getAllCurrencies();

	}

	public static List<Map<String, Object>> loadCountriesList()
	{
		LookUpServiceClient lookUpServiceClient = ServiceClientUtil.getLookUpServiceClient(getLoginToken());
		return lookUpServiceClient.getAllCountries();
	}

	public static List<Map<String, Object>> loadStatusesList()
	{
		LookUpServiceClient lookUpServiceClient = ServiceClientUtil.getLookUpServiceClient(FacesUtil.getLoginToken());
		return lookUpServiceClient.getAllStatuses();
	}

	public static boolean isImageUploaded(UploadedFile uploadedImage) throws Exception
	{
		boolean imageUploaded = uploadedImage != null && uploadedImage.getContents().length > 0;
		if (imageUploaded && uploadedImage.getContents().length > 65500)
		{
			throw new ValidationException(ExceptionConstants.LARGE_IMAGE_MAX_65K_EX_MSG);
		}
		return imageUploaded;
	}

	private static Properties exceptionMessagesProp;

	private final static String exceptionMessagesFileName = "/resources/ex_msg.properties";

	public static String getLocalizedExeptionMessage(String errorMessage)
	{
		try
		{
			loadMessagesPropFile(exceptionMessagesFileName);
			String localizedMessage = exceptionMessagesProp.getProperty(errorMessage);
			if (localizedMessage != null)
				return localizedMessage;
			else
				return errorMessage;
		}
		catch (IOException e)
		{
			LOG.error("could not load exception properties", e);
			return errorMessage;
		}
	}

	private static void loadMessagesPropFile(String fileName) throws IOException
	{
		if (exceptionMessagesProp == null)
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(fileName);
			exceptionMessagesProp = new Properties();
			exceptionMessagesProp.load(inputStream);
		}
	}
}
