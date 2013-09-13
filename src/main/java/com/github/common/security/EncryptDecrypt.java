package com.github.common.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.github.puppet.client.ConfigurationManager;

public class EncryptDecrypt {
	private final static String algorithm = "AES";
	private final static String default_key = "JJ7kKLiXxkTWZFjl43+X9A==";
	private static String secureKey = ConfigurationManager.getDefaultConfig().getValue(
			"secure_key", default_key);
	private static byte[] key = null;

	static {
		try {
			// step 1 should never fail
			key = Base64.decodeBase64(default_key);
			key = Base64.decodeBase64(secureKey);
		} catch (Exception e) {
		}
	}
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
    	StringBuilder hs = new StringBuilder();
        for (int n = 0; n < b.length; n++) {
        	String stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0");
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

	public static String encrypt(byte[] data) {
		// Instantiate the cipher
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(data);
			return byte2hex(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static byte[] decrypt(String encrypted) {
		try {
			byte[] tmp = hex2byte(encrypted);

			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			return cipher.doFinal(tmp);
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
}
