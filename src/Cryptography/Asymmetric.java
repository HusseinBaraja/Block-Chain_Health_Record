package Cryptography;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class Asymmetric {

	private Cipher cipher;

	public Asymmetric() {
		
	}

	public Asymmetric(String ALGORITHM) throws Exception{
		cipher = Cipher.getInstance(ALGORITHM);
	}

	public String encrypt(String data, PublicKey pubKey) throws Exception {
		String cipherText = null;
		
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		
		byte[] cipherBytes = cipher.doFinal(data.getBytes());
		
		cipherText = Base64.getEncoder().encodeToString(cipherBytes);
		
		return cipherText;
		
	}
	
	public String decrypt(String ciphertext, PrivateKey privKey) throws Exception {
		
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		
		byte[] cipherBytes = Base64.getDecoder().decode(ciphertext);
		
		byte[] dataBytes = cipher.doFinal(cipherBytes);
		
		return new String(dataBytes);
		
	}
}
