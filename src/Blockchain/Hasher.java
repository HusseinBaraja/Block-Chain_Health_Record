package Blockchain;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class Hasher {

	/**
	 *
	 * @param input
	 * @return
	 */
	static public String sha256(String input)
	{
		return hash(input, "SHA-256");
	}


	/**
	 * 
	 * @param input
	 * @return
	 */
	static public String sha384(String input)
	{
		return hash(input, "SHA-384");
	}


	/**
	 * hash(String, String) : String
	 */
	private static String hash(String input, String algorithm) 
	{
		MessageDigest md;
		try 
		{
			//instantiate the MD object
			md = MessageDigest.getInstance(algorithm);
			//fetch input to MD
			md.update( input.getBytes() );
						
			//digest it
			byte[] hashBytes = md.digest();
			//convert to Hex format with Hex API from Apache common
			return String.valueOf(Hex.encodeHex(hashBytes));
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
