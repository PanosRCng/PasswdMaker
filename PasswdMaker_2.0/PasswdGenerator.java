

public class PasswdGenerator
{
 		
	private final char[] lowercase_letters_charset = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	private final char[] uppercase_letters_charset = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    	private final char[] numbers_charset = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    	private final char[] special_charset = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '{', '}', '|', '[', ']', ':', '"', ';', '<', '>', '?', ',', '.', '/'};


	/*
	 * constructor
	 */
	public PasswdGenerator()
	{
		//
	}


	public String getPasswd(String hash, int[] charsets, int length)
	{
		String passwd = "";

		char[] available_chars = getCharset(charsets);

		char[] hash_array = hash.toCharArray();

		int sum = 0;
		for(int i=0; i<hash_array.length; i++)
		{
			sum = sum + hash_array[i];
		}
		
		for(int i=0; i<length; i++)
		{
			char ch = hash_array[ (i % hash_array.length) ];

			passwd += available_chars[ ((sum * (ch + i)) % available_chars.length) ];
		}		

		return passwd;
	}


	private char[] getCharset(int[] charsets)
	{
		char[] charset = {};

		for(int i=0; i<charsets.length; i++)
		{
			switch( charsets[i] )
			{
				case 1: charset = concatCharsets(charset, lowercase_letters_charset);
					break;

				case 2: charset = concatCharsets(charset, uppercase_letters_charset);
					break;

				case 3: charset = concatCharsets(charset, numbers_charset);
					break;

				case 4: charset = concatCharsets(charset, special_charset);
					break;
			}
		}

		return charset;
	}


	private char[] concatCharsets(char[] charset1, char[] charset2)
	{
		int length1 = charset1.length;
		int length2 = charset2.length;
		
		char[] new_charset= new char[length1+length2];

		System.arraycopy(charset1, 0, new_charset, 0, length1);
		System.arraycopy(charset2, 0, new_charset, length1, length2);

		return new_charset;
	}

}
