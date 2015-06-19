package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

import java.io.File;


public class PlayStocksActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stocks);

        Intent intent = getIntent();
        String absPath = intent.getStringExtra("filepath");

//        String path = Environment.getExternalStorageDirectory().getPath();
//        String filePath = "/内部ストレージ/Movies/kitashiga.mp4";
//        File dir = new File(path+"/Movies");
//        File file = new File(dir.getAbsolutePath()+"/kitashiga.mp4");
//        File file = new File(dir.getAbsolutePath()+"/Utada - Come Back To Me.mp4");
        File file =new File(absPath);
        Log.d("Play",file.getPath());
        if (file.exists()) {
            VideoView videoView = (VideoView) findViewById(R.id.video);
            videoView.setVideoPath(file.getPath());
            videoView.start();
        }else{
            Log.d("Play","cannot find file path.");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_stocks, menu);
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
}
