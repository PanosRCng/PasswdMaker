


public class Test
{


	public static void main(String args[])
	{
		if(args.length == 4)
		{
			if( validate_inputs(args) )
			{
				String passwd = get_passwd(args);

				System.out.println(passwd);

				return;
			}
		}
		
		System.out.println("Usage: test hash_algorithm master_passwd url length");
	}


	private static String get_passwd(String[] args)
	{
		HashGenerator hashGen = new HashGenerator( args[0] );

		String hash = hashGen.getHash( args[1] + args[2] + args[3] );


		int[] charsets = {1,2,3,4};


		PasswdGenerator passwdGen = new PasswdGenerator();

		return passwdGen.getPasswd(hash, charsets, Integer.parseInt(args[3]));
	}


	private static boolean validate_inputs(String[] args)
	{
		for(int i=0; i<args.length; i++)
		{
			if(args[i].length() > 256)
			{
				return false;
			}
		}

		try
		{
			if( Integer.parseInt(args[3]) < 8)
			{
				return false;
			}
		}
		catch( NumberFormatException ex )
		{
			return false;
		}

		if( !supportedHash(args[0]) )
		{
			return false;
		}

		return true;
	}


	private static boolean supportedHash(String requested_hash)
	{
		for(int i=0; i<HashGenerator.supported_algorithms.length; i++)
		{
			if(HashGenerator.supported_algorithms[i].equals( requested_hash ))
			{
				return true;
			}
		}

		return false;
	}

}
