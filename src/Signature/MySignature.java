package Signature;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class MySignature {
	private static final String ALGORITHM = "SHA256WithRSA";
	private final Signature sig;

	public MySignature() throws Exception {
		sig = Signature.getInstance(ALGORITHM);
	}

	public byte[] getSignature(String text, PrivateKey key) throws Exception {
		sig.initSign(key);
		sig.update(text.getBytes());
		return sig.sign();
	}

	public boolean isTextAndSignatureValid(String text, byte[] signature, PublicKey key)throws Exception {
		sig.initVerify(key);
		sig.update(text.getBytes());
		return sig.verify(signature);
	}
}
