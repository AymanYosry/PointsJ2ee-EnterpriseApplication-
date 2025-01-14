/**
 * 
 */
package com.ewhale.points.web.exception;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.ws.rs.WebApplicationException;

import org.primefaces.application.exceptionhandler.ExceptionInfo;
import org.primefaces.application.exceptionhandler.PrimeExceptionHandler;

import com.ewhale.points.common.exception.SessionExpiredException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.web.managedbean.main.FacesUtil;

/**
 * @author Ayman Yosry
 */
public class WebExceptionHandler extends PrimeExceptionHandler
{
	private static final Logger log = Logger.getLogger(WebExceptionHandler.class.getCanonicalName());

	public WebExceptionHandler(ExceptionHandler exception)
	{
		super(exception);
	}

	@Override
	public void handle() throws FacesException
	{
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getResponseComplete())
		{
			return;
		}

		Iterable<ExceptionQueuedEvent> exceptionQueuedEvents = getUnhandledExceptionQueuedEvents();
		if (exceptionQueuedEvents != null && exceptionQueuedEvents.iterator() != null)
		{
			Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();

			if (unhandledExceptionQueuedEvents.hasNext())
			{
				try
				{
					Throwable throwable = unhandledExceptionQueuedEvents.next().getContext().getException();

					Throwable rootCause = getRootCause(throwable);
					ExceptionInfo info = createExceptionInfo(rootCause);

					// print exception in development stage
					if (context.getApplication().getProjectStage() == ProjectStage.Development)
					{
						rootCause.printStackTrace();
						log.log(Level.SEVERE, rootCause.getMessage(), rootCause);
					}

					if (isLogException(context, rootCause))
					{
						logException(rootCause);
					}

					Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

					// NavigationHandler nav = context.getApplication().getNavigationHandler();
					// redirect error page
					if (rootCause instanceof WebApplicationException)
					{
						WebApplicationException wae = (WebApplicationException) rootCause;

						int status = wae.getResponse().getStatus();
						String errorMessage = wae.getResponse().readEntity(String.class);
						String message = wae.getMessage();
						if (status == ExceptionConstants.VALIDATION_EXCETION_STATUS_CODE)
						{
							String localizedMessage = FacesUtil.getLocalizedExeptionMessage(errorMessage);
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", localizedMessage));
						}
						else
						{
							// add error message to the page
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorMessage));
							requestMap.put("javax.servlet.error.statusCode", status);
							requestMap.put("javax.servlet.error.exception", errorMessage);
							requestMap.put("javax.servlet.error.message", message);
						}
						context.renderResponse();
						return;
					}
					else
					{
						requestMap.put("javax.servlet.error.exception", rootCause);
						requestMap.put("javax.servlet.error.message", rootCause.getMessage());
					}

					if (rootCause instanceof SessionExpiredException)
					{
						final ExternalContext externalContext = context.getExternalContext();

						// timeout.jsf is the page to to be viewed. Better not to give /WEB-INF/Login.xhtml
						context.setViewRoot(context.getApplication().getViewHandler().createView(context, "/timeout.jsf"));

						// when browser back button is pressed after session timeout, I used this.
						externalContext.redirect("/timeout.jsf");

						context.getPartialViewContext().setRenderAll(true);
						context.renderResponse();
						context.responseComplete();
						return;

						// handleRedirect(context, rootCause, info, false);
						// NavigationHandler nav = context.getApplication().getNavigationHandler();
						// nav.handleNavigation(context, null, "/logout.jsf");
						// context.renderResponse();
					}
					else if (context.getPartialViewContext().isAjaxRequest())
					{
							handleAjaxException(context, rootCause, info);
					}
					else
					{
						handleRedirect(context, rootCause, info, false);
					}

				}
				catch (Exception ex)
				{
					log.log(Level.SEVERE, "Could not handle exception!", ex);
				}

				finally
				{
					// remove it from queue
					unhandledExceptionQueuedEvents.remove();
				}
			}
			while (unhandledExceptionQueuedEvents.hasNext())
			{
				// Any remaining unhandled exceptions are not interesting. First fix the first.
				// unhandledExceptionQueuedEvents.next();
				Throwable throwable = unhandledExceptionQueuedEvents.next().getContext().getException();
				throwable.printStackTrace();
				// log.logp(level, sourceClass, sourceMethod, msg, throwable);
				unhandledExceptionQueuedEvents.remove();
			}
		}
	}

	@Override
	protected boolean isLogException(FacesContext context, Throwable rootCause)
	{
		if (rootCause != null && rootCause instanceof ValidationException)
			return false;

		return super.isLogException(context, rootCause);
	}
}
