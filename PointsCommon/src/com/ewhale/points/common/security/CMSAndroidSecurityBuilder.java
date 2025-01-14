package com.ewhale.points.common.security;

import java.io.Serializable;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.security.base64.BASE64Encoder;
import com.ewhale.points.common.security.cms.android.DataEncryptor;
import com.ewhale.points.common.security.cms.android.DataSigner;
import com.ewhale.points.common.security.cms.android.DataVerifier;
import com.ewhale.points.common.security.cms.android.KeyGenerator;
import com.ewhale.points.common.util.AppConstants;

class CMSAndroidSecurityBuilder extends SecurityBuilder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @param context
	 * @throws AuthenticationSecurityException
	 */
	@Override
	public void loadKeys(Object context) throws AuthenticationSecurityException
	{
		KeyGenerator generator = new KeyGenerator(AppConstants.SecurityConstants.DS_CERT_ALIAS);
		generator.loadPrivatekey(context);
		generator.loadCertificate();
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	@Override
	public String sign(String data) throws AuthenticationSecurityException
	{
		DataSigner signer = new DataSigner();
		String signature = signer.sign(data);
		return signature;
	}

	/**
	 * 
	 * @param data
	 * @param signature
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	@Override
	public boolean verify(String data, String signature, String publicKey) throws AuthenticationSecurityException
	{
		DataVerifier verifier = new DataVerifier();
		verifier.setPublicKey(publicKey);
		boolean isVerified = verifier.verify(data, signature);
		return isVerified;
	}

	/**
	 * @param password
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	@Override
	public String encrypt(String password) throws AuthenticationSecurityException
	{
		DataEncryptor enc = new DataEncryptor();
		password = toHex(hashSHA256(password));
		String encryptedPassword = enc.encrypt(password);
		return encryptedPassword;
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	@Override
	public String getAndroidPublicKey() throws AuthenticationSecurityException
	{
		KeyGenerator generator = new KeyGenerator(AppConstants.SecurityConstants.DS_CERT_ALIAS);

		String b64EncPK = null;
		byte[] encPK = generator.getPublicKey().getEncoded();
		BASE64Encoder b64enc = new BASE64Encoder();
		b64EncPK = b64enc.encode(encPK);
		b64EncPK = b64EncPK.replace("\r\n", AppConstants.SecurityConstants.SIGNATURE_SEPARATOR).replace("\n", AppConstants.SecurityConstants.SIGNATURE_SEPARATOR);

		return b64EncPK;
	}
}
