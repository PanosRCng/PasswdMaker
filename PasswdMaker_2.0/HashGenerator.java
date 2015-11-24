

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashGenerator
{
	public final static String[] supported_algorithms = {"MD5", "SHA-1", "SHA-256"}; 

	private String hash_algorithm = "";


	/*
	 * constructor
	 */
	public HashGenerator(String hash_algorithm)
	{
		this.hash_algorithm = hash_algorithm;
	}

	
    	public String getHash(String input)
    	{
		String hash = "";

		try
		{
        	    MessageDigest md = MessageDigest.getInstance(this.hash_algorithm);

        	    md.reset();

        	    byte[] buffer = input.getBytes();

        	    md.update(buffer);

        	    byte[] digest = md.digest();
	
        	    for (int i = 0; i<digest.length; i++)
        	    {
        	        hash +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
        	    }
		}
		catch(NoSuchAlgorithmException ex)
		{
			ex.printStackTrace();
		}

		return hash;
    	}
		
}
