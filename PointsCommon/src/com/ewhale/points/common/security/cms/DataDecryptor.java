package com.ewhale.points.common.security.cms;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.security.base64.BASE64Decoder;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 */
public class DataDecryptor implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private KeyGenerator keyPair = null;
	private String KEYSTORE = AppConstants.SecurityConstants.AUTH_KEYSTORE;
	private String KEYSTORE_PWD = AppConstants.SecurityConstants.AUTH_KEYSTORE_PWD;
	private String CERT_ALIAS = AppConstants.SecurityConstants.AUTH_CERT_ALIAS;
	private String PRIVATEKEY_PWD = AppConstants.SecurityConstants.AUTH_PRIVATEKEY_PWD;

	public DataDecryptor()
	{
		keyPair = new KeyGenerator(KEYSTORE, KEYSTORE_PWD, CERT_ALIAS, PRIVATEKEY_PWD);
	}

	/**
	 * Decrypts a given string with the RSA keys
	 * @param encryptedData String
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String decrypt(String encryptedData) throws AuthenticationSecurityException
	{
		PrivateKey privateKey = keyPair.getPrivateKey();
		String decryptedData = decrypt(encryptedData, privateKey);
		return decryptedData;
	}

	/**
	 * Decrypts a given string with the RSA keys
	 * @param encrypted byte[]
	 * @param privateKey
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String decrypt(String encrypted, PrivateKey privateKey) throws AuthenticationSecurityException
	{
		BASE64Decoder b64 = new BASE64Decoder();
		Cipher dec = null;
		byte[] original = null;
		String originalString;
		try
		{
			dec = Cipher.getInstance(AppConstants.SecurityConstants.CIPHER_ALGORITHM);
			dec.init(Cipher.DECRYPT_MODE, privateKey);
			original = dec.doFinal(b64.decodeBuffer(encrypted)); 
			originalString = new String(original);
		}
		catch (IllegalBlockSizeException e)
		{
			throw new AuthenticationSecurityException("Decrypt error --> Illegal Block Size", e);
		}
		catch (BadPaddingException e)
		{
			throw new AuthenticationSecurityException("Decrypt error --> Bad Padding", e);
		}
		catch (IOException e)
		{
			throw new AuthenticationSecurityException("Decrypt error --> Base 64 Decode Problem", e);
		}
		catch (GeneralSecurityException e)
		{
			throw new AuthenticationSecurityException("Problem in initiating RSA algorithm", e);
		}

		return originalString;
	}
}
