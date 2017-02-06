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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    CheckBox checkBox;
    TextView checkBoxStatusText;
    RadioGroup listPreferenceRadioGroup;
    RadioButton listPreferenceRadioButtonValue1;
    RadioButton listPreferenceRadioButtonValue2;
    RadioButton listPreferenceRadioButtonValue3;
    String listPreferenceValue;
    EditText editTextPreferenceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setClickable(false);
        listPreferenceRadioGroup = (RadioGroup) findViewById(R.id.listPreferenceRadioGroup);
        listPreferenceRadioButtonValue1 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue1);
        listPreferenceRadioButtonValue2 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue2);
        listPreferenceRadioButtonValue3 = (RadioButton) findViewById(R.id.listPreferenceRadioButtonValue3);
        checkBoxStatusText = (TextView) findViewById(R.id.checkBoxStatusText);
        editTextPreferenceEditText = (EditText) findViewById(R.id.editText);
        listPreferenceRadioButtonValue1.setClickable(false);
        listPreferenceRadioButtonValue2.setClickable(false);
        listPreferenceRadioButtonValue3.setClickable(false);
        //make editText not clickable, referenced from the @link: http://stackoverflow.com/a/9171614/5770629
        editTextPreferenceEditText.setKeyListener(null);
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
        loadListFromPreferences(sharedPreferences);
        loadEditTextFloatValueFromSharedPreferences(sharedPreferences);

        //register the sharedPreferenceListener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void loadListFromPreferences(SharedPreferences sharedPreferences) {
        listPreferenceValue = sharedPreferences.getString(getString(R.string.pref_list_key),
                getString(R.string.pref_value1_value));
        switch (listPreferenceValue){
            case "value2":
                listPreferenceRadioButtonValue2.setChecked(true);
                break;
            case "value3":
                listPreferenceRadioButtonValue3.setChecked(true);
                break;
            case "value1":
                listPreferenceRadioButtonValue1.setChecked(true);
                break;
        }
    }
    private void loadEditTextFloatValueFromSharedPreferences(SharedPreferences sharedPreferences) {
        float minFloatValue = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_editTextPref_key),
                getString(R.string.pref_editTextPref_default)));
        editTextPreferenceEditText.setText(String.valueOf(minFloatValue));
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
        } else if (key.contains(getString(R.string.pref_list_key))) {
            loadListFromPreferences(sharedPreferences);
        } else if (key.contains(getString(R.string.pref_editTextPref_key))) {
            loadEditTextFloatValueFromSharedPreferences(sharedPreferences);
        }

    }

    //un-register the sharedPreferenceListener
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
