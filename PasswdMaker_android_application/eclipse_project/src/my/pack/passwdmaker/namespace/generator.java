package my.pack.passwdmaker.namespace;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class generator {

	public generator()
	{
		
	}
	
    private String makeMD5Hash(String input)
    {
	try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++)
	    {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;

	 }catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	return "";
    }

    private String makeSHA1Hash(String input)
    {
	try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++)
	    {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;

	 }catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	return "";
    }
	
	private char[] convertStringToCharArray(String str)
	{        
        	char[] cArray = str.toCharArray();
        
		return cArray;
    }
    
	public String get_passwd(String master_passwd, String url, int length, int use_hash, int use_charset)
	{
    	char [] all_charset = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '}', '|', '[', ']', ':', '"', ';', '<', '>', '?', ',', '.', '/'};
    	char[] letters_numbers_charset = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};    	
    	char[] letters_charset = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    	char[] numbers_charset = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    	char[] special_charset = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '}', '|', '[', ']', ':', '"', ';', '<', '>', '?', ',', '.', '/'};
    	
    	char[] available_chars = {' '};
		
    	if(use_charset == 1)
    	{
    		available_chars = all_charset;
    	}
    	else if(use_charset == 2)
    	{
    		available_chars = letters_numbers_charset;
    	}
    	else if(use_charset == 3)
    	{
    		available_chars = letters_charset;
    	}
    	else if(use_charset == 4)
    	{
    		available_chars = numbers_charset;
    	}	
    	else if(use_charset == 5)
    	{
    		available_chars = special_charset;
    	}
    	
		String hash = "";
		
		if(use_hash == 1)
		{
			hash = makeMD5Hash(master_passwd + url);	
		}
		else if(use_hash == 2)
		{
			hash = makeSHA1Hash(master_passwd + url);
		}
				
		char[] hash_array = convertStringToCharArray(hash);

		int sum = 0;

		for(int i=0; i<hash_array.length; i++)
		{
			char ch = hash_array[i];

			sum = sum + ch;
		}
		
		String passwd = "";

		for(int i=0; i<length; i++)
		{
			char ch = hash_array[(i % hash_array.length)];

			int pos = 0;

			pos = (sum * (ch + i)) % available_chars.length; 	

			passwd += available_chars[pos];
		}

		return passwd;
	}
    
    
	
}
