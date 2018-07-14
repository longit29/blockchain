package vn.vcoin.core.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class VcoinUtils {
	public static String applySha256(String input) {
		//System.out.println("sha256:"+ input);
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			// System.out.println(new String(hash));
			// This will contain hash as hexidecimal
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				// System.out.println(hash[i] + "-----" +hex);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			// System.out.println("hash>>>"+hexString);
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Applies ECDSA Signature and returns the result ( as bytes ).
	public static byte[] signECDSA(PrivateKey privateKey, String data) {
		try {
			Signature dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = data.getBytes();
			dsa.update(strByte);
			// signed
			return dsa.sign();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	// Verifies a String signature
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initVerify(publicKey);
			dsa.update(data.getBytes());

			return dsa.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//get string key
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}
