package Cryptography;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyAccess {
	public static PublicKey getPublicKey(String path) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		return KeyFactory.getInstance("RSA").generatePublic(spec);
		
	}
	public static PrivateKey getPrivateKey(String path) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(path));
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		return KeyFactory.getInstance("RSA").generatePrivate(spec);
	}

	public static void put(byte[] keyBytes, String path) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(keyBytes);
		} catch (IOException e) {
			throw new IOException("Failed to write key to file: " + e.getMessage());
		}
	}

}
