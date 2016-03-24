package com.app.serverscheck;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class AccountActivity extends Activity implements View.OnClickListener {

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

        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textPassword = (EditText) findViewById(R.id.editTextPassword);

        SharedPreferences sharedPreferences = getSharedPreferences("ServicesCheck", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", "");
        String password = sharedPreferences.getString("Password", "");

        textEmail.setText(email);
        textPassword.setText(password);

        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSave) {
            if (RegexUtilities.validateEmail(textEmail.getText().toString())) {
                if (textPassword.getText().toString().isEmpty()) {
                    RegexUtilities.showAlertDialog(AccountActivity.this,
                            "Validation", "Password can not be empty.");
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("ServicesCheck", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email", textEmail.getText().toString());
                    editor.putString("Password", textPassword.getText().toString());
                    editor.apply();

                    onBackPressed();
                }
            } else {
                RegexUtilities.showAlertDialog(AccountActivity.this, "Validation", "Please enter valid email.");
            }
        } else if (v.getId() == R.id.buttonCancel) {
            onBackPressed();
        }
    }
}