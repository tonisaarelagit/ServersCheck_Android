package com.app.serverscheck;

import android.app.Application;

import com.app.serverscheck.utils.LocalStorage;

public class SCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    private void initSingletons() {
        LocalStorage.init(this);
    }
}
