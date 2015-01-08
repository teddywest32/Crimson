/*******************************************************************************
 *              Crimson Extended Administration Tool (CrimsonXAT)              *
 *                   Copyright (C) 2015 Subterranean Security                  *
 *                                                                             *
 *     This program is free software: you can redistribute it and/or modify    *
 *     it under the terms of the GNU General Public License as published by    *
 *      the Free Software Foundation, either version 3 of the License, or      *
 *                      (at your option) any later version.                    *
 *                                                                             *
 *       This program is distributed in the hope that it will be useful,       *
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 *                 GNU General Public License for more details.                *
 *                                                                             *
 *      You should have received a copy of the GNU General Public License      *
 *      along with this program.  If not, see http://www.gnu.org/licenses      *
 *******************************************************************************/
package subterranean.crimson.universal;



import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {

	public static String decrypt(byte[] data, EncType encryption, SecretKeySpec key) {
		switch (encryption) {
		case AES: {
			try {
				Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
				aes.init(Cipher.DECRYPT_MODE, key);
				return new String(aes.doFinal(data));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}

		}
		case DES: {
			try {
				Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
				des.init(Cipher.DECRYPT_MODE, key);
				return new String(des.doFinal(data));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}

		}
		case TRIPLEDES: {
			try {
				Cipher tripledes = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				tripledes.init(Cipher.DECRYPT_MODE, key);
				return new String(tripledes.doFinal(data));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}

		}
		case BLOWFISH: {
			try {
				Cipher blowfish = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
				blowfish.init(Cipher.DECRYPT_MODE, key);
				return new String(blowfish.doFinal(data));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}

		}
		default:
			break;

		}
		return null;
	}

	public static byte[] encrypt(String s, EncType encryption, SecretKeySpec key) {

		switch (encryption) {

		case AES: {
			byte[] ciphertext = null;
			try {
				Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
				aes.init(Cipher.ENCRYPT_MODE, key);
				ciphertext = aes.doFinal(s.getBytes());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				Logger.add("Error with encrypting");
				e.printStackTrace();
				Reporter.report(e);
			}

			return ciphertext;
		}
		case DES: {
			byte[] ciphertext = null;
			try {
				Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
				des.init(Cipher.ENCRYPT_MODE, key);
				ciphertext = des.doFinal(s.getBytes());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				Logger.add("Error with encrypting");
				e.printStackTrace();
				Reporter.report(e);
			}

			return ciphertext;

		}
		case TRIPLEDES: {
			byte[] ciphertext = null;
			try {
				Cipher tripledes = Cipher.getInstance("DESede/ECB/PKCS5Padding");
				tripledes.init(Cipher.ENCRYPT_MODE, key);
				ciphertext = tripledes.doFinal(s.getBytes());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				Logger.add("Error with encrypting");
				e.printStackTrace();
				Reporter.report(e);
			}

			return ciphertext;
		}
		case BLOWFISH: {
			byte[] ciphertext = null;
			try {
				Cipher tripledes = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
				tripledes.init(Cipher.ENCRYPT_MODE, key);
				ciphertext = tripledes.doFinal(s.getBytes());
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				Logger.add("Error with encrypting");
				e.printStackTrace();
				Reporter.report(e);
			}

			return ciphertext;
		}

		default:
			break;

		}

		return null;

	}

	public static SecretKeySpec makeKey(String passphrase, EncType encryption) {

		switch (encryption) {
		case None: {

			break;
		}
		case AES: {
			Logger.add("Creating an AES key");
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA");
				digest.update(passphrase.getBytes());
				return new SecretKeySpec(digest.digest(), 0, 16, "AES");

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}
		case DES: {
			Logger.add("Creating a DES key");
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA");
				digest.update(passphrase.getBytes());
				return new SecretKeySpec(digest.digest(), 0, 8, "DES");

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}
		case TRIPLEDES: {
			Logger.add("Creating a 3DES key");
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA");
				digest.update(passphrase.getBytes());
				return new SecretKeySpec(digest.digest(), 0, 16, "DESede");

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}
		case BLOWFISH: {

			Logger.add("Creating a blowfish key");
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA");
				digest.update(passphrase.getBytes());
				byte[] d = digest.digest();
				return new SecretKeySpec(d, 0, 16, "Blowfish");

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}

		}
		return null;
	}

}
