package com.app.serverscheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtilities {

    /**
     * Validate email address
     *
     * @return true if @param email is valid email address,
     * otherwise false
     */
    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    // show alert dialog
    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }
}