package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dropbox.client2.exception.DropboxException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class ImpActivity extends ActionBarActivity implements View.OnClickListener{

    List<String> modifiedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp);
        View implistButton = findViewById(R.id.button_implist);
        implistButton.setOnClickListener(this);
        View settingsButton = findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(this);
        View nowButton = findViewById(R.id.button_nowDL);
        nowButton.setOnClickListener(this);

        setModifiedList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        final int id = v.getId();

        if (id == R.id.button_implist) {
            Intent intent = new Intent(this, ImplistActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_settings) {
            Intent intent = new Intent(this, SetActivity.class);
            startActivity(intent);
        }else if (id == R.id.button_nowDL) {
            downloadModifiedList();
        }
    }

    public void setModifiedList(){
        modifiedList = new ArrayList<String>();
        modifiedList.add("/Movies/バイオハザード5 マーセナリーズリユニオン DUO 船首甲板 1165k.mp4");
        modifiedList.add("/Movies/バイオハザード5AE マーセナリーズ リユニオン DUO 船首甲板 ‐.mp4");
    }

    public void downloadModifiedList(){
        setModifiedList();
        ImplistActivity activity = new ImplistActivity();
//        DropBoxAuthActivity.Login();
//        loadMovie();
    }
//
//    public void loadMovie() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mDBApi.getFile("/test.txt", null, OutputStream, null);
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            String fileText = new String(outputStream.toByteArray());
//                        }
//                    });
//                } catch (DropboxException e) {
//                    Log.e("Download", "Download was failed.");
//                }
//            }
//        }).start();
//    }

}