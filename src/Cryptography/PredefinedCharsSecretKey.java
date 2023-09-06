package Cryptography;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

public class PredefinedCharsSecretKey {
	private static final String ALGORITHM = "AES";
	
	private static final String SECRET_CHARS = "asdasdasdsa";
	
	public static Key create() {
		int keySize = 16;
		return new SecretKeySpec(Arrays.copyOf(SECRET_CHARS.getBytes(), keySize), ALGORITHM);
	}

	public static void main(String[] args) {
		System.out.println(create());
	}
}


