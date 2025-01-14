package com.ewhale.points.common.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.util.AppConstants;

public abstract class SecurityBuilder
{
	/**
	 *
	 * @param context
	 * @throws AuthenticationSecurityException
	 */
	public void loadKeys(Object context) throws AuthenticationSecurityException	{}

	/**
	 * Sign Data
	 * 
	 * @param data
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public abstract String sign(String data) throws AuthenticationSecurityException;

	/**
	 * Verify Data
	 * 
	 * @param data
	 * @param signature
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public boolean verify(String data, String signature) throws AuthenticationSecurityException
	{
		return false;
	}
	/**
	 * for android
	 * 
	 * @param data
	 * @param signature
	 * @param publicKey
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public boolean verify(String data, String signature, String publicKey) throws AuthenticationSecurityException
	{
		return false;
	}

	/**
	 * @param password
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public abstract String encrypt(String password) throws AuthenticationSecurityException;

	/**
	 * @param password
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String decrypt(String password) throws AuthenticationSecurityException
	{
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String getAndroidPublicKey() throws AuthenticationSecurityException
	{
		return null;
	}

	/**
	 * @param password
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public String authenticate(String password) throws AuthenticationSecurityException
	{
		password = toHex(hashSHA512(password));
		password = encrypt(password);
		return password;
	}

	/**
	 * @param password
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public static String passwordHashing(String password) throws AuthenticationSecurityException
	{
		String hashedPassword = toHex(hashSHA512(password));
		hashedPassword = toHex(hashSHA256(hashedPassword));
		return hashedPassword;
	}

	/**
	 * @param data
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public static byte[] hashSHA256(String data) throws AuthenticationSecurityException
	{
		byte[] hashedData;
		hashedData = hash(data, AppConstants.SecurityConstants.SHA256_ALGORITHM);
		return hashedData;
	}

	/**
	 * @param data
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public static byte[] hashSHA512(String data) throws AuthenticationSecurityException
	{
		byte[] hashedData;
		hashedData = hash(data, AppConstants.SecurityConstants.SHA512_ALGORITHM);
		return hashedData;
	}

	/**
	 * @param data
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	private static byte[] hash(String data, String hashAlgorithm) throws AuthenticationSecurityException
	{
		byte[] hashedData;
		try
		{
			hashedData = MessageDigest.getInstance(hashAlgorithm)
					.digest(data.getBytes(AppConstants.SecurityConstants.ENCODE_TYPE_8));
		}
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
		{
			throw new AuthenticationSecurityException(e);
		}
		return hashedData;
	}

	/**
	 * 
	 * @param hash
	 * @return
	 * @throws AuthenticationSecurityException
	 */
	public static String toHex(byte[] hash) throws AuthenticationSecurityException
	{
		String hashedData = "";
		try
		{
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++)
			{
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			hashedData = hexString.toString();
		}
		catch (Exception e)
		{
			throw new AuthenticationSecurityException(e);
		}
		return hashedData;
	}

	/**
	 * Convert hex string to byte array
	 * 
	 * @param data
	 *            input string data
	 * @return bytes
	 */
	public static byte[] fromHex(String data)
	{
		int len = data.length();
		byte[] results = new byte[len / 2];
		for (int i = 0; i < len; i += 2)
		{
			results[i / 2] = (byte) ((Character.digit(data.charAt(i), 16) << 4)
					+ Character.digit(data.charAt(i + 1), 16));
		}
		return results;
	}

	/**
	 * @return SecureRandom
	 */
	public static SecureRandom createRandom()
	{
		return new SecureRandom();
	}

	/**
	 * @return SecureRandom
	 */
	public static SecureRandom createFixedRandom()
	{
		return new FixedRandom();
	}

	public static String getRandomPassword() throws AuthenticationSecurityException
	{
		SecureRandom random = createRandom();
		String password = new BigInteger(AppConstants.SecurityConstants.PASSWORD_BITS, random)
				.toString(AppConstants.SecurityConstants.PASSWORD_RADIX);
		return password;
	}

	public static String getRandomNumber(int numberOfDigits)
	{
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(numberOfDigits * 10);
		String formatted = String.format("%05d", num);

		return formatted;
	}

	private static class FixedRandom extends SecureRandom
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		MessageDigest sha;

		byte[] state;

		FixedRandom()
		{
			try
			{
				this.sha = MessageDigest.getInstance(AppConstants.SecurityConstants.SHA256_ALGORITHM);
				this.state = sha.digest();
			}
			catch (NoSuchAlgorithmException e)
			{
				throw new RuntimeException(
						"can't find " + AppConstants.SecurityConstants.SHA256_ALGORITHM + " Algorithm!");
			}
		}

		public void nextBytes(byte[] bytes)
		{
			int off = 0;
			sha.update(state);

			while (off < bytes.length)
			{
				state = sha.digest();
				if (bytes.length - off > state.length)
				{
					System.arraycopy(state, 0, bytes, off, state.length);
				}
				else
				{
					System.arraycopy(state, 0, bytes, off, bytes.length - off);
				}

				off += state.length;

				sha.update(state);
			}
		}
	}
}
