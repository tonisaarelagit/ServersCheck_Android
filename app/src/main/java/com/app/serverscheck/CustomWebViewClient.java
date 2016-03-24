package com.app.serverscheck;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomWebViewClient extends WebViewClient {

    // variable for main activity
    private MainActivity _mainActivity;

    public CustomWebViewClient(MainActivity mainActivity) {
        _mainActivity = mainActivity;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        _mainActivity.showLoading(true);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        super.shouldOverrideUrlLoading(view, url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (_mainActivity.isLogging) {
            if (url.equals(String.format(Constants.BASE_URL, "device.php"))) {
                _mainActivity.onLogined();
            } else {
                RegexUtilities.showAlertDialog(_mainActivity, "Error", "Login failed. Please verify your network settings, username & password.");
                _mainActivity.showLoading(false);
            }
            _mainActivity.isLogging = false;
        } else if (url.equals(String.format(Constants.BASE_URL, "login.php"))) {
            _mainActivity.onLogout();
            _mainActivity.showLoading(false);
        } else if (view.getVisibility() == View.VISIBLE || !_mainActivity.isLogined) {
            _mainActivity.showLoading(false);
        }

        if (view == _mainActivity.webViewMap) {
            _mainActivity.dateTimeMap = new Date();
            _mainActivity.textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(_mainActivity.dateTimeMap));
        } else if (view == _mainActivity.webViewDevices) {
            _mainActivity.dateTimeDevices = new Date();
            _mainActivity.textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(_mainActivity.dateTimeDevices));
        } else if (view == _mainActivity.webViewAlerts) {
            _mainActivity.dateTimeAlerts = new Date();
            _mainActivity.textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(_mainActivity.dateTimeAlerts));
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        _mainActivity.showLoading(false);
    }
}
