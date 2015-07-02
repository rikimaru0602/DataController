package com.example.rainbowapps.datacontroller01;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.rainbowapps.datacontroller01.content.AutoDeleteService;
import com.example.rainbowapps.datacontroller01.content.AutoDownloadService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class PlayStocksActivity extends ActionBarActivity implements View.OnClickListener {

    private int INTERVAL_DELETE_SECOND = 5;
    private  String absPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stocks);

        TextView title = (TextView)findViewById(R.id.text_title_playstock);
        //TextView title = new TextView(this);
        View topButton = findViewById(R.id.button_top);
        topButton.setOnClickListener(this);
        View backButton = findViewById(R.id.button_back_stockplay);
        backButton.setOnClickListener(this);
        View newButton = findViewById(R.id.button_NewArrival);
        newButton.setOnClickListener(this);

        Intent intent = getIntent();
        absPath = intent.getStringExtra("filepath");
        title.setText(absPath);

        File file =new File(absPath);
        Log.d("Play",file.getPath());

        VideoView videoView = (VideoView) findViewById(R.id.video);
        videoView.setMediaController(new MediaController(this));

        if (file.exists()) {
            videoView.setVideoPath(file.getPath());
            videoView.start();
        }else{
            Log.d("Play","cannot find file path.");
        }

        RatingBar rb = new RatingBar(this);
        // 星の数を７に設定
        rb.setNumStars(5);
        // レートの変更を可能にする
        rb.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.5に設定
        rb.setStepSize((float) 0.5);
        // レートの初期値を3.0に設定
        rb.setRating((float) 3.0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if (id == R.id.button_top) {
            Intent intent1 = new Intent(this, MenuActivity.class);
            Intent intent2 = new Intent(this, AutoDeleteService.class);
            intent2.putExtra("path", absPath);
            showDeleteDate();
            startService(intent2);
            startActivity(intent1);
        }else if (id == R.id.button_back_stockplay) {
            Intent intent1 = new Intent(this, StocksActivity.class);
            Intent intent2 = new Intent(this, AutoDeleteService.class);
            intent2.putExtra("path", absPath);
            showDeleteDate();
            startService(intent2);
            startActivity(intent1);
        }else if (id == R.id.button_NewArrival) {
            Intent intent1 = new Intent(this, NewArrivalActivity.class);
            Intent intent2 = new Intent(this, AutoDeleteService.class);
            intent2.putExtra("path", absPath);
            showDeleteDate();
            startService(intent2);
            startActivity(intent1);
        }
    }

    public int getINTERVAL_DELETE_SECOND(){
        return INTERVAL_DELETE_SECOND;
    }

   public void showDeleteDate(){
       Calendar Cal    = new GregorianCalendar(new Locale("ja", "JP"));
       Cal.setTime(new Date());
       SimpleDateFormat DF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       Cal.add(Calendar.SECOND, INTERVAL_DELETE_SECOND);
       Toast.makeText(this,
               "この動画は" + DF.format(Cal.getTime()) + "に自動削除されます。",
               Toast.LENGTH_LONG).show();
   }
}