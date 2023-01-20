package com.example.masir.utility.util;

import static com.example.masir.utility.extentions.UtilExtKt.isAppAvailable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.example.masir.R;

import java.util.List;

public class IntentActionsUtil {

    private Activity activity;
    private Context context;

    public IntentActionsUtil(Activity activity){
        this.activity = activity;
    }
    public IntentActionsUtil(Context context){this.context = context;}

    public void callPhoneNumber(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        activity.startActivity(intent);
    }

    public void sendMessageToPhoneNumber(String phoneNumber , String msg){
        Intent intentSMS = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
        intentSMS.putExtra("sms_body", msg);
        activity.startActivity(intentSMS);
    }

    public void openWebSite(String url){
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void goToLocation(String Latitude , String longitude){
        Uri uri = Uri.parse("google.navigation:q=" + Latitude + "," + longitude + "&mode=d");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        activity.startActivity(intent);
    }


    public void openLinkedInPage(String linkedId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + linkedId));
        final PackageManager packageManager = activity.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=" + linkedId));
        }
        activity.startActivity(intent);
    }


    @SuppressLint("IntentReset")
    public void sendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        intent.putExtra(Intent.EXTRA_TEXT   , "");
        activity.startActivity(Intent.createChooser(intent, "Send mail..."));
    }


    public void openInstagram(String InstagramID){
        Uri uri = Uri.parse("http://instagram.com/_u/"+InstagramID);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            activity.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+InstagramID)));
        }
    }


    public void shareMessageToTelegram(String msg)
    {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(context, appName);
        if (isAppInstalled)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
            activity.startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else Toast.makeText(activity, "Telegram not Installed", Toast.LENGTH_SHORT).show();
    }
}
