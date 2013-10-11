package my.pack.passwdmaker.namespace;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class PasswdmakerActivity extends Activity {

	private EditText masterPasswd_et;
	private EditText url_et;
	private EditText passwd_et;
	private EditText length_et;
	private CheckBox md5_cbx;
	private CheckBox sha_cbx;
	private CheckBox all_cbx;
	private CheckBox letters_numbers_cbx;
	private CheckBox letters_cbx;
	private CheckBox numbers_cbx;
	private CheckBox special_cbx;
	private Button genButton;
	private Button pickButton;
	private Button optionsButton;
	private Button hideButton;
	
	private int use_hash;
	private int use_charset;
	
    private static final int PASSWDMAKER_ID = 1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    	use_hash = publicController.getInstance().getUse_hash();
    	use_charset = publicController.getInstance().getUse_charset();
        
    	create_notification();
    	
        if(publicController.getInstance().getState() == 1)
        {	        	
        	setLayout_mini();
        }
        else
        {
        	setLayout_options();
        }
      
    }

    private void create_notification()
    {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        
        int icon = R.drawable.passwdmaker;
        CharSequence tickerText = "passwordmaker";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        
        Context context = getApplicationContext();
        CharSequence contentTitle = "passwdmaker";
        CharSequence contentText = "click to open passwdmaker";
        Intent notificationIntent = new Intent(this, PasswdmakerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        
        mNotificationManager.notify(PASSWDMAKER_ID, notification);
    }
    
    private void setLayout_mini()
    {
        setContentView(R.layout.layout_mini);
  	
        publicController.getInstance().setState(1);
        
        masterPasswd_et = (EditText)findViewById(R.id.masterpasswd_add);  
        url_et = (EditText)findViewById(R.id.url_add); 
        length_et = (EditText)findViewById(R.id.length_add);
        passwd_et = (EditText)findViewById(R.id.passwd_add);         
        genButton = (Button)this.findViewById(R.id.gen_button);
    	pickButton = (Button)this.findViewById(R.id.pick_button);	
    	optionsButton = (Button)this.findViewById(R.id.options_button);	 
    	
    	pickButton.setVisibility(View.GONE);
    	
    	int length = publicController.getInstance().getLength();
    	length_et.setText(Integer.toString(length));
    	
    	String master_passwd = publicController.getInstance().getMasterPasswd();
    	masterPasswd_et.setText(master_passwd);
    	
    	String url = publicController.getInstance().getUrl();
    	url_et.setText(url);   	
    	
	    genButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	generate_passwd();
	        }
	    });
	    
	    pickButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	pick_passwd();
	        }
	    });
	    
	    optionsButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	setLayout_options();
	        }
	    });
	    	    
    }   
    
    private void setLayout_options()
    {
        setContentView(R.layout.layout_options);

        publicController.getInstance().setState(0);
        
        masterPasswd_et = (EditText)findViewById(R.id.masterpasswd_add);  
        url_et = (EditText)findViewById(R.id.url_add); 
        length_et = (EditText)findViewById(R.id.length_add);
        passwd_et = (EditText)findViewById(R.id.passwd_add); 
        md5_cbx = (CheckBox)findViewById(R.id.md5_check); 
        sha_cbx = (CheckBox)findViewById(R.id.sha_check); 
        all_cbx = (CheckBox)findViewById(R.id.all_check); 
        letters_numbers_cbx = (CheckBox)findViewById(R.id.letters_numbers_check); 
        letters_cbx = (CheckBox)findViewById(R.id.letters_check); 
        numbers_cbx = (CheckBox)findViewById(R.id.numbers_check); 
        special_cbx = (CheckBox)findViewById(R.id.special_check);         
        genButton = (Button)this.findViewById(R.id.gen_button);
    	pickButton = (Button)this.findViewById(R.id.pick_button);	
    	hideButton = (Button)this.findViewById(R.id.hide_button);	 
    	
    	pickButton.setVisibility(View.GONE);
    	
    	int length = publicController.getInstance().getLength();  	
    	length_et.setText(Integer.toString(length));
    	
    	String master_passwd = publicController.getInstance().getMasterPasswd();
    	masterPasswd_et.setText(master_passwd);
    	
    	String url = publicController.getInstance().getUrl();
    	url_et.setText(url);  
    
    	use_hash = publicController.getInstance().getUse_hash();
    	
	    if(use_hash == 1)
	    {	
	    	md5_cbx.setChecked(true);
	    }
	    else if(use_hash == 2)
	    {
	    	sha_cbx.setChecked(true);
	    }
	    
    	use_charset = publicController.getInstance().getUse_charset();
	    
    	if(use_charset == 1)
    	{
    		all_cbx.setChecked(true);
    	}
    	else if(use_charset == 2)
    	{
    		letters_numbers_cbx.setChecked(true);
    	}
    	else if(use_charset == 3)
    	{
    		letters_cbx.setChecked(true);
    	}
    	else if(use_charset == 4)
    	{
    		numbers_cbx.setChecked(true);
    	}	
    	else if(use_charset == 5)
    	{
    		special_cbx.setChecked(true);
    	}
    	
	    genButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	generate_passwd();
	        }
	    });
	    
	    pickButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	pick_passwd();
	        }
	    });
	    
	    hideButton.setOnClickListener(new View.OnClickListener()
	    {
	        public void onClick(View v)
	        {
	        	if(all_cbx.isChecked())
	        	{
	        		use_charset = 1;
	        	}
	        	else if(letters_numbers_cbx.isChecked())
	        	{
	        		use_charset = 2;
	        	}
	        	else if(letters_cbx.isChecked())
	        	{
	        		use_charset = 3;
	        	}
	        	else if(numbers_cbx.isChecked())
	        	{
	        		use_charset = 4;
	        	}
	        	else if(special_cbx.isChecked())
	        	{
	        		use_charset = 5;
	        	}
	        	
	        	publicController.getInstance().setUse_charset(use_charset);
	        	
	        	if(md5_cbx.isChecked())
	        	{
	        		use_hash = 1;
	        	}
	        	else if(sha_cbx.isChecked())
	        	{
	        		use_hash = 2;
	        	} 	

	        	publicController.getInstance().setUse_hash(use_hash);
	        	
	        	setLayout_mini();
	        }
	    });
	    	    	
	    md5_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(md5_cbx.isChecked())
	    		   {
	    			   sha_cbx.setChecked(false);
	    		   }
	    		   else
	    		   {
	    			   sha_cbx.setChecked(true);
	    		   }

	    	   }
	    });
	    
	    sha_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(sha_cbx.isChecked())
	    		   {
	    			   md5_cbx.setChecked(false);
	    		   }
	    		   else
	    		   {
	    			   md5_cbx.setChecked(true);
	    		   }

	    	   }
	    });
	    
	    all_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(all_cbx.isChecked())
	    		   {
	    			   letters_numbers_cbx.setChecked(false);
	    			   letters_cbx.setChecked(false);
	    			   numbers_cbx.setChecked(false);
	    			   special_cbx.setChecked(false);
	    		   }
	    	   }
	    });
	    
	    letters_numbers_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(letters_numbers_cbx.isChecked())
	    		   {
	    			   all_cbx.setChecked(false);
	    			   letters_cbx.setChecked(false);
	    			   numbers_cbx.setChecked(false);
	    			   special_cbx.setChecked(false);
	    		   }
	    	   }
	    });

	    letters_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(letters_cbx.isChecked())
	    		   {
	    			   letters_numbers_cbx.setChecked(false);
	    			   all_cbx.setChecked(false);
	    			   numbers_cbx.setChecked(false);
	    			   special_cbx.setChecked(false);
	    		   }
	    	   }
	    });	    

	    numbers_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(numbers_cbx.isChecked())
	    		   {
	    			   letters_numbers_cbx.setChecked(false);
	    			   letters_cbx.setChecked(false);
	    			   all_cbx.setChecked(false);
	    			   special_cbx.setChecked(false);
	    		   }
	    	   }
	    });	    

	    special_cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	    	
	    	   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	    	   {
	    		   if(special_cbx.isChecked())
	    		   {
	    			   letters_numbers_cbx.setChecked(false);
	    			   letters_cbx.setChecked(false);
	    			   numbers_cbx.setChecked(false);
	    			   all_cbx.setChecked(false);
	    		   }
	    	   }
	    });	    
	    
    }
    
    private void generate_passwd()
    {    	
    	generator myGen = new generator();

    	String master_passwd = masterPasswd_et.getText().toString();
    	String url = url_et.getText().toString();
    	String str_length = length_et.getText().toString();
    	
    	int length = publicController.getInstance().getLength();

    	if(str_length.length() > 0)
    	{
    		try
    		{
    			length = Integer.parseInt(str_length);
    		}
    		catch(NumberFormatException e)
    		{
    			Context context = getApplicationContext();
    			CharSequence text = "invalid password length";
    			int duration = Toast.LENGTH_SHORT;

    			Toast toast = Toast.makeText(context, text, duration);
    			toast.show();
    		}
    	}	
 
    	publicController.getInstance().setLength(length);
    	publicController.getInstance().setMasterPasswd(master_passwd);
    	publicController.getInstance().setUrl(url);
    	
    	if( publicController.getInstance().getState() == 0 )
    	{	
    		if(all_cbx.isChecked())
    		{
    			use_charset = 1;
    		}
    		else if(letters_numbers_cbx.isChecked())
    		{
    			use_charset = 2;
    		}
    		else if(letters_cbx.isChecked())
    		{
    			use_charset = 3;
    		}
    		else if(numbers_cbx.isChecked())
    		{
    			use_charset = 4;
    		}
    		else if(special_cbx.isChecked())
    		{
    			use_charset = 5;
    		}
    	
    		if(md5_cbx.isChecked())
    		{
    			use_hash = 1;
    		}
    		else if(sha_cbx.isChecked())
    		{
    			use_hash = 2;
    		}     	
    	}
    		
		String passwd = myGen.get_passwd(master_passwd, url, length, use_hash, use_charset);
    	
    	passwd_et.setText(passwd);
    	
    	pickButton.setVisibility(View.VISIBLE);

    }
    
    private void pick_passwd()
    {
    	copyToClipBoard();
    }
    
    private void copyToClipBoard()
    {
    	ClipboardManager ClipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    	ClipMan.setText(passwd_et.getText());
    }

}