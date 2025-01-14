package com.ewhale.points.common.security.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.stores.SecurityKeyStore;
import com.ewhale.points.common.util.AppConstants;

/**
 * @author Ayman Yosry
 */
public class KeyGenerator implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String keystorePassword = null;
	private String keystoreFile = null;
	private String privatekeyPassword = null;
	protected String certificateAlias = null;

	protected KeyGenerator(String certificateAlias)
	{
		this.certificateAlias = certificateAlias;
	}
	/**
	 * 
	 * @param keystoreFile
	 * @param keystorePassword
	 * @param certificateAlias
	 * @param privatekeyPassword
	 */
	public KeyGenerator(String keystoreFile, String keystorePassword, String certificateAlias, String privatekeyPassword)
	{
		this.keystoreFile = keystoreFile;
		this.keystorePassword = keystorePassword;
		this.certificateAlias = certificateAlias;
		this.privatekeyPassword = privatekeyPassword;
	}

	/**
	 * 
	 * @param keystoreFile
	 * @param keystorePassword
	 * @param certificateAlias
	 */
	public KeyGenerator(String keystoreFile, String keystorePassword, String certificateAlias)
	{
		this.keystoreFile = keystoreFile;
		this.keystorePassword = keystorePassword;
		this.certificateAlias = certificateAlias;
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	protected KeyStore getKeyPair() throws AuthenticationSecurityException
	{
		KeyStore keystore = getKeystore();
		if (keystore == null)
		{
			keystore = loadKeystore();			
			storeKeystore(keystore);
		}
		return keystore;
	}
	
	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	protected KeyStore loadKeystore() throws AuthenticationSecurityException
	{
		KeyStore keystore = null;
		InputStream input = null;
		try
		{
			keystore = KeyStore.getInstance(AppConstants.SecurityConstants.KEYSTORE_INSTANCE);
			input = KeyGenerator.class.getResourceAsStream(keystoreFile);
			keystore.load(input, keystorePassword.toCharArray());
		}
		catch (KeyStoreException e)
		{
			throw new AuthenticationSecurityException("Problem in loading keystore ...", e);
		}
		catch (CertificateException | NoSuchAlgorithmException | IOException e)
		{
			throw new AuthenticationSecurityException("Problem in loading certificate ...", e);
		}
		finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e)
				{
					throw new AuthenticationSecurityException("IO Problem in closng keystore ...", e);
				}
			}
		}
		
		return keystore;
	}

	/**
	 * 
	 * @param keystore
	 */
	private void storeKeystore(KeyStore keystore)
	{
		SecurityKeyStore.KeyMap.put(keystoreFile, keystore);
	}

	/**
	 * 
	 * @return
	 */
	private KeyStore getKeystore()
	{
		KeyStore keyPair = SecurityKeyStore.KeyMap.get(keystoreFile);
		return keyPair;
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public PrivateKey getPrivateKey() throws AuthenticationSecurityException
	{
		PrivateKey privateKey;
		try
		{
			KeyStore keystore = getKeyPair();
			privateKey = (PrivateKey) keystore.getKey(certificateAlias, privatekeyPassword.toCharArray());
		}
		catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e)
		{
			throw new AuthenticationSecurityException("IO Problem in exporting private key ...", e);
		}
		return privateKey;
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public PublicKey getPublicKey() throws AuthenticationSecurityException
	{
		X509Certificate cert = getCertificate();
		PublicKey publicKey = cert.getPublicKey();

		return publicKey;
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public X509Certificate getCertificate() throws AuthenticationSecurityException
	{
		X509Certificate cert = null;
		try
		{
			KeyStore keystore = getKeyPair();
			cert = (X509Certificate) keystore.getCertificate(certificateAlias);
		}
		catch (KeyStoreException e)
		{
			throw new AuthenticationSecurityException("IO Problem in exporting Certificate ...", e);
		}
		//printCertificateInfo(cert);
		return (X509Certificate) cert;
	}

	/**
	 * 
	 * @param cert
	 */
	protected void printCertificateInfo(Certificate cert)
	{
		X509Certificate x509 = (X509Certificate) cert;
		// Print to console information contained in the certificate.
		System.out.println("--------------------------------------------------------");
		System.out.println("Subject: \t" + x509.getSubjectDN().getName());
		System.out.println("Issuer: \t" + x509.getIssuerDN().getName());
		System.out.println("Version: \t" + x509.getVersion());
		System.out.println("Valid Date: \t" + x509.getNotBefore());
		System.out.println("Expiry Date: \t\t" + x509.getNotAfter());
		System.out.println("Type: \t" + x509.getType());
		System.out.println("Serial Number: \t\t" + x509.getSerialNumber());
		System.out.println("Algorithm OID: \t\t" + x509.getSigAlgOID());
		System.out.println("Signature Algorithm:\t\t\t" + x509.getSigAlgName());
		System.out.println("Algorithm: \t" + x509.getPublicKey().getAlgorithm());
		System.out.println("Public Key Format: \t\t" + x509.getPublicKey().getFormat());
		System.out.println("Signature Length: \t\t" + x509.getSignature().length);
		//System.out.println("Certificate to string:" + x509.toString());
		System.out.println("--------------------------------------------------------");
	}
}
