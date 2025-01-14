package com.ewhale.points.common.security.cms;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

import javax.crypto.Cipher;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.security.base64.BASE64Encoder;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 */
public class DataEncryptor implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KeyGenerator keyPair = null;
	private String KEYSTORE = AppConstants.SecurityConstants.AUTH_TRUST_KEYSTORE;
	private String KEYSTORE_PWD = AppConstants.SecurityConstants.AUTH_KEYSTORE_PWD;
	protected String CERT_ALIAS = AppConstants.SecurityConstants.AUTH_CERT_ALIAS;

	public DataEncryptor()
	{
		keyPair = new KeyGenerator(KEYSTORE, KEYSTORE_PWD, CERT_ALIAS);
	}

	/**
	 * Encrypts a given string with the RSA Algorithm
	 * @param inputData String
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String encrypt(String inputData) throws AuthenticationSecurityException
	{
		PublicKey publicKey = keyPair.getPublicKey();
		String encryptedData = encrypt(inputData, publicKey);
		return encryptedData;
	}

	/**
	 * Encrypts a given string with the RSA Algorithm
	 * @param inputData byte[]
	 * @param publicKey
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	private String encrypt(String inputData, PublicKey publicKey) throws AuthenticationSecurityException
	{
		Cipher enc;
		String encryptedData = null;
		BASE64Encoder b64 = new BASE64Encoder();
		try
		{
			enc = Cipher.getInstance(AppConstants.SecurityConstants.CIPHER_ALGORITHM);
			enc.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] hexEncodedCipher = enc.doFinal(inputData.getBytes());
			encryptedData = b64.encode(hexEncodedCipher);
		}
		catch (GeneralSecurityException e)
		{
			throw new AuthenticationSecurityException("RSA algorithm not supported", e);
		}
		catch (Exception e)
		{
			throw new AuthenticationSecurityException(e);
		}

		return encryptedData;
	}
}