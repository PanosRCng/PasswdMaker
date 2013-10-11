package my.pack.passwdmaker.namespace;


public class publicController 
{
	private int state = 1;    // 1 -> mini   , 0 -> options 
	private int use_hash = 1;
	private int use_charset = 1;
	private String master_passwd = "";
	private String url = "";
	private int length = 8;
	
	private static publicController instance;

	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getMasterPasswd()
	{
		return master_passwd;
	}
	
	public void setMasterPasswd(String master_passwd)
	{
		this.master_passwd = master_passwd;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}

	public int getState()
	{
		return state;
	} 	
	
	public void setState(int state) 
	{
		this.state = state;
	}
	
	public int getUse_hash()
	{
		return use_hash;
	}
	
	public void setUse_hash(int use_hash)
	{
		this.use_hash = use_hash;
	}
	
	public int getUse_charset()
	{
		return use_charset;
	}
	
	public void setUse_charset(int use_charset)
	{
		this.use_charset = use_charset;
	}
	
	
	static
	{
		instance = new publicController();
	}
	private publicController() 
	{
	}
	
	public static publicController getInstance() 
	{
		return publicController.instance;
	}
}