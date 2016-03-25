package com.app.serverscheck.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.app.serverscheck.R;
import com.app.serverscheck.RegexUtilities;
import com.app.serverscheck.activities.base.BaseActivity;
import com.app.serverscheck.utils.Constants;

public class AccountActivity extends BaseActivity implements View.OnClickListener {

    private EditText textEmail, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_account);

        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textPassword = (EditText) findViewById(R.id.editTextPassword);

        String email = localStorage.getStringPreference(Constants.EMAIL);
        String password = localStorage.getStringPreference(Constants.PASSWORD);

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
                    localStorage.put(Constants.EMAIL, textEmail.getText().toString());
                    localStorage.put(Constants.PASSWORD, textPassword.getText().toString());

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