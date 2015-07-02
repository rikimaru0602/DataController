package com.example.rainbowapps.datacontroller01.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

public class DropboxUtils {
    private static final String TOKEN = "token";
    private static final String PREF_NAME = "dropbox";
    public static final String APP_KEY = "appkey";
    public static final String APP_KEY_SECRET = "appsecret";
    private Context context;

    public DropboxUtils(Context context) {
        this.context = context;
    }

    public void storeOauth2AccessToken(String secret){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, secret);
        editor.commit();
    }

    public AndroidAuthSession loadAndroidAuthSession() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        if (token != null) {
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_KEY_SECRET);
            return new AndroidAuthSession(appKeys,token);
        } else {
            return null;
        }
    }

    public boolean hasLoadAndroidAuthSession() {
        return loadAndroidAuthSession() != null;
    }
}