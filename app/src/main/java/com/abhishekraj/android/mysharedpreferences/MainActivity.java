package com.abhishekraj.android.mysharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBox checkBox;
    TextView checkBoxStatusText;
    RadioButton listPreferenceRadioButtonValue1;
    RadioButton listPreferenceRadioButtonValue2;
    RadioButton listPreferenceRadioButtonValue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setClickable(false);
        listPreferenceRadioButtonValue1 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue1);
        listPreferenceRadioButtonValue2 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue2);
        listPreferenceRadioButtonValue3 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue3);
        checkBoxStatusText = (TextView) findViewById(R.id.checkBoxStatusText);
        listPreferenceRadioButtonValue1.setClickable(false);
        listPreferenceRadioButtonValue2.setClickable(false);
        listPreferenceRadioButtonValue3.setClickable(false);
        setupSharedPreferences();


    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        // Get a reference to the default shared preferences from the PreferenceManager class
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean checkBoxStatus = sharedPreferences.getBoolean(getString(R.string.pref_checkbox_key),
                getResources().getBoolean(R.bool.pref_show_checkbox_default));

        checkBox.setChecked(checkBoxStatus);
        if (checkBoxStatus) {
            checkBoxStatusText.setText(R.string.checkBoxStatusTextChecked);
        } else {
            checkBoxStatusText.setText(R.string.checkBoxStatusTextUnchecked);
        }
        //register the sharedPreferenceListener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.contains(getString(R.string.pref_checkbox_key))) {
            boolean checkBoxStatus = sharedPreferences.getBoolean(getString(R.string.pref_checkbox_key),
                    getResources().getBoolean(R.bool.pref_show_checkbox_default));
            checkBox.setChecked(checkBoxStatus);
            if (checkBoxStatus) {
                checkBoxStatusText.setText(R.string.checkBoxStatusTextChecked);
            } else {
                checkBoxStatusText.setText(R.string.checkBoxStatusTextUnchecked);
            }
        }

    }

    //un-register the sharedPreferenceListener
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
