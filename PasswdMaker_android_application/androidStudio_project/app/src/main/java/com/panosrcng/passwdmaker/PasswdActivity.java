package com.panosrcng.passwdmaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class PasswdActivity extends AppCompatActivity
{
    private  ClipboardManager ClipMan;

    private EditText masterEditText;
    private EditText urlEditText;
    private EditText lengthEditText;
    private EditText passwdEditText;
    private CheckBox showCheckBox;
    private TextView md5sumTextView;

    private String hash_algorithm;
    private int[] charsets;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        masterEditText = (EditText) findViewById(R.id.master_passwd_editText);
        urlEditText = (EditText) findViewById(R.id.url_editText);
        lengthEditText = (EditText) findViewById(R.id.length_editText);
        passwdEditText = (EditText) findViewById(R.id.passwd_editText);
        md5sumTextView = (TextView) findViewById(R.id.md5sum_textView);

        Button passwdButton = (Button) findViewById(R.id.passwd_button);
        passwdButton.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                String master_passwd = masterEditText.getText().toString();
                String url = urlEditText.getText().toString();
                String length = lengthEditText.getText().toString();

                String[] args = {master_passwd, url, length};

                if( validate_inputs(args) )
                {
                    String passwd = get_passwd(args);

                    HashGenerator hashGen = new HashGenerator( "MD5" );
                    String passwd_md5sum = hashGen.getHash(passwd);

                    passwdEditText.setText(passwd);
                    md5sumTextView.setText(passwd_md5sum);

                    copyToClipBoard(passwd);
                }

            }

        } );


        showCheckBox = (CheckBox) findViewById(R.id.show_checkBox);
        showCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (showCheckBox.isChecked()) {
                    passwdEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwdEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_passwd, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                settings();
                return true;
            case R.id.about:
                about();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void settings()
    {
        Intent i = new Intent(PasswdActivity.this, SettingsActivity.class);
        startActivity(i);
    }


    private void about()
    {
        Intent i = new Intent(PasswdActivity.this, AboutActivity.class);
        startActivity(i);
    }


    private String get_passwd(String[] args)
    {
        getSettings();

        HashGenerator hashGen = new HashGenerator( hash_algorithm );
        String hash = hashGen.getHash(args[0] + args[1] + args[2]);

        PasswdGenerator passwdGen = new PasswdGenerator();
        return passwdGen.getPasswd(hash, charsets, Integer.parseInt(args[2]));
    }


    private static boolean validate_inputs(String[] args)
    {
        for(int i=0; i<args.length; i++)
        {
            if( (args[i].length() == 0) || (args[i].length() > 256) )
            {
                return false;
            }
        }

        try
        {
            if( Integer.parseInt(args[2]) < 8)
            {
                return false;
            }
        }
        catch( NumberFormatException ex )
        {
            return false;
        }

        return true;
    }


    private void getSettings()
    {
        SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_STORE, 0);
        hash_algorithm = settings.getString("hash_algorithm", "SHA-256");
        Boolean lowercase = settings.getBoolean("lowercase", true);
        Boolean uppercase = settings.getBoolean("uppercase", true);
        Boolean numbers = settings.getBoolean("numbers", true);
        Boolean special = settings.getBoolean("special", true);
        getCharsets(lowercase, uppercase, numbers, special);
    }


    private void getCharsets(Boolean lowercase, Boolean uppercase, Boolean numbers, Boolean special)
    {
        charsets = new int[] {-1,-1,-1,-1};

        if(lowercase)
        {
            charsets[0] = 1;
        }

        if(uppercase)
        {
            charsets[1] = 2;
        }

        if(numbers)
        {
            charsets[2] = 3;
        }

        if(special)
        {
            charsets[3] = 4;
        }
    }


    private void copyToClipBoard(String text)
    {
        ClipMan.setText(text);
    }

}
