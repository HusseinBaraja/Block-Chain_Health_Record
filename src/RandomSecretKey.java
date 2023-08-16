import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;

public class RandomSecretKey {
	private static final String ALGORITHM = "AES";
	
	public static Key create() {
		short keySize = 256;
		try {
			KeyGenerator Kg = KeyGenerator.getInstance(ALGORITHM);
			Kg.init(keySize, new SecureRandom());
			return Kg.generateKey();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(create());
	}
}
