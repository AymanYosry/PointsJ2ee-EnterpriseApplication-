/**
 * 
 */
package com.ewhale.points.ws.interceptors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.logging.Logger;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.security.SecurityFactory;
import com.ewhale.points.common.security.base64.BASE64Encoder;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 * @updated Ahmad Khalil
 */
@Provider
@Priority(value = 1)
public class ServiceClientDigitalSignaturePreInterceptor implements WriterInterceptor
{
	protected Logger LOG = Logger.getLogger(ServiceClientDigitalSignaturePreInterceptor.class);
	private String securityBuilder = SecurityFactory.CMS_SECURITY_BUILDER;

	public ServiceClientDigitalSignaturePreInterceptor(){}
	public ServiceClientDigitalSignaturePreInterceptor(String securityBuilder)
	{
		this.securityBuilder = securityBuilder;
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
			throws WebApplicationException, IOException
	{
		OutputStream old = null;
		BASE64Encoder b64 = new BASE64Encoder();
		try
		{
			String signature = null;
			old = writerInterceptorContext.getOutputStream();

			// store body in a byte array so we can use it to calculate
			// signature
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			writerInterceptorContext.setOutputStream(baos);
			writerInterceptorContext.proceed();
			byte[] body = baos.toByteArray();
			LOG.debug("stream will close");
			baos.close();
			String dataToBeSigned = b64.encode(body);
			
			// sign
			signature = SecurityFactory.getSecurityBuilder(securityBuilder).sign(dataToBeSigned);
			if (securityBuilder.equals(SecurityFactory.CMS_ANDROID_SECURITY_BUILDER))
			{
				String publicKey = SecurityFactory.getSecurityBuilder(securityBuilder).getAndroidPublicKey();
				writerInterceptorContext.getHeaders().putSingle(AppConstants.SecurityConstants.PK, publicKey);
			}
			
			writerInterceptorContext.getHeaders().putSingle(AppConstants.SecurityConstants.SIGNATURE, signature);
			writerInterceptorContext.getHeaders().putSingle(AppConstants.SecurityConstants.SECURITY_BUILDER, securityBuilder);
			
			old.write(body);
		}
		catch (AuthenticationSecurityException e)
		{
			LOG.error("Error In Signing Client Sigining Intercepto", e);
			throw new WebApplicationException("Error In Signing Client Sigining Intercepto", e, e.getStatusCode());
		}
		finally
		{
			writerInterceptorContext.setOutputStream(old);
		}
	}

}
