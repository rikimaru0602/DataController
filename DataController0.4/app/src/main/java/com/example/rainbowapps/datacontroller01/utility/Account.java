package com.example.rainbowapps.datacontroller01.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Account {

    public static void saveAcc(Context context, String text1, String text2, String text3){
        String accNA = text1;
        String accOS = text2;
        String SSID = text3;
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("acc_na",accNA);
        editor.putString("acc_os",accOS);
        editor.putString("ssid",SSID);
        editor.apply();
    }

    public static String loadAccNA(Context context){
        String accNA = "-";
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        return preferences.getString("acc_na", accNA);
    }

    public static String loadSSID(Context context){
        String SSID = "-";
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        return preferences.getString("ssid", SSID);
    }

    public static String loadAccOS(Context context){
        String accOS = "-";
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        return preferences.getString("acc_os", accOS);
    }

    public static void saveAccPresence(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("acc_pre", true);
        editor.apply();
    }
    public static void delAccPresence(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("acc_pre", false);
        editor.apply();
    }
    public static boolean checkAccPresence(Context context) {
        boolean presence = false;
        final SharedPreferences preferences = context.getSharedPreferences("acc1", Context.MODE_PRIVATE);
        return preferences.getBoolean("acc_pre", presence);
    }


}