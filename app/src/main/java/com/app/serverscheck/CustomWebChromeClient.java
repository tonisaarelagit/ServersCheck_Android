package com.app.serverscheck;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Brightest Star on 12/20/2015.
 */
public class CustomWebChromeClient extends WebChromeClient {

    private Date _startDateTime;
    private MainActivity _mainActivity;

    public CustomWebChromeClient(MainActivity mainActivity) {
        _mainActivity = mainActivity;
    }

    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

        if (view.getProgress() >= 0) {
            _startDateTime = new Date();
        }

        Date secondsSinceStart = new Date((new Date()).getTime() - _startDateTime.getTime());
        if (view.getProgress() < 100) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(secondsSinceStart);
            if (calendar.get(Calendar.SECOND) > 10) {
                view.stopLoading();
                _mainActivity.showLoading(false);
            }
        }
    }
}
