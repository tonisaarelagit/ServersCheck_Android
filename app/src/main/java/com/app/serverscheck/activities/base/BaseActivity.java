package com.app.serverscheck.activities.base;

import android.app.Activity;
import android.os.Bundle;

import com.app.serverscheck.utils.LocalStorage;

public class BaseActivity extends Activity {

    protected LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = LocalStorage.getInstance();
    }
}
