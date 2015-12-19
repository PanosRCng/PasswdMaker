package com.panosrcng.passwdmaker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity
{
    public static final String SETTINGS_STORE = "SETTINGS_STORE";

    private RadioGroup hashRadioGroup;

    private CheckBox lowercaseCheckBox;
    private CheckBox upperCaseCheckBox;
    private CheckBox numbersCheckBox;
    private CheckBox specialCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        hashRadioGroup = (RadioGroup) findViewById(R.id.hash_radioGroup);

        lowercaseCheckBox = (CheckBox) findViewById(R.id.lowercase_checkBox);
        upperCaseCheckBox = (CheckBox) findViewById(R.id.uppercase_checkBox);
        numbersCheckBox = (CheckBox) findViewById(R.id.numbers_checkBox);
        specialCheckBox = (CheckBox) findViewById(R.id.special_checkBox);

        SharedPreferences settings = getSharedPreferences(SETTINGS_STORE, 0);
        String hash_algorithm = settings.getString("hash_algorithm", "SHA-256");
        Boolean lowercase = settings.getBoolean("lowercase", true);
        Boolean uppercase = settings.getBoolean("uppercase", true);
        Boolean numbers = settings.getBoolean("numbers", true);
        Boolean special = settings.getBoolean("special", true);

        setSettings(hash_algorithm, lowercase, uppercase, numbers, special);
    }


    @Override
    protected void onStop()
    {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(SETTINGS_STORE, 0);
        SharedPreferences.Editor editor = settings.edit();

        int radioButtonID = hashRadioGroup.getCheckedRadioButtonId();
        View radioButton = hashRadioGroup.findViewById(radioButtonID);
        int index = hashRadioGroup.indexOfChild(radioButton);

        switch(index)
        {
            case 0:
                editor.putString("hash_algorithm", "MD5");
                break;
            case 1:
                editor.putString("hash_algorithm", "SHA-1");
                break;
            case 2:
                editor.putString("hash_algorithm", "SHA-256");
                break;
        }


        if( lowercaseCheckBox.isChecked() )
        {
            editor.putBoolean("lowercase", true);
        }
        else
        {
            editor.putBoolean("lowercase", false);
        }

        if( upperCaseCheckBox.isChecked() )
        {
            editor.putBoolean("uppercase", true);
        }
        else
        {
            editor.putBoolean("uppercase", false);
        }

        if( numbersCheckBox.isChecked() )
        {
            editor.putBoolean("numbers", true);
        }
        else
        {
            editor.putBoolean("numbers", false);
        }

        if( specialCheckBox.isChecked() )
        {
            editor.putBoolean("special", true);
        }
        else
        {
            editor.putBoolean("special", false);
        }

        editor.commit();
    }


    private void setSettings(String hash_algorithm, Boolean lowercase, Boolean uppercase, Boolean numbers, Boolean special)
    {
        switch (hash_algorithm)
        {
            case "MD5":
                hashRadioGroup.check(R.id.md5_radioButton);
                break;
            case "SHA-1":
                hashRadioGroup.check(R.id.sha1_radioButton);
                break;
            case "SHA-256":
                hashRadioGroup.check(R.id.sha256_radioButton);
                break;
        }

        lowercaseCheckBox.setChecked(lowercase);
        upperCaseCheckBox.setChecked(uppercase);
        numbersCheckBox.setChecked(numbers);
        specialCheckBox.setChecked(special);
    }

}
