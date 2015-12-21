package com.app.serverscheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Brightest Star on 12/18/2015.
 */
public class RegexUtilities {

    // check email
    static boolean isInvaild = false;

    public static boolean isInVaildEmail(String string) {
        isInvaild = false;
        if (string != null && string.isEmpty())
            isInvaild = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);

        if (matcher.matches()) {
            isInvaild = true;
        } else {
            isInvaild = false;
        }

        return isInvaild;
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