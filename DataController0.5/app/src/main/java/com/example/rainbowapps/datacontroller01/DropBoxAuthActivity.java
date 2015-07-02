package com.example.rainbowapps.datacontroller01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.dropbox.client2.*;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

public class DropBoxAuthActivity extends Activity {

    final static public String APP_KEY = "adttr9ats1zypbz";
    final static public String APP_SECRET = "t4d5tu2j1qy5pnw";
    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Login();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // 認証処理の終了
                mDBApi.getSession().finishAuthentication();
                System.out.println("onresume:" + mDBApi.getSession().getOAuth2AccessToken());
                saveAccessToken();
                //メニューへ遷移
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }

    //認証情報の永続化
    public void saveAccessToken() {
        // AccessTokenPair(認証情報)の取得
        String tokens = mDBApi.getSession().getOAuth2AccessToken();
        Log.d("DB Login", "token:" + tokens);
        // 認証情報をSharedPreferencesに保存
        SharedPreferences sp = getSharedPreferences("drop_box_auth", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("access_token", tokens);
        edit.commit();
    }

    public void Login() {
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        SharedPreferences prefs = getSharedPreferences("drop_box_auth", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        System.out.println("Login:auth:"+accessToken);
        if (accessToken == null) {
            mDBApi = new DropboxAPI<>(session);
            mDBApi.getSession().startOAuth2Authentication(DropBoxAuthActivity.this);

            Log.d("DB Login", "Token saved.");
        } else {
            session.setOAuth2AccessToken(accessToken);
            mDBApi = new DropboxAPI<>(session);
            mDBApi.getSession().setOAuth2AccessToken(accessToken);
            Log.d("DB Login", "Login Automatically.");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}
