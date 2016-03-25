package com.app.serverscheck.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.serverscheck.utils.Constants;
import com.app.serverscheck.CustomWebChromeClient;
import com.app.serverscheck.CustomWebViewClient;
import com.app.serverscheck.R;
import com.app.serverscheck.RegexUtilities;
import com.app.serverscheck.activities.base.BaseActivity;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity {

    // declare variables for private
    private Button buttonMap, buttonDevices, buttonAlerts, buttonLogout;
    private ImageView imageViewLogoMain, imageViewLogoBottom;
    private TextView textViewLogin, textViewAccount;
    private LinearLayout linearLayoutMenuButtons, linearLayoutLogin, linearLayoutLoading;
    private RelativeLayout relativeLayoutWebViews;

    // declare variables for public
    public TextView textViewLastRefreshTime;
    public WebView webViewDevices, webViewAlerts, webViewMap;
    public boolean isLogging;
    public boolean isLogined;
    public Date dateTimeMap = new Date(), dateTimeAlerts = new Date(), dateTimeDevices = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // set layout with xml
        setContentView(R.layout.activity_main);

        // button
        buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonDevices = (Button) findViewById(R.id.buttonDevices);
        buttonAlerts = (Button) findViewById(R.id.buttonAlerts);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        // text view
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewAccount = (TextView) findViewById(R.id.textViewAccount);
        textViewLastRefreshTime = (TextView) findViewById(R.id.textViewLastRefreshTime);

        // image view
        imageViewLogoMain = (ImageView) findViewById(R.id.imageViewLogoMain);
        imageViewLogoBottom = (ImageView) findViewById(R.id.imageViewLogoBottom);

        // layout
        linearLayoutLogin = (LinearLayout) findViewById(R.id.linearLayoutLogin);
        linearLayoutLoading = (LinearLayout) findViewById(R.id.linearLayoutLoading);
        linearLayoutMenuButtons = (LinearLayout) findViewById(R.id.linearLayoutMenuButtons);
        relativeLayoutWebViews = (RelativeLayout) findViewById(R.id.relativeLayoutWebViews);

        // web view
        webViewDevices = (WebView) findViewById(R.id.webViewDevices);
        webViewAlerts = (WebView) findViewById(R.id.webViewAlerts);
        webViewMap = (WebView) findViewById(R.id.webViewMap);

        // set custom view client
        webViewDevices.setWebViewClient(new CustomWebViewClient(this));
        webViewAlerts.setWebViewClient(new CustomWebViewClient(this));
        webViewMap.setWebViewClient(new CustomWebViewClient(this));

        webViewDevices.setWebChromeClient(new CustomWebChromeClient(this));
        webViewAlerts.setWebChromeClient(new CustomWebChromeClient(this));
        webViewMap.setWebChromeClient(new CustomWebChromeClient(this));

        webViewDevices.loadUrl(String.format(Constants.BASE_URL, "logout.php"));

        webViewDevices.getSettings().setJavaScriptEnabled(true);
        webViewAlerts.getSettings().setJavaScriptEnabled(true);
        webViewMap.getSettings().setJavaScriptEnabled(true);

        webViewDevices.getSettings().setUseWideViewPort(true);
        webViewAlerts.getSettings().setUseWideViewPort(true);
        webViewMap.getSettings().setUseWideViewPort(true);

        // set action for button and text view
        textViewLogin.setOnClickListener(setOnClickListener);
        textViewAccount.setOnClickListener(setOnClickListener);
        buttonLogout.setOnClickListener(setOnClickListener);
        buttonMap.setOnClickListener(setOnClickListener);
        buttonDevices.setOnClickListener(setOnClickListener);
        buttonAlerts.setOnClickListener(setOnClickListener);
    }

    // for back pressed
    public void onBackPressed() {
        if (webViewMap.getVisibility() == View.VISIBLE) {
            if (webViewMap.canGoBack()) {
                webViewMap.goBack();
            } else {
                webViewMap.setVisibility(View.GONE);
                webViewAlerts.setVisibility(View.GONE);
                webViewDevices.setVisibility(View.VISIBLE);
                textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(dateTimeDevices));

                if (isNetworkAvailable()) {
                    webViewDevices.loadUrl(String.format(Constants.BASE_URL, "m/device.php"));
                }
            }
        } else if (webViewDevices.getVisibility() == View.VISIBLE) {
            if (webViewDevices.canGoBack()) {
                if (!webViewDevices.getUrl().equals(String.format(Constants.BASE_URL, "m/device.php"))) {
                    webViewDevices.goBack();
                }
            }
        } else if (webViewAlerts.getVisibility() == View.VISIBLE) {
            if (webViewAlerts.canGoBack()) {
                webViewAlerts.goBack();
            } else {
                webViewMap.setVisibility(View.GONE);
                webViewAlerts.setVisibility(View.GONE);
                webViewDevices.setVisibility(View.VISIBLE);
                textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(dateTimeDevices));

                if (isNetworkAvailable()) {
                    webViewDevices.loadUrl(String.format(Constants.BASE_URL, "m/device.php"));
                }
            }
        }
    }

    // for check available network
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activityNetworkInfo != null && activityNetworkInfo.isConnected();
    }

    // event for click log in and account button
    View.OnClickListener setOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonAlerts) {                                                // Alert Button
                webViewMap.setVisibility(View.GONE);
                webViewDevices.setVisibility(View.GONE);
                webViewAlerts.setVisibility(View.VISIBLE);
                textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(dateTimeAlerts));

                if (isNetworkAvailable()) {
                    webViewAlerts.loadUrl(String.format(Constants.BASE_URL, "m/alerts.php"));
                }
            } else if (view.getId() == R.id.buttonDevices) {                                         // Devices Button
                webViewMap.setVisibility(View.GONE);
                webViewAlerts.setVisibility(View.GONE);
                webViewDevices.setVisibility(View.VISIBLE);
                textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(dateTimeDevices));

                if (isNetworkAvailable()) {
                    webViewDevices.loadUrl(String.format(Constants.BASE_URL, "m/device.php"));
                }
            } else if (view.getId() == R.id.buttonMap) {                                            // Map Button
                webViewDevices.setVisibility(View.GONE);
                webViewAlerts.setVisibility(View.GONE);
                webViewMap.setVisibility(View.VISIBLE);
                textViewLastRefreshTime.setText(new SimpleDateFormat("dd MMM dd, yyyy - HH:mm").format(dateTimeMap));

                if (isNetworkAvailable()) {
                    webViewMap.loadUrl(String.format(Constants.BASE_URL, "m/map.php"));
                }
            } else if (view.getId() == R.id.buttonLogout) {                                         // Logout Button
                webViewDevices.loadUrl(String.format(Constants.BASE_URL, "logout.php"));
            } else if (view.getId() == R.id.textViewLogin) {                                        // Log in Button
                if (isNetworkAvailable()) {
                    String email = localStorage.getStringPreference(Constants.EMAIL);
                    String password = localStorage.getStringPreference(Constants.PASSWORD);
                    if (email == null || password == null) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Error");
                        alertDialogBuilder.setMessage("Please set up username and password. Do you want to set up them now?");
                        alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alertDialogBuilder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentAccount = new Intent(MainActivity.this, AccountActivity.class);
                                startActivity(intentAccount);
                            }
                        });

                        alertDialogBuilder.show();
                    } else {
                        String postDataBody = "email=" + email + "&pass=" + password;
                        isLogging = true;
                        webViewDevices.postUrl(String.format(Constants.BASE_URL, "login.php"), postDataBody.getBytes(Charset.forName("UTF-8")));
                    }
                } else {
                    RegexUtilities.showAlertDialog(MainActivity.this, "Warning", "Please check your network connection.");
                }
            } else if (view.getId() == R.id.textViewAccount) {                                      // Account Button
                Intent intentAccount = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intentAccount);
            }
        }
    };

    // after log in
    public void onLogined() {
        linearLayoutLogin.setVisibility(View.GONE);
        imageViewLogoMain.setVisibility(View.GONE);

        webViewDevices.setVisibility(View.VISIBLE);
        linearLayoutMenuButtons.setVisibility(View.VISIBLE);
        imageViewLogoBottom.setVisibility(View.VISIBLE);
        buttonLogout.setVisibility(View.VISIBLE);
        relativeLayoutWebViews.setVisibility(View.VISIBLE);
        isLogging = false;

        webViewDevices.loadUrl(String.format(Constants.BASE_URL, "m/device.php"));
        isLogined = true;
    }

    // after log out
    public void onLogout() {
        webViewDevices.setVisibility(View.GONE);
        webViewAlerts.setVisibility(View.GONE);
        webViewMap.setVisibility(View.GONE);
        linearLayoutMenuButtons.setVisibility(View.GONE);
        imageViewLogoBottom.setVisibility(View.GONE);
        buttonLogout.setVisibility(View.GONE);
        relativeLayoutWebViews.setVisibility(View.GONE);

        linearLayoutLogin.setVisibility(View.VISIBLE);
        imageViewLogoMain.setVisibility(View.VISIBLE);

        isLogined = false;
    }

    public void showLoading(boolean show) {
        linearLayoutLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}