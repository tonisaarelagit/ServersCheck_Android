package com.app.serverscheck;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Brightest Star on 12/18/2015.
 */
public class AccountActivity extends Activity {

    // declare variables
    private Button btnSave, btnCancel;
    private EditText textEmail, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // set layout with xml
        setContentView(R.layout.activity_account);

        // integrating variables with design data
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnCancel = (Button) findViewById(R.id.buttonCancel);

        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textPassword = (EditText) findViewById(R.id.editTextPassword);

        SharedPreferences sharedPreferences = getSharedPreferences("ServicesCheck", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", "");
        String password = sharedPreferences.getString("Password", "");

        textEmail.setText(email);
        textPassword.setText(password);

        btnSave.setOnClickListener(buttonClickListener);
        btnCancel.setOnClickListener(buttonClickListener);
    }

    // event for click save and cancel button
    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonSave) {                                                     // save button
                if (RegexUtilities.isInVaildEmail(textEmail.getText().toString())) {                // check email
                    if (textPassword.getText().toString() == null || textPassword.getText().toString().isEmpty()) {
                        RegexUtilities.showAlertDialog(AccountActivity.this, "Validation", "Password can not be empty.");
                    } else {
                        // save to shared preference
                        SharedPreferences sharedPreferences = getSharedPreferences("ServicesCheck", Context.MODE_APPEND);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Email", textEmail.getText().toString());
                        editor.putString("Password", textPassword.getText().toString());
                        editor.commit();

                        onBackPressed();
                    }
                } else {
                    RegexUtilities.showAlertDialog(AccountActivity.this, "Validation", "Please enter valid email.");
                }
            } else if (v.getId() == R.id.buttonCancel) {
                onBackPressed();
            }
        }
    };
}