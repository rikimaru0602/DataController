package com.example.rainbowapps.datacontroller01;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.example.rainbowapps.datacontroller01.utility.Account;
import com.example.rainbowapps.datacontroller01.utility.DropboxUtils;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private int notif_num=0;
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private DropboxUtils mDButil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View menuButton = findViewById(R.id.button_Menu);
        menuButton.setOnClickListener(this);
        View regButton = findViewById(R.id.button_delAcc);
        regButton.setOnClickListener(this);

        notif_num = getIntent().getIntExtra("notif_num",notif_num);
        System.out.println("起動方法"+notif_num);
        if(notif_num==0) {
            if (Account.checkAccPresence(this)) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
            }
        }else if(notif_num==1){
            Intent intent = new Intent(this, ImplistActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v){
        final int id = v.getId();
        if (id == R.id.button_Menu) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_delAcc) {
            Toast.makeText(this, "アカウントを消去しました", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Account.delAccPresence(this);
            Intent intent = new Intent(this, RegActivity.class);
            startActivity(intent);
        }
    }
}